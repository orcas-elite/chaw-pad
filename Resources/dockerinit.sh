#!/usr/bin/env bash

CUR_DIR=`pwd`
BASE="duelle/edcc"

eval $(minikube docker-env)

cd services
SVC_DIR=`pwd`

cd jmsserver
docker build -t ${BASE}:jmsserver .
docker push ${BASE}:jmsserver
cd $SVC_DIR

cd monitoringserver
mvn clean compile assembly:single
docker build -t ${BASE}:monitoringserver .
docker push ${BASE}:monitoringserver
cd $SVC_DIR

cd registry 
mvn clean package
docker build -t ${BASE}:registry .
docker push ${BASE}:registry
cd $SVC_DIR

cd locust
docker build -t ${BASE}:locust .
docker push ${BASE}:locust
cd $SVC_DIR

cd env-cassandra
GEN_DIR=`pwd`

cd gen-cassandra
mvn clean package
docker build -t ${BASE}:cassandra .
docker push ${BASE}:cassandra
cd $GEN_DIR

cd gen-restdata
mvn clean package
docker build -t ${BASE}:restdata .
docker push ${BASE}:restdata
cd $GEN_DIR

cd gen-app
mvn clean package
docker build -t ${BASE}:app .
docker push ${BASE}:app
cd $GEN_DIR

cd gen-proxy
mvn clean package
docker build -t ${BASE}:proxy .
docker push ${BASE}:proxy
cd $GEN_DIR

cd gen-elb
mvn clean package
docker build -t ${BASE}:elb .
docker push ${BASE}:elb
cd $GEN_DIR

cd $CUR_DIR

