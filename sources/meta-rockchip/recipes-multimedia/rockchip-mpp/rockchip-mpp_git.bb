# Copyright (C) 2016 - 2017 Randy Li <ayaka@soulik.info>
# Released under the GNU GENERAL PUBLIC LICENSE Version 2
# (see COPYING.GPLv2 for the terms)

DEFAULT_PREFERENCE = "-1"

include rockchip-mpp.inc

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/rockchip-linux/mpp.git;branch=develop"

PV = "develop+git${SRCPV}"

S = "${WORKDIR}/git"
