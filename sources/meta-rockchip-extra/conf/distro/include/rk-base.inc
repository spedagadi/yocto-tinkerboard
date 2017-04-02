require conf/distro/poky.conf

DISTRO = "rk-base"
DISTRO_NAME = "Rockchip Distro Base"
SDK_VENDOR = "-rksdk"

MAINTAINER = "Jacob Chen <jacob2.chen@rock-chips.com>"

TARGET_VENDOR = "-rk"

DISTROOVERRIDES = "rockchip"

# Rockchip BSP default settings

PREFERRED_VERSION_libdrm = "rk"
PREFERRED_VERSION_xserver-xorg = "rk"
PREFERRED_VERSION_xserver-nodm-init = "1.0"
XSERVER ?= "xserver-xorg \
		   xf86-input-libinput \
		   xf86-video-modesetting"

PREFERRED_PROVIDER_virtual/egl = "rockchip-mali"
PREFERRED_PROVIDER_virtual/libgles1 = "rockchip-mali"
PREFERRED_PROVIDER_virtual/libgles2 = "rockchip-mali"
PREFERRED_PROVIDER_virtual/libopencl = "rockchip-mali"

# Workaround: libmali.so provided by rk having no SONAME field in it
# so add it to fix rdepends problems
ASSUME_SHLIBS += "libEGL.so:rockchip-mali"
ASSUME_SHLIBS += "libGLESv1_CM.so:rockchip-mali"
ASSUME_SHLIBS += "libGLESv2.so:rockchip-mali"
ASSUME_SHLIBS += "libOpenCL.so:rockchip-mali"
ASSUME_SHLIBS += "libgbm.so:rockchip-mali"
ASSUME_SHLIBS += "libwayland-egl.so:rockchip-mali"

PREFERRED_PROVIDER_virtual/kernel = "linux-rockchip"

DEFAULTTUNE_rk3036="cortexa7hf-neon-vfpv4"
DEFAULTTUNE_rk3288="cortexa17hf-neon-vfpv4"
DEFAULTTUNE_rk3399="aarch64"
