#!/bin/bash

# Build bash 3.2 for SBSv2

rm -rf make-3.81
export MAKE=$(which make)
tar -xjf make-3.81.tar.bz2 &&
(
cd make-3.81
CFLAGS="-O2 -mtune=i686" ./configure --prefix=/opt/symbian/linux-i386 --disable-job-server
$MAKE -j8 
)



