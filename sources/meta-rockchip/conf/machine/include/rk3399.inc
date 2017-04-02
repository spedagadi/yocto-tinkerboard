# Copyright (C) 2017 Jacob Chen
# Released under the MIT license (see COPYING.MIT for the terms)

SOC_FAMILY = "rk3399"

require conf/machine/include/arm/arch-armv8.inc
require conf/machine/include/soc-family.inc

PREFERRED_PROVIDER_virtual/kernel = "linux"
SERIAL_CONSOLES = "1500000;ttyS2"
KERNEL_IMAGETYPE = "Image"
KBUILD_DEFCONFIG = "multi_v8_defconfig"

PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-rockchip"
SPL_BINARY ?= "u-boot-spl-nodtb.bin"

IMAGE_FSTYPES = "ext4 rockchip-gpt-img"
IMAGE_CLASSES += "rockchip-gpt-img"
