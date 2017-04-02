# Copyright (C) 2015 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit allarch

SUMMARY = "Extremely basic init script which starts Petitboot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://init-boot-petitboot.sh"

do_install() {
    install -d ${D}/dev
    mknod -m 600 ${D}/dev/console c 5 1
    mknod -m 666 ${D}/dev/null c 1 3
    install -m 0755 ${WORKDIR}/init-boot-petitboot.sh ${D}/init
}

FILES_${PN} += " /init /dev/console /dev/null "

