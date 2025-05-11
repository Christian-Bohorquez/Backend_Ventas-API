FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

# Copiar el archivo POM y descargar las dependencias
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Construir la aplicación
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Imagen final
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency

# Copiar las dependencias
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Configurar el punto de entrada
ENTRYPOINT ["java","-cp","app:app/lib/*","com.sistema.ventas.app.VentasApiApplication"]
