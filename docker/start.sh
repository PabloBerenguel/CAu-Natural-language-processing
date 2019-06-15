#!/bin/bash

rm -rdf /root/nlp

export JAVA_HOME=/root/jdk-11
export PATH=$PATH:/root/jdk-11/bin:/root/apache-maven-3.6.1/bin

git clone https://git.sanchezm.fr/mathieu/CAU-NLP-Spring-2019-PQ-Man-Server.git /root/nlp
cd /root/nlp/CAu_NLP_2019

mvn assembly:assembly

java -jar target/CAu_NLP_2019-1.0-SNAPSHOT-jar-with-dependencies.jar
