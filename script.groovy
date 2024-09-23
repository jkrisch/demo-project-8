def buildApp(){
    echo 'building the application..'
}

def testApp(){
    echo 'testing the application..'
}

def deployApp(){
    echo 'deploying the application..'
    echo "deploying ${params.VERSION} to environment: ${ENV}"
}

//return statement important as you need it to be able to import it into the jenkinsfile
return this