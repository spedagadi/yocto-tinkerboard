DEPENDS += "libvdpau"

PROVIDES += "libegl"

MESA_LLVM_RELEASE = "3.7.1"

PACKAGECONFIG_append = " gbm egl gles dri ${MESA_CRYPTO} \
                ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}\
                ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)}\
                " 

PACKAGECONFIG_x86 = " egl gles gbm dri dri3 x11 xa r600 gallium gallium-llvm "
PACKAGECONFIG_x86-64 = " egl gles gbm dri dri3 x11 xa r600 gallium gallium-llvm "

GALLIUMDRIVERS_LLVM33 = "${@bb.utils.contains('PACKAGECONFIG', 'r600', 'r600', '', d)}"

GALLIUMDRIVERS_append_armv7a = ",freedreno"
GALLIUMDRIVERS_append_aarch64 = ",freedreno"

PACKAGECONFIG_append_armv7a = " gallium \
                         ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xa', '', d)} \
                       "
PACKAGECONFIG_append_aarch64 = " gallium \
                         ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xa', '', d)} \
                       "


FILES_${PN}-dbg += "${libdir}/vdpau/.debug"
FILES_${PN}-dev += "${libdir}/vdpau/*.so"

FILES_mesa-megadriver += "${libdir}/vdpau/*.so.*"

