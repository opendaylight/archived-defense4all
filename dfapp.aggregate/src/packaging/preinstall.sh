#!/bin/sh
getent group defense4all >/dev/null || groupadd -r defense4all
getent passwd defense4all >/dev/null || \
useradd -d /usr/share/defense4all -g defense4all -M -r defense4all
exit 0
