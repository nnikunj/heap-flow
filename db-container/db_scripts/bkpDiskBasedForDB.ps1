#Stop UI container so that no request could come while bkp is in progress.
docker stop dashboard
#Wait for 5 seconds such that all pending txn could be completed.
Start-Sleep -Seconds 5
#Stop Db container before taking bkp
docker stop heap-db

$srcDir = ""
$destDir = ""

Compress-Archive -Path $srcDir -DestinationPath $destDir\db-disk-$(get-date -f yyyy-MMM-dd).zip
docker start heap-db
Start-Sleep -Seconds 5
docker exec heap-db /usr/bin/mysqldump -u root --password=!HK06IS033 --databases heapdb |  Set-Content $destDir\db-backup$(get-date -f yyyy-MMM-dd).sql
Start-Sleep -Seconds 5
docker start dashboard