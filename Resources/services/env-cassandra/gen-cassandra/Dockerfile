FROM java:openjdk-8

EXPOSE 8080

COPY target/cassandra-1.4.jar /

CMD java -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=* -jar cassandra-1.4.jar
