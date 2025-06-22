# Usa imagem oficial do OpenJDK 17 (leve e otimizada)
FROM eclipse-temurin:17-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado pelo Maven para dentro do container
COPY target/agroforte-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão da aplicação Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]
