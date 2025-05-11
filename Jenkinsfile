pipeline {
    agent any

    tools {
        maven 'Maven 3.8.1'
        jdk 'JDK 21'
    }

    environment {
        JAVA_HOME = "${tool 'JDK 21'}"
        MAVEN_HOME = "${tool 'Maven 3.8.1'}"
        PATH = "${env.JAVA_HOME}/bin:${env.MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Christian-Bohorquez/Backend_Ventas-API.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Desplegando aplicación...'
                sh 'nohup java -jar target/*.jar &'
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado exitosamente.'
        }
        failure {
            echo 'Falló el pipeline.'
        }
    }
}
