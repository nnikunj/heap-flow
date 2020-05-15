#!/bin/sh

ng build --aot=true --optimization=true --prod=true --extractCss=true

docker build . --tag heap-flow-dashboard:1.0.0
rm -rf dist/heap-flow-dashboard.tar.gz
docker save heap-flow-dashboard:1.0.0 | gzip > dist/heap-flow-dashboard.tar.gz
