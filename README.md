<div align="center">

# Sistema de Ventas - Backend API

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-enabled-blue.svg)](https://www.docker.com/)
[![Jenkins](https://img.shields.io/badge/Jenkins-CI%2FCD-red.svg)](https://www.jenkins.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

API REST para la gestión de ventas con integración continua y despliegue automatizado

</div>

## Tabla de Contenidos

- [Descripción](#-descripción)
- [Tecnologías](#-tecnologías)
- [Arquitectura](#-arquitectura)
- [Instalación y Ejecución](#-instalación-y-ejecución)
  - [Prerrequisitos](#prerrequisitos)
  - [Ejecución Local](#ejecución-local)
  - [Ejecución con Docker](#ejecución-con-docker)
- [CI/CD con Jenkins](#-cicd-con-jenkins)
  - [Configuración](#configuración-de-jenkins)
  - [Pipeline](#pipeline-de-jenkins)
- [Endpoints API](#-endpoints-api)
- [Autores](#-autores)

## Descripción

Este proyecto es una API REST desarrollada en **Java** utilizando el framework **Spring Boot**, con una arquitectura limpia y modular. Gestiona procesos relacionados con cuentas de usuario, agentes, reservas, facturación y otros aspectos esenciales de un sistema de ventas.

## Tecnologías

- **Backend:**
  - Java 21
  - Spring Boot 3.2
  - Maven 3.8+
  - JPA / Hibernate
  - JUnit / Mockito (pruebas unitarias)
  - Lombok

- **Base de datos:**
  - MySQL 8.0

- **DevOps:**
  - Docker
  - Docker Compose
  - Jenkins
  - Git

## Arquitectura

El proyecto sigue principios de **arquitectura limpia**, separando la lógica de negocio, infraestructura y controladores:

```
ventas-api/
├── Dockerfile                # Configuración para la creación de la imagen Docker
├── Jenkinsfile               # Definición del pipeline de CI/CD
├── docker-compose.yml        # Configuración para orquestar contenedores
├── pom.xml                   # Configuración de Maven y dependencias
└── src/
    ├── main/
    │   ├── java/com/sistema/ventas/app/
    │   │   ├── application/       # Lógica de negocio (casos de uso, DTOs, servicios)
    │   │   ├── domain/            # Entidades de dominio
    │   │   ├── infrastructure/    # Adaptadores y repositorios
    │   │   └── VentasApiApplication.java  # Clase principal
    │   └── resources/
    │       ├── application.properties  # Configuración de la aplicación
    │       └── ...
    └── test/                 # Pruebas unitarias e integración
```

## Instalación y Ejecución

### Prerrequisitos

- Java 21
- Maven 3.8+
- Docker y Docker Compose (para ejecución containerizada)
- Git

### Ejecución Local

```bash
# 1. Clonar el repositorio
git clone https://github.com/Christian-Bohorquez/Backend_Ventas-API.git
cd ventas-api

# 2. Construir el proyecto
mvn clean install -DskipTests

# 3. Ejecutar la aplicación
mvn spring-boot:run

# 4. Ejecutar pruebas unitarias
mvn test
```

### Ejecución con Docker

```bash
# Construir y ejecutar con Docker Compose
docker-compose up -d

# Ver logs de la aplicación
docker-compose logs app

# Detener los contenedores
docker-compose down
```

La aplicación estará disponible en: http://localhost:8090

## CI/CD con Jenkins

### Configuración de Jenkins

El proyecto utiliza Jenkins para la integración continua y el despliegue automatizado. La configuración incluye:

1. **Plugins necesarios:**
   - Git
   - Pipeline
   - Docker Pipeline
   - Email Extension

2. **Configuración de puertos:**
   - Jenkins se ejecuta en los puertos 8080 y 50000 (para el agente)
   - La aplicación Spring Boot se configura en el puerto 8090 para evitar conflictos
   - La base de datos MySQL usa el puerto 3307 para evitar conflictos con instalaciones locales

3. **Configuración de notificaciones por correo:**
   - SMTP server: smtp.gmail.com
   - SMTP port: 465
   - Uso de SSL
   - Template de correo personalizado

### Pipeline de Jenkins

El pipeline definido en el `Jenkinsfile` incluye las siguientes etapas:

1. **Cleanup:** Limpia el entorno para evitar conflictos con contenedores existentes
2. **Checkout:** Obtiene el código fuente del repositorio
3. **Build:** Compila el proyecto con Maven
4. **Test:** Ejecuta pruebas unitarias
5. **Package:** Empaqueta la aplicación
6. **Build Docker Image:** Construye la imagen Docker de la aplicación
7. **Docker Compose Up:** Despliega la aplicación con Docker Compose
8. **Verify Deployment:** Verifica que la aplicación se haya desplegado correctamente

El pipeline incluye notificaciones por correo electrónico en caso de éxito o fallo.

## Endpoints API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET    | `/api/productos` | Obtiene todos los productos |
| GET    | `/api/productos/{id}` | Obtiene un producto por ID |
| POST   | `/api/productos` | Crea un nuevo producto |
| PUT    | `/api/productos/{id}` | Actualiza un producto existente |
| DELETE | `/api/productos/{id}` | Elimina un producto |
| GET    | `/api/ventas` | Obtiene todas las ventas |
| POST   | `/api/ventas` | Registra una nueva venta |

## Autores

- **Lindao Montalván Emily Priscila**
- **Quinde Bohorquez Christian**
- **Palacios Villafuerte Mauro**
- **Pinto Vélez Denisse Valentina**
- **Vargas Peñafiel Eva Melany**
