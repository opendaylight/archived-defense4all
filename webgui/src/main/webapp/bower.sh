#!/bin/sh

BASEDIR=$(dirname $(readlink -f $0))

export PATH="${BASEDIR}/node:${PATH}"

${BASEDIR}/node_modules/.bin/bower $@
