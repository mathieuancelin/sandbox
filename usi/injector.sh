#!/bin/bash
#http://192.168.86.4:8080/api/user
for i in {1..5000}
do
    NOW=`date +"%N"`
    curl --silent -d "{ firstname:'maurice$i', lastname:'plutanfiard$i', mail:'maurice$NOW.plutanfiard@gmail.com', password:'xxxxx' }" http://192.168.86.137:8080/api/user
    #echo call $i 
done
