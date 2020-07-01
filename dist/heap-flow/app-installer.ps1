Write-HOST 'Starting deploy of Heap Flow App.'
Write-HOST $PSScriptRoot

Write-HOST 'Load Docker Proxy Container'
Write-HOST 'Stopping Proxy'
docker rm heap-flow-proxy -f
docker rmi heap-flow-proxy:1.0.0
Write-HOST 'Removed old Proxy'
docker load -i $PSScriptRoot\heap-flow-proxy.tar.gz

Write-HOST '$PSScriptRoot\heap-flow-proxy.tar.gz loaded'
docker images
Write-HOST 'Load Docker App Container'
Write-HOST 'Stopping app'
docker stop heap-flow-app
Write-HOST 'Removing old app'
docker rm heap-flow-app -f
docker rmi heap-flow-app:1.0.0
Write-HOST 'Removed old app'
docker load -i $PSScriptRoot\heap-flow-app.tar.gz

Write-HOST '$PSScriptRoot\heap-flow-app.tar.gz loaded'
docker images


Write-HOST 'Load Docker Dashboard Container'
Write-HOST 'Stopping dashboard app'
docker stop dashboard
Write-HOST 'Removing dashboard  app'
docker rm dashboard -f
docker rmi heap-flow-dashboard:1.0.0
Write-HOST 'Removed dashbaord app'

docker load -i $PSScriptRoot\heap-flow-dashboard.tar.gz

Write-HOST '$PSScriptRoot\heap-flow-dashboard.tar.gz loaded'
docker images

Write-HOST 'Load Docker Report Container'

Write-HOST 'Stopping rpt app'
docker stop heap-flow-rpt
Write-HOST 'Removing old rpt app'
docker rm heap-flow-rpt -f
docker rmi heap-flow-rpt:1.0.0
Write-HOST 'Removed old rpt app'

docker load -i $PSScriptRoot\heap-flow-rpt.tar.gz

Write-HOST '$PSScriptRoot\heap-flow-rpt.tar.gzloaded'
docker images



Write-HOST 'Starting App container.'
docker rm heap-flow-app -f
docker run   --restart=always -d --name heap-flow-app --link heap-db -p 9443:9443 -v $PSScriptRoot\logs\app:/opt/paranika/logs/ -d heap-flow-app:1.0.0
Write-HOST 'Started App container.'
Write-Host 'Wait for 5 seconds'
Start-Sleep -Seconds 5

Write-HOST 'Starting Report container.'
docker rm heap-flow-rpt -f
docker run   --restart=always -d --name heap-flow-rpt --link heap-db -p 8443:8443 -v $PSScriptRoot\logs\rpt:/opt/paranika/logs/ -d heap-flow-rpt:1.0.0
Write-HOST 'Started RPT container.'
Write-Host 'Wait for 5 seconds'
Start-Sleep -Seconds 5

Write-HOST 'Starting Dashboard container.'
docker rm dashboard -f
docker run   --restart=always -d --name dashboard -p 8080:8080 -d heap-flow-dashboard:1.0.0
Write-HOST 'Started dashboard container.'

Write-HOST 'Starting Proxy container.'
docker rm heap-flow-proxy -f
docker run --restart=always --name heap-flow-proxy --link dashboard --link heap-flow-app --link heap-flow-rpt  -p 80:80 -d  heap-flow-proxy:1.0.0

Write-HOST 'Deployment complete, Access APP at http://localhost'

Start-Sleep -Seconds 10
