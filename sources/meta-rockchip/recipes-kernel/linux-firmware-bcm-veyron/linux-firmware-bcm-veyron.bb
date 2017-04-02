# Copyright (C) 2015 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit allarch

DESCRIPTION = "Firmware and system files for Broadcom BCM4354 to be used with Linux kernel"
LICENSE = "CLOSED"
PR = "r1"
PV = "003.001.012.0306.0659"
SECTION = "kernel"

RDEPENDS_${PN} = "brcm-patchram-plus"

SRC_URI = "http://archlinuxarm.org/builder/src/veyron/BCM4354_003.001.012.0306.0659.hcd \
	file://brcmfmac4354-sdio.txt \
	file://99-veyron-brcm.rules \
"
SRC_URI[md5sum] = "20f8931f3795e5226829d48c3d470334"
SRC_URI[sha256sum] = "45c04797a769bae3b6c73a88a2a2d5b812581f9e0b11e9f030aef8513b305995"

FILES_${PN} = "/lib/firmware/brcm/* /lib/udev/rules.d/*"

do_install() {
	install -d ${D}/lib/firmware/brcm ${D}/lib/udev/rules.d
	install -m 0644 ${WORKDIR}/BCM4354_003.001.012.0306.0659.hcd ${WORKDIR}/brcmfmac4354-sdio.txt ${D}/lib/firmware/brcm
	install -m 0644 ${WORKDIR}/99-veyron-brcm.rules ${D}/lib/udev/rules.d
}
