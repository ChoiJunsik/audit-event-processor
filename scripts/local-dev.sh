#!/bin/sh

cd ../resources/local-dev

docker-compose down
docker-compose up -d

cd ./kafka-cluster
docker-compose down
echo "INTERNAL_IP=$(ifconfig | grep "inet " | grep -Fv 127.0.0.1 | awk '{print $2}')" > .env
docker-compose up