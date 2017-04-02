# Copyright (C) 2014 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Extremely basic script to kexec over the network"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://net-kexec.sh"

PR = "r1"

RDEPENDS_${PN} += "kexec"

do_install() {
        install -d ${D}/sbin
        install -m 0755 ${WORKDIR}/net-kexec.sh ${D}/sbin/net-kexec
}

inherit allarch

FILES_${PN} += "/sbin/net-kexec"
