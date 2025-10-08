Scientific Calculator with a Full DevOps CI/CD Pipeline
This project implements a command-line scientific calculator in Java and automates its entire build, test, and deployment process using a modern DevOps toolchain. The core of this project is a Jenkins pipeline that automatically compiles, containerizes, and deploys the application whenever new code is pushed to this repository.

**
<p align="center">The final application running after being deployed by the pipeline.</p>

Calculator Features
The calculator is a menu-driven Java application that supports the following scientific operations:

Square Root Function - √x

Factorial Function - x!

Natural Logarithm (base e) - ln(x)

Power Function - x^b

🤖 DevOps Pipeline Overview
The project is fully automated using a CI/CD (Continuous Integration/Continuous Deployment) pipeline. When a developer pushes a code change to the main branch on GitHub, a webhook triggers the Jenkins pipeline, which executes the following stages automatically:

**

Stage 1: Build & Test (Maven)

Jenkins clones the repository.

It uses Maven (mvn clean install) to compile the Java source code and run the JUnit tests to ensure code quality.

Stage 2: Containerize (Docker)

A Docker image is built using the provided Dockerfile. This packages the compiled Java application and its dependencies into a lightweight, portable container.

Stage 3: Push to Registry (Docker Hub)

The newly created Docker image is tagged and pushed to Docker Hub, making it available for deployment.

Stage 4: Deploy (Ansible)

Jenkins executes an Ansible playbook (playbook.yml).

The playbook pulls the latest Docker image from Docker Hub onto the local machine, stops any old running versions of the container, and starts the new one in interactive mode.

Stage 5: Notify

Jenkins sends an email notification indicating the success or failure of the pipeline build.

🛠️ Code & Configuration Files
This section details the purpose and content of each configuration and source code file.

1. Java Application (App.java)
This is the core application code. It's a simple command-line program that presents a menu of scientific operations to the user.

package com.spe.calculator;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // ... (main application loop)
    }

    public static double squareRoot(double num) {
        if (num < 0) return Double.NaN;
        return Math.sqrt(num);
    }

    public static long factorial(int num) {
        if (num < 0) return -1;
        if (num == 0 || num == 1) return 1;
        long result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    public static double naturalLog(double num) {
        if (num <= 0) return Double.NaN;
        return Math.log(num);
    }

    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
}

2. Unit Tests (AppTest.java)
These JUnit 5 tests validate the correctness of each calculator function. They are executed automatically during the Maven build stage in the Jenkins pipeline.

package com.spe.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    private final double DELTA = 1e-9;

    @Test
    void testSquareRoot() {
        assertEquals(5.0, App.squareRoot(25.0), DELTA);
    }

    @Test
    void testFactorial() {
        assertEquals(120, App.factorial(5));
    }
    // ... (other tests)
}

3. Dockerfile
This file provides the instructions to build the Docker image. It uses a multi-stage build to create a small, efficient final image.

# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Create a smaller final image with only the compiled app
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/scientific-calculator-1.0-SNAPSHOT.jar scientific-calculator.jar
ENTRYPOINT ["java", "-jar", "scientific-calculator.jar"]

4. Jenkinsfile (The CI/CD Pipeline)
This is the heart of the automation, written as a declarative Jenkins pipeline script. It defines all the stages and steps required to build, test, and deploy the application.

pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        DOCKER_IMAGE_NAME = "knightstriker/scientific-calculator"
    }

    stages {
        // Stage 1: Compiles the Java code and runs unit tests.
        stage('1. Build Java App') {
            steps {
                echo 'Building the application...'
                sh 'mvn clean install'
            }
        }

        // Stage 2: Builds the Docker image using the Dockerfile.
        stage('2. Build Docker Image') {
            steps {
                echo 'Building the Docker image...'
                sh "docker build -t ${DOCKER_IMAGE_NAME}:latest ."
            }
        }

        // Stage 3: Logs into Docker Hub and pushes the image.
        stage('3. Push to Docker Hub') {
            steps {
                echo 'Logging in and pushing the image...'
                sh "echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin"
                sh "docker push ${DOCKER_IMAGE_NAME}:latest"
            }
        }

        // Stage 4: Deploys the container using Ansible.
        // BUILD_ID=dontKillMe prevents Jenkins from stopping the container after the build.
        stage('4. Deploy with Ansible') {
            steps {
                echo 'Deploying the container using Ansible...'
                sh 'BUILD_ID=dontKillMe ansible-playbook playbook.yml'
            }
        }
    }

    // This block runs after all stages are complete.
    post {
        always {
            echo 'Pipeline finished. Logging out of Docker Hub.'
            sh 'docker logout'
        }
        // Sends a success email.
        success {
            mail(
                to: 'hemangseth0411@gmail.com',
                subject: "SUCCESS: Jenkins Build #${env.BUILD_NUMBER} for ${env.JOB_NAME}",
                body: "Build succeeded! View details at ${env.BUILD_URL}"
            )
        }
        // Sends a failure email.
        failure {
            mail(
                to: 'hemangseth0411@gmail.com',
                subject: "FAILURE: Jenkins Build #${env.BUILD_NUMBER} for ${env.JOB_NAME}",
                body: "Build failed. Check logs at ${env.BUILD_URL}"
            )
        }
    }
}

5. Ansible Playbook (playbook.yml)
This playbook automates the deployment process. It's executed by Jenkins in the final stage of the pipeline.

- name: Deploy Scientific Calculator Docker Container
  hosts: localhost
  connection: local
  tasks:
    # 1. Ensure any old version of the container is stopped and removed.
    - name: Stop and remove any existing container
      community.docker.docker_container:
        name: scientific-calculator-app
        state: absent
      ignore_errors: yes

    # 2. Pull the newest version of the image from Docker Hub.
    - name: Pull the latest Docker image from Docker Hub
      community.docker.docker_image:
        name: "knightstriker/scientific-calculator:latest"
        source: pull

    # 3. Run the new container in interactive mode so the menu works.
    - name: Run the Docker container
      community.docker.docker_container:
        name: scientific-calculator-app
        image: "knightstriker/scientific-calculator:latest"
        state: started
        interactive: true # Keep STDIN open for the menu
        tty: true       # Allocate a pseudo-TTY for interaction

🚀 How to Run Manually (Without Pipeline)
Prerequisites
Java 17 (JDK)

Apache Maven

Docker

Steps
Clone the repository:

git clone [https://github.com/Hemang-2004/scientfic-calculator.git](https://github.com/Hemang-2004/scientfic-calculator.git)
cd scientfic-calculator

Build the application with Maven:

mvn clean install

Build the Docker image:

docker build -t scientific-calculator .

Run the Docker container:

docker run -it --name scientific-calculator-app scientific-calculator

Press Enter to see the menu.

🔗 Project Links
GitHub Repository: https://github.com/Hemang-2004/scientfic-calculator

Docker Hub Repository: https://hub.docker.com/r/knightstriker/scientific-calculator

Author
Hemang Seth - Initial Work
