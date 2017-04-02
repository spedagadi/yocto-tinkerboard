# Copyright (C) 2017 Jacob Chen
# Released under the MIT license (see COPYING.MIT for the terms)

SOC_FAMILY = "rk3036"

require conf/machine/include/tune-cortexa7.inc
require conf/machine/include/soc-family.inc

PREFERRED_PROVIDER_virtual/kernel = "linux"
SERIAL_CONSOLES = "115200;ttyS2"
KERNEL_IMAGETYPE = "zImage"
KBUILD_DEFCONFIG = "multi_v7_defconfig"

PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-rockchip"
SPL_BINARY ?= "u-boot-spl-nodtb.bin"

IMAGE_FSTYPES = "ext4 rockchip-gpt-img"
IMAGE_CLASSES += "rockchip-gpt-img"

GPTIMG_APPEND = "console=tty1 console=ttyS2,115200n8 rw root=/dev/mmcblk1p7 rootfstype=ext4 init=/sbin/init rootwait"
