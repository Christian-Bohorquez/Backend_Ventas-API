pipeline {
    agent any

    tools {
        maven 'Maven 3.8.8'
        jdk 'JDK 21'
    }

    environment {
        JAVA_HOME = "${tool 'JDK 21'}"
        MAVEN_HOME = "${tool 'Maven 3.8.8'}"
        PATH = "${env.JAVA_HOME}/bin:${env.MAVEN_HOME}/bin:${env.PATH}"
        DOCKER_IMAGE = "ventas-api"
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Cleanup') {
            steps {
                script {
                    echo 'Limpiando entorno para evitar conflictos...'
                    sh 'docker-compose down --remove-orphans || true'
                    sh 'docker ps'
                }
            }
        }

        stage('Checkout') {
            steps {
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
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Construyendo imagen Docker...'
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    sh "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest"
                }
            }
        }

        stage('Docker Compose Up') {
            steps {
                script {
                    echo 'Levantando servicios con Docker Compose...'
                    sh 'docker --version'
                    sh 'docker-compose --version'
                    sh 'docker ps'
                    sh 'docker-compose down || true'
                    sh 'cat docker-compose.yml'
                    sh 'docker-compose up -d'
                    sh 'docker-compose ps'
                    sh 'docker-compose logs app || true'
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    echo 'Verificando despliegue...'
                    sh 'sleep 30'
                    sh 'docker ps'
                    sh 'docker-compose ps'
                    sh 'docker-compose logs app --tail=50 || true'
                }
            }
        }

        //  Nuevo stage para limpieza de imágenes
        stage('Cleanup Docker Images') {
            steps {
                script {
                    echo 'Limpiando imágenes Docker antiguas...'
                    sh '''
                        TAGS=$(docker image ls ${DOCKER_IMAGE} --format "{{.Tag}}" | grep -v latest | sort -r)
                        COUNT=$(echo "$TAGS" | wc -l)
                        if [ "$COUNT" -gt 3 ]; then
                            echo "Eliminando imágenes antiguas..."
                            echo "$TAGS" | tail -n +4 | while read TAG; do
                                docker rmi ${DOCKER_IMAGE}:$TAG || true
                            done
                        else
                            echo "No hay suficientes imágenes para limpiar."
                        fi
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado exitosamente.'
            echo 'La aplicación está disponible en http://localhost:8090'
        }

        failure {
            echo 'Falló el pipeline.'
            script {
                sh 'docker-compose down || true'
            }
        }
    }
}
