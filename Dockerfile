FROM openjdk:11
EXPOSE 8080
ADD target/rnd.pdf.jar rnd.pdf.jar
ENTRYPOINT ["java","-jar","/rnd.pdf.jar"]
