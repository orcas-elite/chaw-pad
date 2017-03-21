#!/usr/bin/env bash

CUR_DIR=`pwd`


SVC_ARRAY_STRING="locust-worker locust-master grafana influxdb registry monitoringserver jmsserver"

cd kubernetes

for gensvc in `ls gen-*`
do
  gensvc=$(echo $gensvc | cut -d'.' -f1)
  SVC_ARRAY_STRING="$gensvc $SVC_ARRAY_STRING"
done

SVC_ARRAY=( $SVC_ARRAY_STRING )

for SVC in ${SVC_ARRAY[*]}
do
  kubectl delete -f $SVC.yaml
done

cd $CUR_DIR
