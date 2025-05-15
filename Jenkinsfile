/**
 * Definición del Pipeline de CI/CD para la aplicación ventas-api
 * Este pipeline automatiza la construcción, prueba y despliegue de la aplicación
 * utilizando Docker y Docker Compose
 */
pipeline {
    // Define que este pipeline puede ejecutarse en cualquier agente disponible
    agent any

    /**
     * Herramientas necesarias para la ejecución del pipeline
     * Estas herramientas deben estar previamente configuradas en Jenkins
     */
    tools {
        maven 'Maven 3.8.8'  // Versión de Maven para compilar el proyecto
        jdk 'JDK 21'         // Versión de Java para compilar y ejecutar la aplicación
    }

    /**
     * Variables de entorno utilizadas en todo el pipeline
     * Estas variables configuran rutas y nombres para los artefactos generados
     */
    environment {
        // Configuración de rutas para herramientas
        JAVA_HOME = "${tool 'JDK 21'}"
        MAVEN_HOME = "${tool 'Maven 3.8.8'}"
        PATH = "${env.JAVA_HOME}/bin:${env.MAVEN_HOME}/bin:${env.PATH}"
        
        // Configuración de Docker
        DOCKER_IMAGE = "ventas-api"                // Nombre de la imagen Docker
        DOCKER_TAG = "${env.BUILD_NUMBER}"        // Tag de la imagen basado en el número de build
    }

    /**
     * Definición de las etapas secuenciales del pipeline
     * Cada etapa (stage) representa un paso en el proceso de CI/CD
     */
    stages {
        /**
         * ETAPA 1: CLEANUP
         * Limpia el entorno antes de comenzar para evitar conflictos con ejecuciones previas
         * Detiene y elimina contenedores Docker existentes que podrían causar conflictos
         */
        stage('Cleanup') {
            steps {
                script {
                    echo 'Limpiando entorno para evitar conflictos...'
                    // Detiene y elimina todos los contenedores definidos en docker-compose.yml
                    // La opción --remove-orphans elimina también contenedores huérfanos
                    // || true asegura que el pipeline continúe incluso si no hay contenedores para detener
                    bat 'docker-compose down --remove-orphans || true'
                    // Muestra los contenedores en ejecución para verificar que se hayan detenido
                    bat 'docker ps'
                }
            }
        }

        /**
         * ETAPA 2: CHECKOUT
         * Obtiene el código fuente del repositorio Git
         * Esta etapa clona el repositorio y hace checkout de la rama principal
         */
        stage('Checkout') {
            steps {
                // Configuración detallada del checkout de Git
                checkout([$class: 'GitSCM',
                    branches: [[name: 'main']],               // Rama a clonar (main)
                    doGenerateSubmoduleConfigurations: false,  // No generar configuraciones de submódulos
                    extensions: [],                           // Sin extensiones adicionales
                    submoduleCfg: [],                         // Sin configuración de submódulos
                    userRemoteConfigs: [[
                        // ID de credenciales configurado en Jenkins para acceder al repositorio
                        credentialsId: '202ad56b-0965-413b-a661-f05ae4b1890f',
                        // URL del repositorio Git que contiene el código fuente
                        url: 'https://github.com/Christian-Bohorquez/Backend_Ventas-API.git'
                    ]]
                ])
            }
        }

        /**
         * ETAPA 3: BUILD
         * Compila el código fuente y descarga las dependencias
         * Esta etapa construye el proyecto sin ejecutar pruebas para ahorrar tiempo
         */
        stage('Build') {
            steps {
                // Ejecuta Maven para limpiar, compilar e instalar las dependencias
                // -DskipTests omite la ejecución de pruebas en esta fase
                bat 'mvn clean install -DskipTests'
            }
        }

        /**
         * ETAPA 4: TEST
         * Ejecuta las pruebas unitarias y de integración
         * Verifica que la aplicación funcione correctamente
         */
        stage('Test') {
            steps {
                // Ejecuta las pruebas unitarias y de integración con Maven
                bat 'mvn test'
            }
            // Acciones a realizar después de la etapa de pruebas
            post {
                // 'always' significa que estas acciones se ejecutan siempre, independientemente del resultado
                always {
                    // Publica los resultados de las pruebas en formato JUnit
                    // Esto permite visualizar los resultados en la interfaz de Jenkins
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        /**
         * ETAPA 5: PACKAGE
         * Empaqueta la aplicación en un archivo JAR ejecutable
         * Prepara el artefacto para su despliegue
         */
        stage('Package') {
            steps {
                // Crea el archivo JAR sin ejecutar pruebas nuevamente
                bat 'mvn package -DskipTests'
            }
        }

        /**
         * ETAPA 6: BUILD DOCKER IMAGE
         * Construye una imagen Docker de la aplicación
         * Utiliza el Dockerfile del proyecto para crear una imagen contenerizada
         */
        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Construyendo imagen Docker...'
                    // Construye la imagen Docker con un tag basado en el número de build
                    // Esto permite tener un historial de versiones de la imagen
                    bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    // También etiqueta la imagen como 'latest' para facilitar referencias
                    bat "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest"
                }
            }
        }

        /**
         * ETAPA 7: DOCKER COMPOSE UP
         * Despliega la aplicación utilizando Docker Compose
         * Inicia los servicios definidos en docker-compose.yml (app y base de datos)
         */
        stage('Docker Compose Up') {
            steps {
                script {
                    echo 'Levantando servicios con Docker Compose...'
                    // Verifica las versiones de Docker y Docker Compose instaladas
                    bat 'docker --version'
                    bat 'docker-compose --version'
                    // Muestra los contenedores en ejecución antes del despliegue
                    bat 'docker ps'
                    // Detiene contenedores previos si existen
                    bat 'docker-compose down || true'
                    // Muestra el contenido del archivo docker-compose.yml para diagnóstico
                    bat 'type docker-compose.yml'
                    // Inicia los servicios en modo detached (en segundo plano)
                    bat 'docker-compose up -d'
                    // Verifica el estado de los servicios después del despliegue
                    bat 'docker-compose ps'
                    // Muestra los logs de la aplicación para diagnóstico
                    bat 'docker-compose logs app || true'
                }
            }
        }

        /**
         * ETAPA 8: VERIFY DEPLOYMENT
         * Verifica que la aplicación se haya desplegado correctamente
         * Espera a que la aplicación esté completamente iniciada y verifica su estado
         */
        stage('Verify Deployment') {
            steps {
                script {
                    echo 'Verificando despliegue...'
                    // Espera 30 segundos para dar tiempo a que la aplicación se inicialice completamente
                    bat 'ping -n 30 127.0.0.1 > nul'
                    // Verifica los contenedores en ejecución
                    bat 'docker ps'
                    // Verifica el estado de los servicios de Docker Compose
                    bat 'docker-compose ps'
                    // Muestra las últimas 50 líneas de logs de la aplicación para verificar su estado
                    bat 'docker-compose logs app --tail=50 || true'
                }
            }
        }

        /**
         * ETAPA 9: CLEANUP DOCKER IMAGES
         * Limpia imágenes Docker antiguas para evitar acumulación de espacio en disco
         * Mantiene solo las 3 versiones más recientes de la imagen y elimina las más antiguas
         */
        stage('Cleanup Docker Images') {
            steps {
                script {
                    echo 'Limpiando imágenes Docker antiguas...'
                    // Script de batch para Windows para gestionar la limpieza de imágenes
                    bat '''
                        @echo off
                        REM Listar todas las imágenes y guardar en un archivo temporal
                        docker image ls ${DOCKER_IMAGE} --format "{{.Tag}}" > temp_tags.txt
                        
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
                                    if not "%%a"=="latest" (
                                        docker rmi ${DOCKER_IMAGE}:%%a
                                        set /a deleted+=1
                                    )
                                )
                            )
                        ) else (
                            echo No hay suficientes imágenes para limpiar
                        )
                        
                        REM Eliminar archivo temporal
                        del temp_tags.txt
                    '''
                }
            }
        }
    }

    /**
     * Sección POST: Define acciones a ejecutar después de que todas las etapas hayan terminado
     * Estas acciones dependen del resultado final del pipeline (exitoso o fallido)
     */
    post {
        // Acciones a ejecutar si el pipeline se completa exitosamente
        success {
            echo 'Pipeline ejecutado exitosamente.'
            echo 'La aplicación está disponible en http://localhost:8090'
            // Nota: Aquí podrían agregarse notificaciones adicionales o acciones de celebración
        }

        // Acciones a ejecutar si el pipeline falla en cualquier etapa
        failure {
            echo 'Falló el pipeline.'
            script {
                // En caso de fallo, asegurarse de que todos los contenedores se detengan
                // para liberar recursos y evitar conflictos en futuras ejecuciones
                bat 'docker-compose down || true'
                // Nota: Aquí podrían agregarse notificaciones de error o acciones de recuperación
            }
        }
    }
}
