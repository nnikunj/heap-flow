#!/bin/sh

mvn clean install -DskipTests=true 

docker build . --tag heap-flow-rpt:1.0.0
rm -rf target/heap-flow-rpt.tar.gz
docker save heap-flow-rpt:1.0.0 | gzip > target/heap-flow-rpt.tar.gz
