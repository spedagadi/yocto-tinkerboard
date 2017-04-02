# Copyright (C) 2015 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Chromium OS verified boot utilities"
HOMEPAGE = "http://git.chromium.org/gitweb/?p=chromiumos/platform/vboot_reference.git;a=summary"
SECTION = "admin"
LICENSE = "Chromium"
LIC_FILES_CHKSUM = "file://LICENSE;md5=562c740877935f40b262db8af30bca36"

DEPENDS = "openssl util-linux-native libyaml-native"
TAG = "43-6946"
SRC_URI = "git://chromium.googlesource.com/chromiumos/platform/vboot_reference;protocol=http;branch=release-R${TAG}.B \
           file://0001-host_misc-Fix-uninitialized-variable-val-in-function.patch \
           file://0002-futility-fix-warning-treated-as-error-about-uninitia.patch"
SRCREV = "9978e0aa0069697816a38c7dcc6a81be5975cab7"
S = "${WORKDIR}/git"
PARALLEL_MAKE = ""
EXTRA_OEMAKE = ""

inherit base native

do_install_append() {
	oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}

