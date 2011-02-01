#!/bin/bash
for i in {1..1000}
do
    curl --silent -d "{ firstname:'maurice$i', lastname:'plutanfiard$i', mail:'maurice$i.plutanfiard@gmail.com', password:'xxxxx' }" http://localhost:8080/api/user
    #echo call $i 
done
