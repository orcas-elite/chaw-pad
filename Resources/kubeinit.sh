#!/usr/bin/env bash

SVC_ARRAY_STRING="jmsserver monitoringserver influxdb grafana registry locust-master locust-worker"

CUR_DIR=`pwd`

cd kubernetes

for gensvc in `ls gen-*`
do
  gensvc=$(echo $gensvc | cut -d'.' -f1)
  SVC_ARRAY_STRING="$SVC_ARRAY_STRING $gensvc"
done

SVC_ARRAY=( $SVC_ARRAY_STRING )
echo ${SVC_ARRAY[*]}

function getPorts() {
  kubectl describe svc $1 | grep "^NodePort:" | cut -f 3,4 | cut -d'/' -f1
}

for SVC in ${SVC_ARRAY[*]} 
do
  SVC_NAME=`echo $SVC | cut -d'.' -f1`
  echo "$SVC.yaml"
  kubectl create -f ${SVC}.yaml
  echo ""
  sleep 3
done

echo "jmsserver"
getPorts jmsserver
echo ""

echo "monitoringserver"
getPorts monitoringserver
echo ""

echo "locust-master"
getPorts locust-master
echo ""

echo "gen-elb"
getPorts gen-elb
echo ""
