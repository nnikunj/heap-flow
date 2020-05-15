FROM centos:7
RUN yum update -y
RUN yum install java-1.8.0-openjdk-devel -y
RUN mkdir -p /opt/paranika/app/heap-flow/
COPY target/heap_flow-1.0.0.jar /opt/paranika/app/heap-flow/
COPY start-rest /usr/local/bin
RUN  chmod +x /usr/local/bin/start-rest

EXPOSE 9443
ENTRYPOINT  start-rest
