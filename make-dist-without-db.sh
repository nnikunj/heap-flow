#!/bin/sh
echo removing old dist/heap-flow/heap-flow-db.tar.gz
rm -rf dist/heap-flow/heap-flow-db.tar.gz
echo removing old dist/heap-flow/heap-flow-app.tar.gz
rm -rf dist/heap-flow/heap-flow-app.tar.gz
echo removing old dist/heap-flow/heap-flow-dashboard.tar.gz
rm -rf dist/heap-flow/heap-flow-dashboard.tar.gz
echo removing old dist/heap-flow/heap-flow-rpt.tar.gz
rm -rf dist/heap-flow/heap-flow-rpt.tar.gz
echo removing old dist/heap-flow/heap-flow-proxy.tar.gz
rm -rf dist/heap-flow/heap-flow-proxy.tar.gz

 
cp heap_flow/target/heap-flow-app.tar.gz dist/heap-flow/heap-flow-app.tar.gz
cp heap_flow_dashboard/heap-flow-dashboard/dist/heap-flow-dashboard.tar.gz  dist/heap-flow/heap-flow-dashboard.tar.gz
cp heap_flow_reports/heap_flow_reports/target/heap-flow-rpt.tar.gz dist/heap-flow/heap-flow-rpt.tar.gz
cp db-container/db_scripts/db_schema_changes.sql   dist/heap-flow/db_schema_changes.sql
cp db-container/db-bkp.ps1 dist/heap-flow/db-bkp.ps1
cp heap_haproxy/target/heap-flow-proxy.tar.gz dist/heap-flow/heap-flow-proxy.tar.gz
zip heap-flow.zip dist/heap-flow/*
echo 'created zip'
