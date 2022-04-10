#!/bin/sh

cd ../resources/kafka
docker-compose down
docker-compose up -d

