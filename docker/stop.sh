#!/bin/bash
export KAFKA_ADVERTISED_HOST_NAME=$(ip a | sed -En 's/127.0.0.1//;s/172.*.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p')
echo "Host IP ${KAFKA_ADVERTISED_HOST_NAME}"
echo "Stop kafka"
docker-compose stop
