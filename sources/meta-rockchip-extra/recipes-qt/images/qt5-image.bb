# Copyright (C) 2017 Fuzhou Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

IMAGE_FEATURES += "\
	splash	\
"

do_rootfs[depends] += "virtual/kernel:do_populate_sysroot"

require recipes-rk/images/rk-image-multimedia.bb

COMMON_INSTALL = " \
	qtbase	\
	qtdeclarative \
	qtmultimedia \
	qtsvg \
	qtsensors \
	qtimageformats \
	qtsystems \
	qtscript \
	qt3d \
	qtwebengine \
	qtgraphicaleffects \
	qtconnectivity \
	qtlocation \
	qtquickcontrols \
	qtquickcontrols2 \
"

QT_DEMOS = " \
	rockery \
	qtsmarthome \
	qt5-opengles2-test \
"

IMAGE_INSTALL += " \
	${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'qtwayland', '', d)} \
	${COMMON_INSTALL} \
	${QT_DEMOS} \
	autostart \
	packagegroup-fonts-truetype \
"
