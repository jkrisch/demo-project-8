def gv

pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage('build app') {
            steps {
                script {
                    echo 'building the application...'
                }
            }
        }
        stage('build image') {
            steps {
                script {
                    echo "building the docker image..."
            }
        }
        stage('deploy') {
            steps {
                script {
                    echo 'deploying docker image...'
                }
            }
        }
    }
}
