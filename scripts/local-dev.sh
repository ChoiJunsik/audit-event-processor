#!/bin/sh

cd ../resources/local-dev

docker-compose down
docker-compose up -d

docker-compose -f ./kafka/docker-compose.yml down
docker-compose -f ./kafka/docker-compose.yml up -d
