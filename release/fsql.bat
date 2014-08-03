@echo off
java -Xms256M -Xmx1024M -Djava.ext.dirs=./dependencies -cp ./lib/fsql.jar pers.ywheel.fsql.FileSQL
exit
