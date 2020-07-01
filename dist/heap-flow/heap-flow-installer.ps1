Write-HOST 'Starting deploy of Heap Flow App.'
Write-HOST $PSScriptRoot

Write-HOST 'create required directories'

If(!(test-path "$PSScriptRoot\db-store"))
{
      New-Item -Path "$PSScriptRoot\" -Name "db-store" -ItemType "directory"
}
ELSE
{
    Write-HOST '$PSScriptRoot\db-store already existing.'
}

If(!(test-path "$PSScriptRoot\logs"))
{
     New-Item -Path "$PSScriptRoot\" -Name "logs" -ItemType "directory"
}
ELSE
{
    Write-HOST '$PSScriptRoot\logs already existing.'
}

If(!(test-path "$PSScriptRoot\logs\app"))
{
     New-Item -Path "$PSScriptRoot\logs" -Name "app" -ItemType "directory"
}
ELSE
{
    Write-HOST '$PSScriptRoot\logs\app already existing.'
}

If(!(test-path "$PSScriptRoot\logs\rpt"))
{
     New-Item -Path "$PSScriptRoot\logs" -Name "rpt" -ItemType "directory"
}
ELSE
{
    Write-HOST '$PSScriptRoot\logs\rpt already existing.'
}

Write-HOST 'Load Docker DB Container'
docker load -i $PSScriptRoot\heap-flow-db.tar.gz

Write-HOST '$PSScriptRoot\heap-flow-db.tar.gz loaded'
docker images

Write-HOST 'Load Docker App Container'
docker load -i $PSScriptRoot\heap-flow-app.tar.gz

Write-HOST '$PSScriptRoot\heap-flow-app.tar.gz loaded'
docker images

Write-HOST 'Load Docker Dashboard Container'
docker load -i $PSScriptRoot\heap-flow-dashboard.tar.gz

Write-HOST '$PSScriptRoot\heap-flow-dashboard.tar.gz loaded'
docker images

Write-HOST 'Load Docker Report Container'
docker load -i $PSScriptRoot\heap-flow-rpt.tar.gz

Write-HOST '$PSScriptRoot\heap-flow-rpt.tar.gzloaded'
docker images

Write-HOST 'Load Docker Proxy Container'
Write-HOST 'Stopping Proxy'
docker rm heap-flow-proxy -f
docker rmi heap-flow-proxy:1.0.0
Write-HOST 'Removed old Proxy'
docker load -i $PSScriptRoot\heap-flow-proxy.tar.gz

Write-HOST 'Starting DB container.'
docker rm heap-db -f
docker run --restart=always -d --name heap-db -p 3306:3306 -v $PSScriptRoot\db-store:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=!HK06IS033 -d heap-flow-db:1.0.0
Write-HOST 'Started DB container.'
Write-Host 'Wait for 30 seconds'
Start-Sleep -Seconds 30

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
docker run   --restart=always -d --name dashboard -p 80:8080 -d heap-flow-dashboard:1.0.0
Write-HOST 'Started dashboard container.'
Write-HOST 'Deployment complete, Access APP at http://localhost'

Write-HOST 'Starting Proxy container.'
docker rm heap-flow-proxy  -f
docker run --restart=always --name heap-flow-proxy --link dashboard --link heap-flow-app --link heap-flow-rpt  -p 80:80 -d  heap-flow-proxy:1.0.0

Start-Sleep -Seconds 10
