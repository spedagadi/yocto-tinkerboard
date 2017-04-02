# Copyright (C) 2016 - 2017 Randy Li <ayaka@soulik.info>
# Released under the GNU GENERAL PUBLIC LICENSE Version 2
# (see COPYING.GPLv2 for the terms)

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://readme.txt;md5=5783b0faa80f27469c4ee56334229e1a"

inherit pkgconfig cmake

EXTRA_OECMAKE = "     \
    -DRKPLATFORM=ON   \
    -DHAVE_DRM=ON     \
"

PACKAGES = "${PN}-dbg ${PN}-staticdev ${PN}-dev ${PN}"
FILES_${PN} = "${libdir}/lib*${SOLIBS}"
FILES_${PN}-dev = "${libdir}/lib*${SOLIBSDEV} ${includedir} ${libdir}/pkgconfig"
SECTION_${PN}-dev = "devel"
FILES_${PN}-staticdev = "${libdir}/*.a"
SECTION_${PN}-staticdev = "devel"
