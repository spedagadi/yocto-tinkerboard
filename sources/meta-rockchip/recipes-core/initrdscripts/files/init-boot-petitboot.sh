#!/bin/sh
# Copyright (C) 2015 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

PATH=/sbin:/bin:/usr/sbin:/usr/bin

mount -t proc proc /proc
mount -t sysfs sysfs /sys
mount -t devtmpfs dev /dev
mkdir /var/volatile/log /var/volatile/tmp
/lib/udev/udevd --daemon
pb-discover --verbose -l /var/log/pb-discover.log &
udevadm trigger --action=add
udevadm settle

clear
export TERM=screen
petitboot-nc
