docker rm heap-db -f
docker run --restart=always -d --name heap-db -p 8181:3306 -v /Users/nnikunj/scratch/docker-db-store-new/:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=!HK06IS033 -d heap-flow-db:1.0.0 

