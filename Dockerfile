#Clones the repo from https://github.com/rkapa2006/EmiCalculator.git
#Builds EmiCalculator Application using mvn clean install
#Then runs EmiCalculator Application using Jenkins

#start from docker machine base image
FROM rkapa2006/dockermachine:0.1

MAINTAINER Ramesh B Kapa (rkapa2006@yahoo.com)

#Clone git project
RUN git clone https://github.com/rkapa2006/EmiCalculator.git

#change to EmiCalculator Directory
WORKDIR EmiCalculator

#Build EmiCalculator Maven Project
RUN mvn clean install

EXPOSE 8080
#Deploy and run EmiCalculator Web Application
CMD mvn jetty:run-war >> server.log && tail -f server.log
