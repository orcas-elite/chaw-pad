FROM java:openjdk-8

EXPOSE 8080

COPY target/proxy-1.1.jar /

CMD java -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=* -jar proxy-1.1.jar
