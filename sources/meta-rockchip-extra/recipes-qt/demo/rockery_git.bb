SUMMARY = "Rockchip Opensource linux demo application"
DESCRIPTION = "This is the Rockchip Opensource linux demo application. It helps you to do evaluations."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = "qtmultimedia qtquickcontrols2 qt3d"

SRC_URI = "git://github.com/wzyy2/rockery.git;"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

require recipes-qt/qt5/qt5.inc

do_install() {
    install -d ${D}${datadir}/${P}
    install -m 0755 ${B}/rockery ${D}${datadir}/${P}
}

FILES_${PN}-dbg += "${datadir}/${P}/.debug"
FILES_${PN} += "${datadir}"

RDEPENDS_${PN} = "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins"
