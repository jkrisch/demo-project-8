def buildApp(){
    echo 'building the application..'
}

def testApp(){
    echo 'testing the application..'
}

def deployApp(){
    echo 'deploying the application..'
    echo "deploying ${params.VERSION}"
}

//return statement important as you need it to be able to import it into the jenkinsfile
return this