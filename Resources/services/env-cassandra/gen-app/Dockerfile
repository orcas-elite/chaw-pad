FROM java:openjdk-8

EXPOSE 8080

COPY target/app-1.2.jar /

CMD java -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=* -jar app-1.2.jar
