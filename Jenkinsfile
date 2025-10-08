pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        DOCKER_IMAGE_NAME = "nonachadcp/scientific-calculator"
    }

    stages {
        stage('1. Build Java App') {
            steps {
                echo 'Building the application...'
                sh 'mvn clean install'
            }
        }

        stage('2. Build Docker Image') {
            steps {
                echo 'Building the Docker image...'
                sh "docker build -t ${DOCKER_IMAGE_NAME}:latest ."
            }
        }

        stage('3. Push to Docker Hub') {
            steps {
                echo 'Logging in and pushing the image...'
                sh "echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin"
                sh "docker push ${DOCKER_IMAGE_NAME}:latest"
            }
        }

        stage('4. Deploy with Ansible') {
            steps {
                echo 'Deploying the container using Ansible...'
                sh 'ansible-playbook playbook.yml'
            }
        }
    }


    // === FINAL MAIL NOTIFICATION SECTION ===
    post {
        always {
            echo 'Pipeline finished. Logging out of Docker Hub.'
            sh 'docker logout'
        }

        success {
            mail to: 'nonachadcp@gmail.com',
                 subject: "SUCCESS: Jenkins Build #${BUILD_NUMBER} for ${JOB_NAME}",
                 body: """\
Build succeeded!



Job: ${JOB_NAME}
Build Number: ${BUILD_NUMBER}
Docker Image: ${DOCKER_IMAGE_NAME}:latest
Build URL: ${BUILD_URL}

All stages completed successfully.
"""
        }

        failure {
            mail to: 'nonachadcp@gmail.com',
                 subject: "FAILURE: Jenkins Build #${BUILD_NUMBER} for ${JOB_NAME}",
                 body: """\
Build failed!

Job: ${JOB_NAME}
Build Number: ${BUILD_NUMBER}
Docker Image: ${DOCKER_IMAGE_NAME}:latest
Check the build logs here: ${BUILD_URL}

Please investigate the issue.
"""
        }
    }
}
