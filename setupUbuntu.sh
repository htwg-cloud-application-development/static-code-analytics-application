#!/bin/bash

# local before clone repo
#sudo apt-get update
#sudo apt-get install git

# java 8
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
sudo apt-get install oracle-java8-set-default

# maven
sudo apt-get install maven

# swap 
# https://www.build-business-websites.co.uk/java-memory-allocation-failures-due-to-insufficient-swap-space/
sudo fallocate -l 1G /swapfile
ls -lh /swapfile
sudo chmod 600 /swapfile
ls -lh /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile