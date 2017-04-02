# Copyright (C) 2014 NEO-Technologies
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Rockchip bootloader collection"
SECTION = "bootloaders"
LICENSE = "CLOSED"

SRC_URI = "git://github.com/neo-technologies/rockchip-bootloader.git"
SRCREV_pn-${PN} = "${AUTOREV}"

S = "${WORKDIR}/git"

LOADER ?= "RK3188Loader(L)_V2.13.bin"

inherit deploy

do_deploy() {
    install -d ${DEPLOYDIR}
    install "${S}/${LOADER}" ${DEPLOYDIR}/loader.bin
}

addtask deploy before do_build after do_compile

do_package[noexec] = "1"
do_packagedata[noexec] = "1"
do_package_write[noexec] = "1"
do_package_write_ipk[noexec] = "1"
do_package_write_rpm[noexec] = "1"
do_package_write_deb[noexec] = "1"
do_package_write_tar[noexec] = "1"
