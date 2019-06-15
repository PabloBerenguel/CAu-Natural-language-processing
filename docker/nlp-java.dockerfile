FROM debian:jessie-slim

COPY ./start.sh /root/start.sh

RUN apt-get update; apt-get install -y git wget;

RUN cd /root; wget https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_linux-x64_bin.tar.gz; wget http://mirror.navercorp.com/apache/maven/maven-3/3.6.1/binaries/apache-maven-3.6.1-bin.tar.gz

RUN cd /root; tar -xsvf openjdk-11+28_linux-x64_bin.tar.gz; tar -xsvf apache-maven-3.6.1-bin.tar.gz

EXPOSE 8080

ENTRYPOINT /root/start.sh
