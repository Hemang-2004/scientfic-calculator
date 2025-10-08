pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        DOCKER_IMAGE_NAME = "knightstriker/scientific-calculator"
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

        stage('5. Run Docker Container') {
            steps {
                echo 'Starting Docker container...'
                sh """
                   docker rm -f scientific-calculator-app || true
                   docker run -d --name scientific-calculator-app ${DOCKER_IMAGE_NAME}:latest tail -f /dev/null
                """
            }
        }

        stage('6. Start App inside Container') {
            steps {
                echo 'Starting app inside container...'
                sh "docker exec -d scientific-calculator-app java -jar scientific-calculator.jar"
            }
        }

    }

    post {
        always {
            echo 'Pipeline finished. Logging out of Docker Hub.'
            sh 'docker logout'
        }

        success {
            mail to: 'nonachadcp@gmail.com',
                 subject: "SUCCESS: Jenkins Build #${BUILD_NUMBER} for ${JOB_NAME}",
                 body: "Build succeeded!\nJob: ${JOB_NAME}\nBuild Number: ${BUILD_NUMBER}\nDocker Image: ${DOCKER_IMAGE_NAME}:latest\nBuild URL: ${BUILD_URL}\nAll stages completed successfully."
        }

        failure {
            mail to: 'nonachadcp@gmail.com',
                 subject: "FAILURE: Jenkins Build #${BUILD_NUMBER} for ${JOB_NAME}",
                 body: "Build failed!\nJob: ${JOB_NAME}\nBuild Number: ${BUILD_NUMBER}\nDocker Image: ${DOCKER_IMAGE_NAME}:latest\nCheck the build logs here: ${BUILD_URL}\nPlease investigate the issue."
        }
    }
}
