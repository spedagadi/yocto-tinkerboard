# Copyright (C) 2015 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-graphics/xorg-driver/xorg-driver-video.inc

DESCRIPTION = "X.org graphics driver for ARM graphics - Rockchip"
HOMEPAGE = "https://github.com/mmind/xf86-video-armsoc"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=10ce5de3b111315ea652a5f74ec0c602"

PE = ""
PR = "r1"
PV = "261+git+67d4cff"

RDEPENDS_${PN} += "xserver-xorg-module-exa"
S = "${WORKDIR}/git"
SRCREV = "67d4cffe67737458716116498a67546291def000"
SRC_URI = "git://github.com/mmind/xf86-video-armsoc.git;protocol=http;branch=devel/rockchip \
	   file://20-armsoc.conf \
"

do_install_append() {
	install -d ${D}/etc/X11/xorg.conf.d
	install -m 644 ${WORKDIR}/20-armsoc.conf ${D}/etc/X11/xorg.conf.d
}
