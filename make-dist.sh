#!/bin/sh

rm -rf dist/heap-flow/heap-flow-db.tar.gz
rm -rf dist/heap-flow/heap-flow-app.tar.gz
rm -rf dist/heap-flow/heap-flow-dashboard.tar.gz
rm -rf dist/heap-flow/heap-flow-rpt.tar.gz

cp db-container/target/heap-flow-db.tar.gz dist/heap-flow/heap-flow-db.tar.gz 
cp heap_flow/target/heap-flow-app.tar.gz dist/heap-flow/heap-flow-app.tar.gz 
cp heap_flow_dashboard/heap-flow-dashboard/dist/heap-flow-dashboard.tar.gz  dist/heap-flow/heap-flow-dashboard.tar.gz
cp heap_flow_reports/heap_flow_reports/target/heap-flow-rpt.tar.gz dist/heap-flow/heap-flow-rpt.tar.gz
zip heap-flow.zip dist/heap-flow/*
echo 'created zip'
