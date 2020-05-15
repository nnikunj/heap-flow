docker rm heap-flow-app -f
docker run   --restart=always -d --name heap-flow-app -p 9443:9443 -d heap-flow-app:1.0.0
