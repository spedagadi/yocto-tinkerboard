# Copyright (C) 2015 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit base

DESCRIPTION = "Chromium OS Broadcom patchram utility"
HOMEPAGE = "https://chromium.googlesource.com/chromiumos/third_party/broadcom/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.broadcom;md5=4ff9e0bf45dfdad0b7756c07b65ed24a"
S = "${WORKDIR}/git/bluetooth"
SECTION = "console/utils"

DEPENDS = "bluez5"
PR = "r1"
PV = "git+4070e71"

SRCREV = "4070e7161f2f1a1a22027a744eb868500688f0b6"
SRC_URI = "git://chromium.googlesource.com/chromiumos/third_party/broadcom;protocol=http;branch=master"

FILES_${PN} = "/usr/bin/*"

do_install_append() {
	install -d ${D}/usr/bin
	install -m 755 brcm_patchram_plus ${D}/usr/bin 
}

