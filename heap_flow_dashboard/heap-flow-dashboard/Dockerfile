FROM centos:7

RUN yum update -y
RUN yum install epel-release -y
RUN yum install nginx -y

COPY nginx.conf /etc/nginx/nginx.conf

RUN mkdir -p /opt/paranika/app/

COPY dist/heap-flow-dashboard/ /opt/paranika/app/heap-flow-dashboard/
ENTRYPOINT ["nginx", "-g", "daemon off;"]
EXPOSE 8080 


