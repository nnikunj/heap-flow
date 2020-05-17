Write-HOST 'Starting deploy of Heap Flow App.'
Write-HOST $PSScriptRoot

$IP = Get-NetIPAddress -AddressFamily IPv4  -InterfaceAlias Ethernet0
Write-HOST $IP

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

Write-HOST 'Creating application properties as per the host.'
 
$propFile = "$PSScriptRoot\application.properties"
if (Test-Path $propFile) 
{
  Remove-Item $propFile
} 
$newFile = Get-Content "$PSScriptRoot\template-application.properties" | ForEach-Object{
    $_ -replace '#IP_ADDR',($IP)
} 
$newFile | Set-Content "$propFile"
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

Write-HOST 'Starting DB container.'
docker rm heap-db -f
docker run --restart=always -d --name heap-db -p 8181:3306 -v $PSScriptRoot\db-store:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=!HK06IS033 -d heap-flow-db:1.0.0
Write-HOST 'Started DB container.'
Write-Host 'Wait for 30 seconds'
Start-Sleep -Seconds 30
Write-HOST 'Starting App container.'
docker rm heap-flow-app -f
docker run   --restart=always -d --name heap-flow-app -p 9443:9443 -v $PSScriptRoot\application.properties:/opt/paranika/config/application.properties -v $PSScriptRoot\logs:/opt/paranika/logs/ -d heap-flow-app:1.0.0
Write-HOST 'Started App container.'
Write-Host 'Wait for 5 seconds'
Start-Sleep -Seconds 5
Write-HOST 'Starting Dashboard container.'
docker rm dashboard -f
docker run   --restart=always -d --name dashboard -p 80:8080 -d heap-flow-dashboard:1.0.0
Write-HOST 'Started dashboard container.'
Write-HOST 'Deployment complete, Access APP at http://localhost'

Start-Sleep -Seconds 10
