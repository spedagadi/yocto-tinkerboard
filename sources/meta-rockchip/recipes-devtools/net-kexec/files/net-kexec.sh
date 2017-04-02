#!/bin/sh
# Copyright (C) 2014 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

GATEWAY=$(route | grep default | sed 's/ [ ]*/:/g' | cut -d ':' -f 2)

fail() {
    echo $1
    exit 1
}

# If we are not already connected, connect eth0 with DHCP
ifconfig eth0 | grep 'inet addr:[0-9]*\.[0-9]*\.[0-9]*\.[0-9]* ' >/dev/null
if [ $? -ne 0 ]; then
    echo "* Starting DHCP on eth0"
    udhcpc -n -i eth0 || fail "Unable to establish a DHCP session with eth0"
fi

tftp -g -r zImage $GATEWAY >/dev/null || fail "Unable to get zImage throught TFTP"
echo "* Found zImage on $TFTP_SERVER"
echo -n "Do you want to reboot to the new kernel on the fly ? [y/n] "
read boot

if [ "$boot" = "y" ]; then
    kexec -l zImage
    kexec -e
fi
