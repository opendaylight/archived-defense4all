#!/bin/bash
#
# Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
#
# This program and the accompanying materials are made available under the terms of the Eclipse Public License
# v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
# @author Konstantin Pozdeev
# @version 0.1
#

#
# /etc/init.d/defense4all
#
# chkconfig: 2345 90 15
# description: Starts and stops defense4all
### BEGIN INIT INFO
# Provides: defense4all
# Required-Start: cassandra
# Required-Stop: cassandra
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
### END INIT INFO



NAME=defense4all
PIDFILE=/var/run/$NAME/$NAME.pid
SCRIPTNAME=/etc/init.d/$NAME
DEFENSE4ALL_HOME=/usr/share/$NAME
OUTFILE=/var/log/$NAME/output.log
DEFENSE4ALL_FIFO=/var/tmp/rsyslog-df-pipe
DEFENSE4ALL_USER=defense4all

JSVC=/usr/bin/jsvc

# If JAVA_HOME has not been set, try to determine it.
if [ -z "$JAVA_HOME" ]; then
  # Find the actual location of the Java launcher:
      java_launcher=`which java`
      java_launcher=`readlink -f "${java_launcher}"`

      # Compute the Java home from the location of the Java launcher:
      export JAVA_HOME="${java_launcher%/bin/java}"
fi
if [ -z "$JAVA_HOME" ]; then
    echo "JAVA_HOME is unknown"
    exit -1
fi
JAVA="$JAVA_HOME/bin/java"

# Find defense4all installation
if [ ! -d $DEFENSE4ALL_HOME ]; then
	echo "Defense4all doesn't seem to be installed"
	exit -1
fi	

exec_jar=`ls $DEFENSE4ALL_HOME/lib/df.aggregate*jar 2>&1`
restservice_war=`ls $DEFENSE4ALL_HOME/lib/restservice*war 2>&1`
webgui_war=`ls $DEFENSE4ALL_HOME/lib/webgui*war 2>&1`
if [ -z "$exec_jar" -o -z "$restservice_war" -o -z "$webgui_war" ]
then
   echo "Defense4all installation is not found"
   exit -1
fi

#ping cassandra
ping_cassandra() {
  echo exit | /usr/bin/cqlsh >/dev/null 2>&1
  if [ $? -ne 0 ]; then
    sleep 10
    echo exit | /usr/bin/cqlsh >/dev/null 2>&1
    if [ $? -ne 0 ]; then
      echo "Cassandra service is not responding"; exit -1
    fi
  fi
}

function usage {
    echo "Usage: $0 {start|stop|restart|status|reset [level]}"
    exit 1
}

#
# Function that returns 0 if process is running, or nonzero if not.
#
# The nonzero value is 3 if the process is simply not running, and 1 if the
# process is not running but the pidfile exists (to match the exit codes for
# the "status" command; see LSB core spec 3.1, section 20.2)
#
CMD_PATT="df.aggregate"
is_running()
{
    if [ -f $PIDFILE ]; then
        pid=`cat $PIDFILE`
        grep -Eq "$CMD_PATT" "/proc/$pid/cmdline" 2>/dev/null && return 0
        rm -f $PIDFILE
        return 1
    fi
    return 2
}

#
# Function that starts the daemon/service
#
do_start()
{
	[ -e `dirname "$PIDFILE"` ] || \
        install -d -odefense4all -gdefense4all -m755 `dirname $PIDFILE`
	
	if [ -e $PIDFILE ]; then
		daemonpid=`cat $PIDFILE`
    	ps -p ${daemonpid} > /dev/null 2>&1
    	daemonexists=$?
    	if [ "${daemonexists}" -eq 0 ]; then
    	return 1
    	fi
	fi
	
	ping_cassandra
    
    rm -rf $DEFENSE4ALL_FIFO  2>&1 > /dev/null
    mkfifo $DEFENSE4ALL_FIFO  2>&1 > /dev/null
    service rsyslog restart   2>&1 > /dev/null
    
    
	
    #$JSVC \
        # -user defense4all \
        # -home $JAVA_HOME \
        # -pidfile $PIDFILE \
        # -errfile "&1" \
        # -outfile /var/log/$NAME/output.log \
        # -DrestWarPath=$restservice_war \
        # $JVM_OPTS \
        # -jar $exec_jar 
	
	su $DEFENSE4ALL_USER -c "/usr/bin/nohup $JAVA_HOME/bin/java \
                                 -DrestWarPath=$restservice_war \
                                 -DguiWarPath=$webgui_war \
                                 -jar $exec_jar >> $OUTFILE 2>&1 & \
                                  echo \$! > $PIDFILE"
    
    is_running && return 0 || return 2
     	  
}

do_reset()
{
	if [ -e $PIDFILE ]; then
        daemonpid=`cat $PIDFILE`
        ps -p ${daemonpid} > /dev/null 2>&1
        daemonexists=$?
        if [ "${daemonexists}" -eq 0 ]; then
            return 1
        fi
    fi
    
    echo "Reset level: $reset_level"
    
    rm -rf $DEFENSE4ALL_FIFO  2>&1 > /dev/null
    mkfifo $DEFENSE4ALL_FIFO  2>&1 > /dev/null
    
	su $DEFENSE4ALL_USER -c "$JAVA_HOME/bin/java \
							-DrestWarPath=$restservice_war \
                            -jar $exec_jar reset $reset_level"
	return $?
}

#
# Function that stops the daemon/service
#
do_stop()
{
	# Return
	#   0 if daemon has been stopped
	#   1 if daemon was already stopped
	#   2 if daemon could not be stopped
	#   other if a failure occurred
    is_running || return 1
    
    if [ -e $PIDFILE ]; then
        daemonpid=`cat $PIDFILE`
        kill "${daemonpid}"
        rm -f $PIDFILE
        return 0
	fi
       
    is_running && return 2 || return 0
}

case "$1" in
  start)
	do_start
	case "$?" in
		0) echo "$NAME is running" ;;  
		1) echo "$NAME is already running!";;
		2) echo "$NAME is not running" ;;
	esac
	;;
  stop)
	do_stop
	stat=$?
	case "$stat" in
		0) echo "$NAME has been stopped" ;;
		1) echo "$NAME is not running" ;;
		2) echo "$NAME could not be stopped" ;;
	esac
	exit "$stat"	
	;;
  restart)
	do_stop
	case "$?" in
		0) echo "$NAME has been stopped" ;;
		1) echo "$NAME is not running" ;;
		2) echo "$NAME could not be stopped"; exit 2 ;;
	esac
	do_start
	stat=$?
	case "$stat" in
		0) echo "$NAME is running" ;;
		1) echo "$NAME is already running" ;;
		2) echo "$NAME is not running" ;;
	esac
	exit "$stat"
	;;
  status)
    is_running
    stat=$?
    case "$stat" in
    	0) echo "$NAME is running" ;;
    	1) echo "could not access pidfile for $NAME" ;;
    	*) echo "$NAME is not running" ;;
    esac
    exit "$stat"
    ;;
  reset)
	reset_level=$2
	if [ -z "$reset_level" ]; then
		usage >&2
		exit -1
	fi	
	do_reset
	stat=$?
    case "$stat" in
    	1) echo "$NAME should be stopped before" ;;
    esac
    exit $stat
    ;;
  *)
	usage >&2
	;;
esac
