#!/bin/bash

help ()
{
	echo "Usage: ./run.sh [OPTIONS]"
	echo ""
	echo "excample call: ./run -l"
	echo ""
    echo " NOPARAM      run service local on port 1234 without eureka"
    echo " -l           run service local with random port and eureka support"
	echo " -a           run service on aws"
	echo " -h           help"
}

ARGS=`getopt -o hla -- "$@"`

eval set -- "$ARGS"

argument=0

while true;
do
	case "$1" in
		'-l')
			mvn spring-boot:run -Drun.profiles=local
			argument=1
			break;;
		'-a')
			mvn spring-boot:run -Drun.profiles=aws
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
	mvn spring-boot:run -Drun.profiles=development
fi