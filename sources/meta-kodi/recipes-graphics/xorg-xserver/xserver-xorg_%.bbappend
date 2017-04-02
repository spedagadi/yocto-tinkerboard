PACKAGECONFIG ?= "dri2 udev ${XORG_CRYPTO} \
                  ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'dri glx glamor', '', d)} \
                  ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "xwayland", "", d)} \
                  ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "systemd", "", d)} \
                  ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "systemd-logind", "", d)} \
                  dri3 xshmfence \
"
