#!/bin/sh
docker stop heap-db
docker rmi heap-flow-db:1.0.0 -f 
rm -rf ~/scratch/docker-db-store

