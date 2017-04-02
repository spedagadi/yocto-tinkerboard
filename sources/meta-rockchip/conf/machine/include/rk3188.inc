# Copyright (C) 2015 Romain Perier
# Released under the MIT license (see COPYING.MIT for the terms)

SOC_FAMILY  = "rk3188"

require conf/machine/include/tune-cortexa9.inc
require conf/machine/include/soc-family.inc
PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"

XSERVER = "xserver-xorg \
           xf86-video-fbturbo \
           xf86-input-evdev \
           xf86-input-mouse \
           xf86-input-keyboard"

PREFERRED_PROVIDER_virtual/kernel = "linux-rockchip-3.0"

KERNEL_IMAGETYPE = "zImage"

IMAGE_FSTYPES += "ext4"

SERIAL_CONSOLES ?= "115200;ttyFIQ0"
KBUILD_DEFCONFIG = "multi_v7_defconfig"
