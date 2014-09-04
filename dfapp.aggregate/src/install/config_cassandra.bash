#!/bin/bash
#
# Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
#
# This program and the accompanying materials are made available under the terms of the Eclipse Public License
# v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
# @author Konstantin Pozdeev
# @version 0.1
#
echo "Configuring cassandra cluster for defense4all"

cassandra_conf=/etc/cassandra

# check existence of cassandra config directory
if [ ! -d  $cassandra_conf ]
then
  echo "$cassandra_conf directory doesn't exist"
  exit -1
fi

# Find cassandra configuration file
# different version of cassandra has it in different location
cassandra_yaml=`find $cassandra_conf -name cassandra.yaml`
if [ -z $cassandra_yaml ]; then
  echo "Can't find  cassandra.yaml file"
  exit -1
fi

# backup original configuration file
if [ ! -f ${cassandra_yaml}.org ]; then
  cp  ${cassandra_yaml} ${cassandra_yaml}.org
fi

# Stop cassandra service
/etc/init.d/cassandra stop > /dev/null
if [ $? -ne 0 ]; then
  echo "Failed to stop cassandra service"
  exit -1
fi

# edit configuration file
chmod +w $cassandra_yaml
cat $cassandra_yaml | sed -e "s/^#* *cluster_name:.*/cluster_name: 'df_cluster'/" > /tmp/cassandra.yaml
mv -f /tmp/cassandra.yaml $cassandra_yaml
cat $cassandra_yaml | sed -e "s/num_tokens:.*/num_tokens: 1/" > /tmp/cassandra.yaml
mv -f /tmp/cassandra.yaml $cassandra_yaml
cat $cassandra_yaml | sed -e "s/^#* *num_tokens:.*/num_tokens: 1/" > /tmp/cassandra.yaml
mv -f /tmp/cassandra.yaml $cassandra_yaml
cat $cassandra_yaml | sed -e "s/^#* *initial_token:.*/initial_token: 0/" > /tmp/cassandra.yaml
mv -f /tmp/cassandra.yaml $cassandra_yaml
cat $cassandra_yaml | sed -e "s/^#* *key_cache_size_in_mb:.*/key_cache_size_in_mb: 0/" > /tmp/cassandra.yaml
mv -f /tmp/cassandra.yaml $cassandra_yaml

# clean-up saved configuration
rm -rf /var/lib/cassandra/data/*

# Start cassandra service
/etc/init.d/cassandra start > /dev/null
if [ $? -ne 0 ]; then
  echo "Failed to start cassandra service"
  exit -1
fi
