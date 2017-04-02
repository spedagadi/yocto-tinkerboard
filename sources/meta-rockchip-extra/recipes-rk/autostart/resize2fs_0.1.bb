SUMMARY = "Resize root filesystem to fit available disk space"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
	file://resize-helper \
	file://resize-helper.sh.in \
"
S = "${WORKDIR}"

RDEPENDS_${PN} += "e2fsprogs-resize2fs gptfdisk parted util-linux udev"

inherit update-rc.d

do_install () {

	install -d ${D}${sbindir}
	install -m 0755 ${S}/resize-helper ${D}${sbindir}

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/resize-helper.sh.in ${D}${sysconfdir}/init.d/resize-helper.sh

	sed -i -e "s:@bindir@:${bindir}:; s:@sbindir@:${sbindir}:; s:@sysconfdir@:${sysconfdir}:" \
			${D}${sysconfdir}/init.d/resize-helper.sh
}

INITSCRIPT_NAME = "resize-helper.sh"
INITSCRIPT_PARAMS = "start 22 5 3 ."