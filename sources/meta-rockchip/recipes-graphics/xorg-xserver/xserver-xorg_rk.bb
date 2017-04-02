require recipes-graphics/xorg-xserver/xserver-xorg.inc

SRCREV = "${AUTOREV}"
SRCBRANCH ?= "rockchip-1.18"
SRC_URI = "git://github.com/rockchip-linux/xserver.git;branch=${SRCBRANCH}"
SRC_URI += "file://macro_tweak.patch"
SRC_URI += "file://musl-arm-inb-outb.patch"

S = "${WORKDIR}/git"

PACKAGECONFIG_append = " glamor dri3 unwind xshmfence"

# These extensions are now integrated into the server, so declare the migration
# path for in-place upgrades.

RREPLACES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RPROVIDES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RCONFLICTS_${PN} = "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
