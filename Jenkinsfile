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
                sh "docker build -t ${DOCK-IMAGE_NAME}:latest ."
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
    // === MODIFIED SECTION FOR EMAIL NOTIFICATIONS ===
    post {
        always {
            echo 'Pipeline finished. Logging out of Docker Hub.'
            sh 'docker logout'
        }
        success {
            emailext (
                subject: "SUCCESS: Pipeline '${env.JOB_NAME}' - Build #${env.BUILD_NUMBER}",
                body: """<p>SUCCESS: Pipeline <b>${env.JOB_NAME}</b> - Build #${env.BUILD_NUMBER}</p>
                           <p>The application was deployed successfully.</p>
                           <p>Check console output at <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                to: 'hemangseth0411@gmail.com' // <-- CHANGE THIS TO YOUR EMAIL
            )
        }
        failure {
            emailext (
                subject: "FAILURE: Pipeline '${env.JOB_NAME}' - Build #${env.BUILD_NUMBER}",
                body: """<p>FAILURE: Pipeline <b>${env.JOB_NAME}</b> - Build #${env.BUILD_NUMBER}</p>
                           <p>The pipeline failed. Please check the logs.</p>
                           <p>Check console output at <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                to: 'hemangseth0411@gmail.com' // <-- CHANGE THIS TO YOUR EMAIL
            )
        }
    }
}

