FROM maven:3.3.9-jdk-8
MAINTAINER Marcos Pinheiro <marcos.pinheiro@outlook.com>
EXPOSE 8080
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
ADD . /usr/src/app
ENTRYPOINT mvn spring-boot:run
