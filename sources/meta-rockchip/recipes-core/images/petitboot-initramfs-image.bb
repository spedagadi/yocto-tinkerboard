# Copyright (C) 2015 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

PACKAGE_INSTALL = "initramfs-boot-petitboot busybox base-files petitboot ${ROOTFS_BOOTSTRAP_INSTALL}"

# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = ""

export IMAGE_BASENAME = "petitboot-initramfs-image"
IMAGE_LINGUAS = ""

LICENSE = "MIT"
IMAGE_FSTYPES = "cpio.xz"

inherit core-image

IMAGE_ROOTFS_SIZE = "8192"

BAD_RECOMMENDATIONS += "busybox-syslog"
