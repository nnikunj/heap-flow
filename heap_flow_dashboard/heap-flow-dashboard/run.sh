#!/bin/bash
docker rm dashboard -f
docker run   --restart=always -d --name dashboard -p 8080:8080 -d heap-flow-dashboard:1.0.0
