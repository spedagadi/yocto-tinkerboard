# Copyright (C) 2016 Romain Perier
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://github.com/radxa/linux-rockchip.git;branch=radxa-stable-3.0 \
    file://0001-arm-mach-rk3188-Fix-relative-path-for-dependencies.patch \
    file://0002-Some-other-fixes.patch"
SRCREV = "${AUTOREV}"
PV = "3.0.36"
DEPENDS = "prebuilt-gcc-linaro"
COMPATIBLE_MACHINE = "(radxarock)"
KBUILD_DEFCONFIG = "radxa_rock_linux_defconfig"
# kernel 3.0.x does not have devicetree
KERNEL_DEVICETREE = ""

# This kernel only builds and works with the gcc-4.x toolchain. As, we only want
# to use this toolchain for this recipe, we overwrite CROSS_COMPILE, CC, LD and
# AR.
export CROSS_COMPILE = "arm-linux-gnueabi-"
KERNEL_CC = "${CROSS_COMPILE}gcc"
KERNEL_LD = "${CROSS_COMPILE}ld.bfd"
KERNEL_AR = "${CROSS_COMPILE}ar"

deltask kernel_configme

# The rockchip kernel for mach-rk3188 does not support out-of-tree build,
# mainly because the Makefile includes relative path to the parent directory
# for most of its dependencies (mach-rk30 and plat-rk). By doing so, it breaks
# the out-of-tree build. Even by patching the Makefile to use "realpath", it is
# very difficult to have a successful build (we get strange link errors). So we
# create symlinks, to put dependencies into the same directory.
do_configure_append() {
    MODULES="ddr.c ddr_reg_resume.inc pmu.c reset.c pm.c common.c devices.c \
             platsmp.c headsmp.S hotplug.c cpuidle.c"
    for m in ${MODULES}; do
        ln -sf ${S}/arch/arm/mach-rk30/${m} ${S}/arch/arm/mach-rk3188/${m}
    done
    ln -sf ${S}/arch/arm/plat-rk/clock.c ${S}/arch/arm/mach-rk3188/clock.c
}

do_install_prepend() {
    install -d ${D}/lib/firmware
}
