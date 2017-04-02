# Copyright (C) 2015 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit gettext autotools

DESCRIPTION = "Petitboot kexec bootloader"
HOMEPAGE = "https://www.kernel.org/pub/linux/kernel/people/geoff/petitboot/petitboot.html"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"
DEPENDS = "udev ncurses"
RDEPENDS_${PN} = "libudev ncurses-libncurses ncurses-libtinfo ncurses-libmenu ncurses-libform kexec udev"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/geoff/petitboot.git"
SRCREV_pn-${PN} = "e330e3f5adf78d3ba77217995a4bc3cd1fd16f4c"
PV = "git+20141211-e330e3"
S = "${WORKDIR}/git"
B = "${S}"

EXTRA_OECONF = "--enable-busybox --with-twin-x11=no --with-twin-fbdev=no"
PARALLEL_MAKE = ""

do_configure() {
	olddir=`pwd`
	cd ${S}
	./bootstrap
	cd $olddir
	export CURSES_LIB=-lncurses HOST_PROG_KEXEC=/usr/sbin/kexec
	oe_runconf
}
