#!/bin/bash

# Build bash 3.2 for SBSv2

rm -rf bash-3.2
rm -rf bash-3.2-patched
tar -xzf bash-3.2.tar.gz &&
mv bash-3.2 bash-3.2-patched &&
tar -xzf bash-3.2.tar.gz &&
tar -xzf bash-3.2-patches.tar.gz &&
(
cd bash-3.2-patched

for i in {1..39}; do patch -p0 < '../bash-3.2-patches/bash32-'$( printf %03d $i); done

CFLAGS="-O2 -mtune=i686 -s" ./configure --prefix=/opt/symbian/linux-i386 --enable-arith-for-command --enable-multibyte --enable-job-control --enable-progcomp --enable-process-substitution  --enable-readline --disable-rpath
)



