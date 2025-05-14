// Nota: La configuración de correo electrónico se gestiona directamente en Jenkins
// con el template 'email-jenkins' y la cuenta mau282002@gmail.com

pipeline {
    agent any

    tools {
            maven 'Maven 3.8.1' //  herramienta Maven con la versión 3.8.1
            jdk 'JDK 21'        // Declara el JDK (Java Development Kit) versión 21
        }

        environment {
            JAVA_HOME = "${tool 'JDK 21'}"          // Establece la variable de entorno JAVA_HOME con la ruta al JDK 21
            MAVEN_HOME = "${tool 'Maven 3.8.1'}"    // Establece MAVEN_HOME con la ruta al Maven 3.8.1
            PATH = "${env.JAVA_HOME}/bin;${env.MAVEN_HOME}/bin;${env.PATH}" // Añade los binarios de Java y Maven al PATH del sistema
            DOCKER_IMAGE = "ventas-api"             // Nombre de la imagen Docker a generar
            DOCKER_TAG = "${env.BUILD_NUMBER}"      // Etiqueta (tag) de la imagen Docker, usando el número de build de Jenkins
        }
    stages {
        stage('Cleanup') {
            steps {
                script {
                    echo 'Limpiando entorno para evitar conflictos...'
                    // Detener y eliminar contenedores existentes para evitar conflictos
                    bat 'docker-compose down --remove-orphans || true'
                    // Verificar que no haya contenedores usando los puertos necesarios
                    bat 'docker ps'
                }
            }
        }
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
            
            // Las notificaciones por correo se gestionan directamente por Jenkins
            // usando el template configurado 'email-jenkins'
        }
        
        failure {
            echo 'Falló el pipeline.'
            script {
                // En caso de fallo, intentar limpiar recursos
                bat 'docker-compose down || true'
            }
            
            // Las notificaciones por correo se gestionan directamente por Jenkins
            // usando el template configurado 'email-jenkins'
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
