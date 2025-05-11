### Autores
- Lindao Montalván Emily Priscila
- Quinde Bohorquez Christian
- Palacios Villafuerte Mauro
- Pinto Vélez Denisse Valentina
- Vargas Peñafiel Eva Melany



#  Sistema de Ventas - Backend API

Este proyecto es una API REST desarrollada en **Java** utilizando el framework **Spring Boot**, con una arquitectura limpia y modular. Gestiona procesos relacionados con cuentas de usuario, agentes, reservas, facturación y otros aspectos esenciales de un sistema de ventas.

##  Tecnologías Utilizadas

- **Java 21**
- **Spring Boot**
- **Maven**
- **JPA / Hibernate**
- **Base de datos**: (agrega aquí el tipo, como MySQL, PostgreSQL, etc.)
- **Lombok**
- **JUnit / Mockito** (pruebas unitarias)

##  Estructura del Proyecto

Backend_Ventas-API-main/
├── pom.xml # Archivo de configuración de Maven
└── src/
└── main/
└── java/com/sistema/ventas/app/
├── application/ # Lógica de negocio (casos de uso, DTOs, servicios)
├── domain/ # Entidades de dominio (si existen)
├── infrastructure/ # Adaptadores y repositorios
└── VentasApiApplication.java # Clase principal de arranque


> El proyecto sigue principios de **arquitectura limpia**, separando la lógica de negocio, infraestructura y controladores.

## Instalación y Ejecución

### Prerrequisitos

- Java 21
- Maven 3.8+
- IntelliJ IDEA o Spring Tools Suite (opcional)

### Ejecución local


```bash
# 1. Clonar el repositorio
git clone https://github.com/tu-usuario/ventas-api.git
cd ventas-api

# 2. Construir el proyecto
mvn clean install

# 3. Ejecutar la aplicación
mvn spring-boot:run
 
# 4. Pruebas unitarias
mvn test

