# Scientific Calculator with a Full DevOps CI/CD Pipeline

This project implements a command-line scientific calculator in Java and automates its build, test, and deployment using a full DevOps pipeline.

<p align="center">The final application running after being deployed by the pipeline.</p>

## Calculator Features
A menu-driven Java application supporting:

- **Square Root Function (√x)** – Calculates the square root of a number.  
- **Factorial Function (x!)** – Computes the factorial of an integer.  
- **Natural Logarithm (ln(x))** – Calculates the natural logarithm.  
- **Power Function (x^b)** – Computes base raised to the exponent.

## 🤖 DevOps Pipeline Overview
The CI/CD pipeline automates the following stages:

- **Build & Test (Maven)** – Compiles Java code and runs unit tests.  
- **Containerize (Docker)** – Builds a Docker image of the application.  
- **Push to Registry (Docker Hub)** – Pushes the Docker image to Docker Hub.  
- **Deploy (Ansible)** – Deploys the container on the local machine.  
- **Notify (Jenkins)** – Sends email notifications after pipeline execution.

## 🛠️ Key Components (One-line Description)
- **Java Application (App.java)** – Core command-line calculator code.  
- **Unit Tests (AppTest.java)** – JUnit tests for validating calculator functions.  
- **Dockerfile** – Multi-stage build instructions for creating the Docker image.  
- **Jenkinsfile** – Automates CI/CD: build, test, push, deploy, and notify.  
- **Ansible Playbook (playbook.yml)** – Automates container deployment and management.

## 🚀 How to Run Manually
**Prerequisites:** Java 17, Maven, Docker  

### Commands
```bash
# Clone repository
git clone https://github.com/Hemang-2004/scientfic-calculator.git
cd scientfic-calculator

# Build Java application using Maven
mvn clean install

# Build Docker image
docker build -t scientific-calculator .

# Run Docker container interactively
docker run -it --name scientific-calculator-app scientific-calculator
```

## 🔧 Manual Commands for Testing

You can also run and test the application manually without the pipeline:

```bash
# Clone the repository
git clone https://github.com/Hemang-2004/scientfic-calculator.git
cd scientfic-calculator

# Build the Java application using Maven
mvn clean install

# Build the Docker image
docker build -t scientific-calculator .

# Run the Docker container interactively
docker run -it --name scientific-calculator-app scientific-calculator
```
## ⚡ Features of the Calculator

- **Square Root (√x)** – Computes square root of a number.  
- **Factorial (x!)** – Computes factorial of a number.  
- **Natural Logarithm (ln(x))** – Computes logarithm base e.  
- **Power (x^b)** – Computes base raised to the exponent.  

## 🛠️ Components Overview

- **Java Application (App.java)** – Core CLI calculator logic.  
- **Unit Tests (AppTest.java)** – Validates correctness of all operations.  
- **Dockerfile** – Builds a container image of the application.  
- **Jenkinsfile** – Automates the full CI/CD pipeline.  
- **Ansible Playbook (playbook.yml)** – Handles deployment and container management.  

## 📦 CI/CD Pipeline Summary

### Jenkins Pipeline

- **Build Stage:** Compiles Java code and runs unit tests.  
- **Docker Stage:** Builds Docker image.  
- **Push Stage:** Pushes image to Docker Hub.  
- **Deploy Stage:** Executes Ansible playbook for deployment.  
- **Notify Stage:** Sends email notifications about build status.  

### Ansible Playbook

- Stops any existing container.  
- Pulls the latest Docker image from Docker Hub.  
- Runs the new container interactively with STDIN and TTY enabled.  

## 🔗 Project Links

- **GitHub Repository:** [scientfic-calculator](https://github.com/Hemang-2004/scientfic-calculator)  
- **Docker Hub Repository:** [scientific-calculator](https://hub.docker.com/r/nonachadcp/scientific-calculator)  

## Author

**Hemang Seth** – Initial Work
