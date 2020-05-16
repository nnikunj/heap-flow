#!/bin/sh


docker build . --tag heap-flow-db:1.0.0
rm -rf target/heap-flow-db.tar.gz
docker save heap-flow-db:1.0.0 | gzip > target/heap-flow-db.tar.gz
