pipeline {
    environment {
        registry = "NiharGharat/coronaVirusTracker"
        registryCredential = "dockerpipeline"
        dockerImage = ""
    }
    agent any
    stages {
        stage('Cloning Git') {
            steps {
                git 'git@github.com:NiharGharat/coronaVirusTracker.git'
            }
        }
        stage('Building Docker Image') {
            steps {
                script {
                    dockerImage = docker.build registry + “:$BUILD_NUMBER”
                }
            }
        }
        stage("Finish") {
            steps {
                script {
                    sh 'ls'
                }
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
	    mail to: 'nihar.gharat@gmail.com',
            subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
            body: "Something is wrong with ${env.BUILD_URL}"
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}
