#!/bin/sh


docker build . --tag heap-flow-proxy:1.0.0
rm -rf target/heap-flow-proxy.tar.gz
docker save heap-flow-proxy:1.0.0 | gzip > target/heap-flow-proxy.tar.gz
