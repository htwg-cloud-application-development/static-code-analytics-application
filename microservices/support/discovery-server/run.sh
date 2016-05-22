#!/bin/bash

help ()
{
	echo "Usage: ./run.sh [OPTIONS]"
	echo ""
	echo "excample call: ./run -l"
	echo ""
    echo " NOPARAM      run service local on port 8761"
	echo " -a           run service on aws"
	echo " -h           help"
}

ARGS=`getopt -o hla -- "$@"`

eval set -- "$ARGS"

argument=0

while true;
do
	case "$1" in
		'-a')
			mvn spring-boot:run -Dspring.profiles.active=aws
			argument=1
			break;;
		'-h')
			help
			argument=1
			break;;
		*)
			break;;
	esac
done

if [ $argument -eq 0 ]
then
	mvn spring-boot:run
fi