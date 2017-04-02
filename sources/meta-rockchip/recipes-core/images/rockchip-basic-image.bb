# Copyright (C) 2014 Romain Perier <romain.perier@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

include recipes-core/images/core-image-minimal.bb

DESCRIPTION = "Basic image for Rockchip devices. This is a small image just \
capable of allowing a device to boot with packages management, \
ssh server and development tools."
IMAGE_FEATURES_append = " package-management ssh-server-dropbear"
IMAGE_INSTALL_append = " net-kexec "

LICENSE = "MIT"
