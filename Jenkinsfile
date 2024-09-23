// globally define the gv var to make accessible within every stage
def gv

pipeline {
    agent any

    /*the paremeters declarative gives you the oportunity to configure parameters you can pass when you run the job
    There are three native parameter types: string, choice, booleanParam
    */
    parameters{
        //string(name: 'VERSION', defaultValue:'', description: 'version to deploy on prd')
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executesTests', defaultValue: true, description: '')
    }
    

    //In the tools declarative you define the tools you need in your build
    tools {
        //retreive the name of the tool from the tools section within your jenkins UI
        //And this statement makes mvn commands available in all stages with the respective maven version
        maven 'maven-3.9'
    }

    //In the environment declarative you define the env vars you need in your build
    /*environment{
    }
    */
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
        stage('build app') {
            steps {
                script {
                    gv.buildApp()
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
                    gv.testApp()
                }
            }
        }

        stage('build image'){
            steps{
                script{
                    withCredentials([
                        usernamePassword(credentisId:'docker-login', passwordVariable: 'PASS', usernameVariable: 'USER')
                        ]){
                            def.buildImage($USER, $PASS, "jaykay84", "java-demo-app")
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
