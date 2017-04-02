# Copyright (C) 2017 Fuzhou Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

SOC_FAMILY = "rv1108"

require conf/machine/include/tune-cortexa7.inc
require conf/machine/include/soc-family.inc

PREFERRED_PROVIDER_virtual/kernel = "linux"
SERIAL_CONSOLES = "1500000;ttyS2"
KERNEL_IMAGETYPE = "zImage"
KBUILD_DEFCONFIG = "multi_v7_defconfig"
