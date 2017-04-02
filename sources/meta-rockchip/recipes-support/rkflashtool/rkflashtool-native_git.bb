# Copyright (C) 2014 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Tools for flashing Rockchip devices"
LICENSE = "ASIS"
LIC_FILES_CHKSUM = "file://rkcrc.c;beginline=1;endline=25;md5=5c55527c991502a9b86b768674cdcff5"

DEPENDS += "libusb-native"
SRC_URI="git://github.com/linux-rockchip/rkflashtool.git"
SRCREV = "${AUTOREV}"
PR = "r1"
PV = "0.1+git${SRCREV}"

inherit native

S = "${WORKDIR}/git"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}/${prefix}/bin

    for tool in rkcrc rkflashtool rkmisc rkpad rkparameters rkunpack rkunsign; do
        install -m 0755 ${S}/${tool} ${D}/${prefix}/bin/${tool}    
    done
}
