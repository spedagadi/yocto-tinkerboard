# Copyright (C) 2017 Fuzhou Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

PACKAGECONFIG_GL   = "gles2"
PACKAGECONFIG_FONTS	= "fontconfig"

PACKAGECONFIG_APPEND = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', 'libinput eglfs', d)}"

PACKAGECONFIG_append = " ${PACKAGECONFIG_APPEND} kms accessibility"
