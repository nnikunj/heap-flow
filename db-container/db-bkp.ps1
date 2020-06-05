Write-HOST 'Taking DB backup.'
docker exec heap-db /usr/bin/mysqldump -u root --password=\!HK06IS033 -r heapdb | Set-Content db-backup.sql
Write-HOST 'DB backup complete.'
