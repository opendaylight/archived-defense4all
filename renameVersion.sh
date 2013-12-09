#!/bin/sh

version_from="0.0.1-SNAPSHOT"
version_to="1.0.0"

grep $version_from `find . -name "*xml"` > /tmp/list
ff_list=`cat /tmp/list | awk -F':' '{print $1}' | uniq`
for ff in $ff_list
do 
    echo $ff
    sed -i -e "s/${version_from}/${version_to}/g" $ff
done
