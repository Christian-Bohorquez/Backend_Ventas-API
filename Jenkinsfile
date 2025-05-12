// Configuración de destinatarios para notificaciones
def emailRecipients = 'valentinapinto002@gmail.com, mau162002@hotmail.com'
def slackChannel = '#ci-cd-notificaciones'

// URL del webhook de GitHub configurada en ngrok
def webhookUrl = 'https://2da9-190-110-47-81.ngrok-free.app/github-webhook/'

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
        stage('Checkout') {
            steps {
                // Obtener código del repositorio de GitHub
                checkout([$class: 'GitSCM', 
                    branches: [[name: 'main']], 
                    doGenerateSubmoduleConfigurations: false, 
                    extensions: [], 
                    submoduleCfg: [], 
                    userRemoteConfigs: [[
                        credentialsId: '202ad56b-0965-413b-a661-f05ae4b1890f', 
                        url: 'https://github.com/Christian-Bohorquez/Backend_Ventas-API.git'
                    ]]
                ])
            }
        }
        
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
                    // Verificar versión de Docker y Docker Compose
                    bat 'docker --version'
                    bat 'docker-compose --version'
                    // Verificar estado actual de Docker
                    bat 'docker ps'
                    // Detener y eliminar contenedores previos si existen
                    bat 'docker-compose down || true'
                    // Mostrar el contenido del archivo docker-compose.yml
                    bat 'type docker-compose.yml'
                    // Iniciar los servicios definidos en docker-compose.yml
                    bat 'docker-compose up -d'
                    // Verificar estado de los contenedores después del despliegue
                    bat 'docker-compose ps'
                    // Ver logs de la aplicación
                    bat 'docker-compose logs app'
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    echo 'Verificando despliegue...'
                    // Esperar a que la aplicación esté disponible (usando ping como alternativa a timeout)
                    bat 'ping -n 30 127.0.0.1 > nul'
                    // Verificar que los contenedores estén en ejecución
                    bat 'docker ps'
                    // Verificar el estado de los contenedores de Docker Compose
                    bat 'docker-compose ps'
                    // Verificar los logs de la aplicación para diagnosticar problemas
                    bat 'docker-compose logs app --tail=50'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado exitosamente.'
            echo 'La aplicación está disponible en http://localhost:8090'
            
            // Notificación por email en caso de éxito (con manejo de errores)
            script {
                try {
                    emailext (
                        subject: "\u2705 Pipeline Exitoso: ${currentBuild.fullDisplayName}",
                        body: """<p>El pipeline de <b>ventas-api</b> se ha ejecutado correctamente.</p>
                                <p>La aplicación está disponible en <a href="http://localhost:8090">http://localhost:8090</a></p>
                                <p>Versión de imagen Docker: <b>${DOCKER_IMAGE}:${DOCKER_TAG}</b></p>
                                <p>Ver detalles: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                        to: emailRecipients,
                        mimeType: 'text/html'
                    )
                } catch (Exception e) {
                    echo "ADVERTENCIA: No se pudo enviar el correo de notificación: ${e.message}"
                    echo "Esto no afecta el éxito del pipeline"
                }
            }
            
            // Notificación por Slack en caso de éxito
            slackSend (
                channel: slackChannel,
                color: 'good',
                message: "\u2705 *Pipeline Exitoso:* ${currentBuild.fullDisplayName}\n" +
                         "La aplicación está disponible en http://localhost:8090\n" +
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
            
            // Notificación por email en caso de fallo (con manejo de errores)
            script {
                try {
                    emailext (
                        subject: "\u274C Pipeline Fallido: ${currentBuild.fullDisplayName}",
                        body: """<p>El pipeline de <b>ventas-api</b> ha fallado.</p>
                            <p>Ver detalles del error: <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></p>
                            <p>Ver pipeline completo: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                        to: emailRecipients,
                        mimeType: 'text/html'
                    )
                } catch (Exception e) {
                    echo "ADVERTENCIA: No se pudo enviar el correo de notificación: ${e.message}"
                    echo "Continuando con el proceso de notificación"
                }
            }
            
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
                // Versión compatible con Windows para limpiar imágenes antiguas
                bat """
                    @echo off
                    REM Listar todas las imágenes y guardar en un archivo temporal
                    docker image ls ${DOCKER_IMAGE} --format \"{{.Tag}}\" > temp_tags.txt
                    
                    REM Contar cuántas imágenes hay
                    set /a count=0
                    for /f %%a in (temp_tags.txt) do set /a count+=1
                    
                    REM Si hay más de 3 imágenes, eliminar las más antiguas
                    if %count% gtr 3 (
                        echo Manteniendo solo las 3 versiones más recientes de la imagen
                        set /a to_delete=%count%-3
                        set /a deleted=0
                        for /f %%a in (temp_tags.txt) do (
                            if !deleted! lss !to_delete! (
                                docker rmi ${DOCKER_IMAGE}:%%a
                                set /a deleted+=1
                            )
                        )
                    ) else (
                        echo No hay suficientes imágenes antiguas para limpiar
                    )
                    
                    REM Eliminar archivo temporal
                    del temp_tags.txt
                """
            }
        }
    }
}
