FROM openjdk:21
WORKDIR /usr/app

# Copia conteúdo estático (mantido)
COPY ./static_content ./static_content

# Copia o fat JAR gerado
COPY ./build/libs/2425-2-LEIC43D-G01-all.jar app.jar

# Executa o fat JAR
CMD ["java", "-jar", "app.jar"]
