Write-HOST 'Taking DB backup.'
docker exec heap-db /usr/bin/mysqldump -u root --password=\!HK06IS033  heapdb > db-backup$(get-date -f yyyy-MMM-dd).sql
Write-HOST 'DB backup complete.'
