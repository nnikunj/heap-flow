docker rm heap-flow-app -f
docker run --restart=always --name heap-flow-app --link heap-db  -p 9443:9443 -d -v /opt/paranika/logs/:/opt/paranika/logs/ -v /opt/paranika/config/:/opt/paranika/config/ heap-flow-app:1.0.0
