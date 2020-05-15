#!/bin/bash
docker rm dashboard -f
docker run   --restart=always -d --name dashboard -p 80:8080 -d heap-flow-dashboard:1.0.0
