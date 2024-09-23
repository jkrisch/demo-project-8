#!/user/bin/env groovy
//@Library('my-shared-lib')

//another way to import the library is
library identifier: 'my-shared-lib@main', retriever: modernSCM(
    [
        $class: 'GitSCMSource',
        remote: 'https://github.com/jkrisch/devops-bootcamp-project-8-shared-library.git'
        //in case the repo is private:
        //credentialsId:<<name-of-credentials-in-jenkins-store>>
    ]
)

// if no import definition like def gv would be there the import of the library would look as follows:
//@Library('my-shared-lib')_

// globally define the gv var to make accessible within every stage
def gv

pipeline {
    agent any

   //In the tools declarative you define the tools you need in your build
    tools {
        //retreive the name of the tool from the tools section within your jenkins UI
        //And this statement makes mvn commands available in all stages with the respective maven version
        maven 'maven-3.9'
    }

    //In the environment declarative you define the env vars you need in your build
    environment{
        TAG = ''
    }
    
    stages {
        stage('init'){
            steps{
                // instead of blowing up your jenkinsfile you can extract script functionalities and put them in an external script.groovy file
                // and load this file to be able to use the functions definide within there
                script {
                    gv = load "script.groovy"
                }
            }
        }
        
        stage('run unit tests'){
            //using the when clause we can decide weather we want to run a stage (similar to if-else conditions in programming)
            when{
                expression{
                    params.executesTests
                }
            }
            steps{
                script{
                    echo "Running tests for Branch= ${BRANCH_NAME}"
                    gv.testApp()
                }
            }
        }

        stage('increment version'){
            steps{
                script{
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit'

                    //read the new version from the pom.xml
                    def matcher = readFile('pom.xml') =~ '<version>(.+)<version>'
                    def version = matcher[0][1]
                    env.TAG = "$version-$BUILD_NUMBER"
                }
            }
        }

        stage('build app') {
            steps {
                script {
                    buildJar()
                }
            }
        }

        stage('build image'){
            steps{
                script{
                    withCredentials([
                        usernamePassword(credentialsId:'docker-login', passwordVariable: 'PASS', usernameVariable: 'USER')
                        ]){
                            dockerLogin(USER, PASS)

                            buildImage("jaykay84", "java-demo-app", tag)

                            dockerPush("jaykay84", "java-demo-app", tag)
                        }
                }
            }
        }

        stage('deploy') {
            // input gives the user the oportunity to choose between different parameters in a certain stage
            // for instance if you want to let the developer decide in which environment the build artifact should be deployed to.
            /*input{
                message "Select the environment to deploy to"
                ok "Done"
                parameters{
                    choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: '')
                }
            }*/
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
