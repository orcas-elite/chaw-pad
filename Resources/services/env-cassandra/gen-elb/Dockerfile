FROM java:openjdk-8

EXPOSE 8080

COPY target/elb-1.0.jar /

CMD java -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=* -jar elb-1.0.jar
