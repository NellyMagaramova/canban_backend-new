FROM openjdk:19

# указываем docker контейнеру, по какому порту будет работать веб приложение, чтобы этот пор был доступен для маппинга
EXPOSE 8080

# компируем в контейнер jar со всеми исходниками и библиотеками внутри него
ADD build/libs/canban-backend-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=db,logging", "-jar", "/app/app.jar"]
