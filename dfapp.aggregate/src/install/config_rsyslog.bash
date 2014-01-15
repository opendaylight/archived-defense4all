#!/bin/bash
#
# Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
#
# This program and the accompanying materials are made available under the terms of the Eclipse Public License
# v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
# @author Konstantin Pozdeev
# @version 0.1
#

echo "Configuring rsyslog service for defense4all"

rsyslog_conf=/etc/rsyslog.conf
local_conf=/etc/rsyslog.d/50-default.conf

# check existence of cassandra config directory
if [ ! -f  $rsyslog_conf ]
then
  echo "$rsyslog_conf file doesn't exist"
  exit -1
fi

# backup original configuration file
if [ ! -f ${rsyslog_conf}.org ]; then
  cp  ${rsyslog_conf} ${rsyslog_conf}.org
fi

# edit configuration file
chmod +w $rsyslog_conf
cat $rsyslog_conf | sed -e "s/^#* *\$ModLoad imudp.*/\$ModLoad imudp/" > /tmp/rsyslog_conf
cp -f /tmp/rsyslog_conf $rsyslog_conf
cat $rsyslog_conf | sed -e "s/^#* *\$UDPServerRun.*/\$UDPServerRun 514/" > /tmp/rsyslog_conf
cp -f /tmp/rsyslog_conf $rsyslog_conf

# find configuration file for rsyslogd incoming connections
if [ ! -f $local_conf ]
then 
  local_conf=$rsyslog_conf
  append_line="Local6.* /var/tmp/rsyslog-pipe"
else
  append_line="Local6.* |/var/tmp/rsyslog-pipe" 
fi

# backup original configuration file
if [ ! -f ${local_conf}.org ]; then
  cp  ${local_conf} ${local_conf}.org
fi

# Append line to configuration if not already exists
grep "^Local6"  ${local_conf} > /dev/null
if [ $? -ne 0 ]
then
    echo $append_line >> ${local_conf}
fi

# re-Start rsyslog service
/etc/init.d/rsyslog  stop  > /dev/null
/etc/init.d/rsyslog  start  > /dev/null
if [ $? -ne 0 ]; then
  echo "Failed to start rsyslog service"
  exit -1
fi