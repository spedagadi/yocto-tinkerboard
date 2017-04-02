# Copyright (C) 2016 Romain Perier
# Released under the MIT license (see COPYING.MIT for the terms)

inherit base native

LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://include/gmp.h;beginline=1;endline=29;md5=6e0172559b628f995afd902f4920f78b"
ARM_ABI = "arm-linux-gnueabi"
VER = "${PV}-x86_64_${ARM_ABI}"
SRC_URI = "https://releases.linaro.org/components/toolchain/binaries/${PV}/${ARM_ABI}/gcc-linaro-${VER}.tar.xz"
SRC_URI[sha256sum] = "fdef102e754b2aa9e7d0b2ad465216a41b421694e1ce0cc972621214f8ac4bd5"

S = "${WORKDIR}/gcc-linaro-${VER}"

python do_fetch() {
    build_arch = d.getVar("BUILD_ARCH", True)
    if build_arch != "x86_64":
        bb.fatal("Build architecture '%s' is not supported by this package\n" % build_arch)
    bb.build.exec_func("base_do_fetch", d)
}

do_install() {
    DIRS="arm-linux-gnueabi bin lib libexec"
    for subdir in $DIRS; do
        cp -rPp ${S}/${subdir} ${STAGING_DIR_NATIVE}${prefix_native}
    done
}
