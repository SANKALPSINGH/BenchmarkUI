#!/bin/sh

mvn clean
mvn install

rm -rf /Users/test/Downloads/apache-tomcat-7.0.68/webapps/AppBenchmark
rm -rf /Users/test/Downloads/apache-tomcat-7.0.68/webapps/AppBenchmark.war

cd target
mv AppBenchmark /Users/test/Downloads/apache-tomcat-7.0.68/webapps/
mv AppBenchmark.war /Users/test/Downloads/apache-tomcat-7.0.68/webapps/

cd /Users/test/Downloads/apache-tomcat-7.0.68/bin/
./catalina.sh stop
./catalina.sh jpda run
