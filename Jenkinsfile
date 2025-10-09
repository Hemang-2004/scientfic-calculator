pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "nonachadcp/scientific-calculator"
    }

    stages {
        stage('Test Java App') {
                    steps {
                        sh 'mvn test'
                    }
                }

        stage('Build Java App') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE_NAME}:latest ."
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_TOKEN')]) {
                    sh '''
                        echo $DOCKER_TOKEN | docker login -u $DOCKER_USER --password-stdin
                        docker push ${DOCKER_IMAGE_NAME}:latest
                    '''
                }
            }
        }






        stage('Deploy via Ansible') {
            steps {
                sh 'ansible-playbook playbook.yml'
            }
        }
    }

    post {
        success {
            mail to: 'nonachadcp@gmail.com',
                 subject: "SUCCESS: Jenkins Build #${BUILD_NUMBER} for ${JOB_NAME}",
                 body: "Build succeeded! Docker Image: ${DOCKER_IMAGE_NAME}:latest"
        }




        failure {
            mail to: 'nonachadcp@gmail.com',
                 subject: "FAILURE: Jenkins Build #${BUILD_NUMBER} for ${JOB_NAME}",
                 body: "Build failed! Check Jenkins logs: ${BUILD_URL}"
        }
    }
}
