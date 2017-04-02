# Copyright (C) 2014 NEO-Technologies
# Released under the MIT license (see COPYING.MIT for the terms)

inherit image_types

# This image depends on the rootfs ext4 image
IMAGE_TYPEDEP_rockchip-update-img = "ext4"

DEPENDS = "mkbootimg-native rockchip-bootloader virtual/kernel"

FIRMWARE_VER  ?= "1.0"
MANUFACTURER  ?= "NEO-Technologies"
MACHINE_MODEL ?= "${MACHINE}"
CMDLINE       ?= "console=ttyFIQ0 root=/dev/block/mtd/by-name/linuxroot rw rootfstype=ext4 rootdelay=1"
MTDPARTS      ?= "0x00008000@0x00002000(boot),-@0x0000A000(linuxroot)"

PACKAGE_FILE = "package-file"
PARAMETER    = "parameter"
LOADER       = "loader.bin"
KERNEL_IMG   = "${KERNEL_IMAGETYPE}"
INITRD_IMG   = "initrd.img"
BOOT_IMG     = "boot.img"
RAW_IMG      = "${IMAGE_NAME}.raw.img"
UPDATE_IMG   = "${IMAGE_NAME}.update.img"

IMAGE_CMD_rockchip-update-img () {
    # Change to image directory
    cd ${DEPLOY_DIR_IMAGE}

    # Create package-file
    cat > ${PACKAGE_FILE} << EOF
# NAME		Relative path
package-file	${PACKAGE_FILE}
bootloader	${LOADER}
parameter	${PARAMETER}
boot		${BOOT_IMG}
linuxroot	${IMAGE_NAME}.rootfs.ext4
EOF

    # Create parameter
    cat > ${PARAMETER} << EOF
FIRMWARE_VER:${FIRMWARE_VER}
MACHINE_MODEL:${MACHINE}
MACHINE_ID:007
MANUFACTURER:${MANUFACTURER}
MAGIC: 0x5041524B
ATAG: 0x60000800
MACHINE: 3066
CHECK_MASK: 0x80
KERNEL_IMG: 0x60408000
#RECOVER_KEY: 1,1,0,20,0
CMDLINE:${CMDLINE} initrd=0x62000000,0x00800000 mtdparts=rk29xxnand:${MTDPARTS}
EOF

    # Create boot.img
    mkbootimg --kernel ${KERNEL_IMG} --ramdisk ${INITRD_IMG} -o ${BOOT_IMG}

    # Build update.img using afptool and img_maker
    afptool -pack . ${RAW_IMG}
    img_maker -rk31 ${LOADER} 1 0 0 ${RAW_IMG} ${UPDATE_IMG}

    # Clean directory
    rm ${PACKAGE_FILE} ${PARAMETER} ${BOOT_IMG} ${RAW_IMG}
}
