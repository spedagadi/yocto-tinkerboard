require recipes-graphics/xorg-driver/xorg-driver-video.inc

SUMMARY = "X.Org X server -- Intel integrated graphics chipsets driver"

DESCRIPTION = "intel is an Xorg driver for Intel integrated graphics \
chipsets. The driver supports depths 8, 15, 16 and 24. On some chipsets, \
the driver supports hardware accelerated 3D via the Direct Rendering \
Infrastructure (DRI)."

LIC_FILES_CHKSUM = "file://COPYING;md5=8730ad58d11c7bbad9a7066d69f7808e"

PV = "2.99.917+git${SRCPV}"

SRCREV = "a1a0f76e55d8dfbbf27a288b71b4bd3edbfbe836"
SRC_URI = "git://anongit.freedesktop.org/git/xorg/driver/xf86-video-intel.git;protocol=http \
           file://0001-sna_display-fix-printf-to-distinguish-between-24Hz-a.patch \
           "

S = "${WORKDIR}/git"

DEPENDS += "virtual/libx11 drm libpciaccess pixman"

PACKAGECONFIG ??= "xvmc sna uxa udev ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'dri dri2 dri3', '', d)}"

PACKAGECONFIG[dri] = "--enable-dri,--disable-dri"
PACKAGECONFIG[dri1] = "--enable-dri1,--disable-dri1,xf86driproto"
PACKAGECONFIG[dri2] = "--enable-dri2,--disable-dri2,dri2proto"
PACKAGECONFIG[dri3] = "--enable-dri3,--disable-dri3,dri3proto"
PACKAGECONFIG[sna] = "--enable-sna,--disable-sna"
PACKAGECONFIG[uxa] = "--enable-uxa,--disable-uxa"
PACKAGECONFIG[udev] = "--enable-udev,--disable-udev,udev"
PACKAGECONFIG[xvmc] = "--enable-xvmc,--disable-xvmc,libxvmc xcb-util"
PACKAGECONFIG[tools] = "--enable-tools,--disable-tools,libxinerama libxrandr libxdamage libxfixes libxcursor libxtst libxext libxrender"

# --enable-kms-only option is required by ROOTLESS_X
EXTRA_OECONF += "--enable-kms-only \
                 --disable-tear-free \
                 --enable-async-swap \
                "

COMPATIBLE_HOST = '(i.86|x86_64).*-linux'

# PKG = libintelxvmc....
DEBIAN_NOAUTONAME_${PN} = "1"

FILES_${PN} += "${datadir}/polkit-1"
