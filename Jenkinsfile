// Configuración de destinatarios para notificaciones
def emailRecipients = 'valentinapinto002@gmail.com, mau162002@hotmail.com'
def slackChannel = '#ci-cd-notificaciones'

// URL del webhook de GitHub configurada en ngrok
def webhookUrl = 'https://dec2-179-60-52-11.ngrok-free.app/github-webhook/'

pipeline {
    agent any

    tools {
        maven 'Maven 3.8.1'
        jdk 'JDK 21'
    }

    environment {
        JAVA_HOME = "${tool 'JDK 21'}"
        MAVEN_HOME = "${tool 'Maven 3.8.1'}"
        PATH = "${env.JAVA_HOME}/bin;${env.MAVEN_HOME}/bin;${env.PATH}"
        DOCKER_IMAGE = "ventas-api"
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Construyendo imagen Docker...'
                    bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    bat "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest"
                }
            }
        }

        stage('Docker Compose Up') {
            steps {
                script {
                    echo 'Levantando servicios con Docker Compose...'
                    // Detener y eliminar contenedores previos si existen
                    bat 'docker-compose down || true'
                    // Iniciar los servicios definidos en docker-compose.yml
                    bat 'docker-compose up -d'
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    echo 'Verificando despliegue...'
                    // Esperar a que la aplicación esté disponible
                    bat 'timeout /t 30 /nobreak'
                    // Verificar que los contenedores estén en ejecución
                    bat 'docker ps'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado exitosamente.'
            echo 'La aplicación está disponible en http://localhost:8080'
            
            // Notificación por email en caso de éxito
            emailext (
                subject: "\u2705 Pipeline Exitoso: ${currentBuild.fullDisplayName}",
                body: """<p>El pipeline de <b>ventas-api</b> se ha ejecutado correctamente.</p>
                        <p>La aplicación está disponible en <a href="http://localhost:8080">http://localhost:8080</a></p>
                        <p>Versión de imagen Docker: <b>${DOCKER_IMAGE}:${DOCKER_TAG}</b></p>
                        <p>Ver detalles: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                to: emailRecipients,
                mimeType: 'text/html'
            )
            
            // Notificación por Slack en caso de éxito
            slackSend (
                channel: slackChannel,
                color: 'good',
                message: "\u2705 *Pipeline Exitoso:* ${currentBuild.fullDisplayName}\n" +
                         "La aplicación está disponible en http://localhost:8080\n" +
                         "Versión de imagen Docker: ${DOCKER_IMAGE}:${DOCKER_TAG}\n" +
                         "Ver detalles: ${env.BUILD_URL}"
            )
        }
        
        failure {
            echo 'Falló el pipeline.'
            script {
                // En caso de fallo, intentar limpiar recursos
                bat 'docker-compose down || true'
            }
            
            // Notificación por email en caso de fallo
            emailext (
                subject: "\u274C Pipeline Fallido: ${currentBuild.fullDisplayName}",
                body: """<p>El pipeline de <b>ventas-api</b> ha fallado.</p>
                        <p>Ver detalles del error: <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></p>
                        <p>Ver pipeline completo: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                to: emailRecipients,
                mimeType: 'text/html'
            )
            
            // Notificación por Slack en caso de fallo
            slackSend (
                channel: slackChannel,
                color: 'danger',
                message: "\u274C *Pipeline Fallido:* ${currentBuild.fullDisplayName}\n" +
                         "Ver detalles del error: ${env.BUILD_URL}console\n" +
                         "Ver pipeline completo: ${env.BUILD_URL}"
            )
        }
        
        always {
            // Limpiar imágenes antiguas para evitar acumulación
            script {
                echo 'Limpiando imágenes Docker antiguas...'
                // Mantener solo las últimas 3 versiones de la imagen
                bat "docker image ls ${DOCKER_IMAGE} --format \"{{.Tag}}\" | sort -r | tail -n +4 | xargs -I {} docker rmi ${DOCKER_IMAGE}:{} || true"
            }
        }
    }
}
