def buildApp(){
    echo 'building the application..'
    sh 'mvn package'
}

def testApp(){
    echo 'testing the application..'
    sh 'mvn test'
}

def buildImage(username, password, repo, appname){
    echo 'building docker image'
    sh """
        echo ${password} | docker login -u ${username} --password-stdin
        docker build -t ${repo}/${appname}:jma-${params.VERSION} .
        docker push ${repo}/${appname}:jma-${params.VERSION}
    """
}

def deployApp(){
    echo 'deploying the application..'
    echo "deploying ${params.VERSION} to environment: ${ENV}"
}

//return statement important as you need it to be able to import it into the jenkinsfile
return this