docker rm heap-flow-proxy -f
docker run --restart=always --name heap-flow-proxy --link dashboard --link heap-flow-app --link heap-flow-rpt  -p 80:80 -d  heap-flow-proxy:1.0.0
