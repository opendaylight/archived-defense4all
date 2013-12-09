#!/bin/sh 
# delete start-up run levels
/etc/init.d/defense4all stop > /dev/null 2>&1
/etc/init.d/cassandra stop > /dev/null 2>&1

if [ -e /sbin/chkconfig ] ; then
	/sbin/chkconfig --del defense4all
	/sbin/chkconfig --del cassandra
elif [ -e /usr/sbin/update-rc.d ] ; then	
	/usr/sbin/update-rc.d -f defense4all remove
	/usr/sbin/update-rc.d -f cassandra remove
fi

rm -rf /var/log/defense4all  > /dev/null 2>&1

