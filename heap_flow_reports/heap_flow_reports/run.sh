docker rm heap-flow-rpt -f
docker run --restart=always --name heap-flow-rpt --link heap-db  -p 8443:8443 -d -v /opt/paranika/logs/:/opt/paranika/logs/  heap-flow-rpt:1.0.0
