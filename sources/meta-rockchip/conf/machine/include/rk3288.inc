# Copyright (C) 2015 Romain Perier
# Released under the MIT license (see COPYING.MIT for the terms)

SOC_FAMILY = "rk3288"

require conf/machine/include/tune-cortexa17.inc
require conf/machine/include/soc-family.inc

PREFERRED_PROVIDER_virtual/kernel ?= "linux"
SERIAL_CONSOLES = "115200;ttyS2"
KERNEL_IMAGETYPE = "zImage"
KBUILD_DEFCONFIG = "multi_v7_defconfig"

PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-rockchip"
SPL_BINARY ?= "u-boot-spl-dtb.bin"

IMAGE_FSTYPES = "ext4 rockchip-gpt-img"
IMAGE_CLASSES += "rockchip-gpt-img"
