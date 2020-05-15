#!/bin/sh

mvn clean install -DskipTests=true 

docker build . --tag heap-flow-app:1.0.0
rm -rf target/heap-flow-app.tar.gz
docker save heap-flow-app:1.0.0 | gzip > target/heap-flow-app.tar.gz
