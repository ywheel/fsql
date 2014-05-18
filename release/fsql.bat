@echo off
set CLASSPATH=.\fsql.jar;.\lib\spring-2.0.jar;.\lib\log4j-1.2.15.jar;.\lib\hsqldb.jar;.\lib\commons-logging-1.1.3.jar
java -Xms256m -Xmx512m pers.ywheel.fsql.FileSQL
exit