FROM centos:7
RUN yum update -y
RUN yum install java-1.8.0-openjdk-devel -y
RUN mkdir -p /opt/paranika/app/heap-flow-rpt/
RUN mkdir -p /opt/paranika/config/
RUN mkdir -p /opt/paranika/logs/
COPY target/heap_flow_reports-1.0.0.jar /opt/paranika/app/heap-flow-rpt/
COPY start-rest /usr/local/bin
RUN  chmod +x /usr/local/bin/start-rest

EXPOSE 8443
ENTRYPOINT  start-rest
