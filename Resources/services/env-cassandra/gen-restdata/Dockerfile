FROM java:openjdk-8

EXPOSE 8080

COPY target/restdata-1.3.jar /

CMD java -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=* -jar restdata-1.3.jar
