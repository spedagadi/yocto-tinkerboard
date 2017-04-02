# Copyright (C) 2014 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Tools to create firmware / boot images for Rockchip devices"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://mkbootimg.c;beginline=3;endline=15;md5=b18126d1bbc60ae5dbc31ef949a6f1c3"

DEPENDS += "openssl-native"
SRC_URI="git://github.com/neo-technologies/rockchip-mkbootimg.git"
SRCREV = "dcfdea23d8a223ec68f09789ebf38fc8b832e5d6"
PR = "r1"
PV = "0.1+git${SRCREV}"

inherit base native

S = "${WORKDIR}/git"

do_compile() {
    oe_runmake LDFLAGS="$LDFLAGS -lcrypto"
}

do_install() {
    oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
