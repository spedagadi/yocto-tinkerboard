# Copyright (C) 2017 Fuzhou Rockchip Electronics Co., Ltd
# Copyright (C) 2017 Trevor Woerner <twoerner@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit image_types

# Use an uncompressed ext4 by default as rootfs
IMG_ROOTFS_TYPE = "ext4"
IMG_ROOTFS = "${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.${IMG_ROOTFS_TYPE}"

# This image depends on the rootfs image
IMAGE_TYPEDEP_rockchip-gpt-img = "${IMG_ROOTFS_TYPE}"

GPTIMG         = "${IMAGE_NAME}-gpt.img"
GPTIMG_SYMLK   = "${IMAGE_BASENAME}-${MACHINE}-gpt.img"
GPTIMG_SIZE   ?= "4096"
BOOT_IMG       = "boot.img"
BOOTIMG_SYMLK  = "${IMAGE_BASENAME}-${MACHINE}-${BOOT_IMG}"
MINILOADER     = "loader.bin"
UBOOT          = "u-boot.out"
TRUST          = "trust.out"
GPTIMG_APPEND ?= "console=tty1 console=ttyS2,115200n8 rw root=/dev/mmcblk2p7 rootfstype=ext4 init=/sbin/init"

# default partitions [in Sectors]
# More info at http://rockchip.wikidot.com/partitions
LOADER1_SIZE = "8000"
RESERVED1_SIZE = "128"
RESERVED2_SIZE = "8192"
LOADER2_SIZE = "8192"
ATF_SIZE = "8192"
BOOT_SIZE = "229376"

IMAGE_DEPENDS_rockchip-gpt-img = "parted-native \
	u-boot-mkimage-native \
	mtools-native \
	dosfstools-native \
	virtual/kernel:do_deploy \
	virtual/bootloader:do_deploy"

PER_CHIP_IMG_GENERATION_COMMAND_rk3036 = "generate_rk3036_loader1_image"
PER_CHIP_IMG_GENERATION_COMMAND_rk3288 = "generate_rk3288_loader1_image"
PER_CHIP_IMG_GENERATION_COMMAND_rk3399 = "generate_rk3399_image"

IMAGE_CMD_rockchip-gpt-img () {
	# Change to image directory
	cd ${DEPLOY_DIR_IMAGE}

	# Remove the existing image symlinks
	rm -f "${GPTIMG_SYMLK}"
	rm -f "${BOOTIMG_SYMLK}"

	create_rk_image

	${PER_CHIP_IMG_GENERATION_COMMAND}

	cd ${DEPLOY_DIR_IMAGE}
	ln -s ${GPTIMG} ${GPTIMG_SYMLK}

	# create per-build boot.img with symlink
	cd ${DEPLOY_DIR_IMAGE}
	rm -f ${IMAGE_NAME}-boot.img
	if [ -f ${WORKDIR}/${BOOT_IMG} ]; then
		cp ${WORKDIR}/${BOOT_IMG} ${IMAGE_NAME}-boot.img
	fi
	ln -s ${IMAGE_NAME}-boot.img ${BOOTIMG_SYMLK}
}

create_rk_image () {

	# Initialize sdcard image file
	dd if=/dev/zero of=${GPTIMG} bs=1M count=0 seek=${GPTIMG_SIZE}

	# Create partition table
	parted -s ${GPTIMG} mklabel gpt

	# Create vendor defined partitions
	LOADER1_START=64
	RESERVED1_START=`expr ${LOADER1_START}  + ${LOADER1_SIZE}`
	RESERVED2_START=`expr ${RESERVED1_START}  + ${RESERVED1_SIZE}`
	LOADER2_START=`expr ${RESERVED2_START}  + ${RESERVED2_SIZE}`
	ATF_START=`expr ${LOADER2_START}  + ${LOADER2_SIZE}`
	BOOT_START=`expr ${ATF_START}  + ${ATF_SIZE}`
	ROOTFS_START=`expr ${BOOT_START}  + ${BOOT_SIZE}`

	parted -s ${GPTIMG} unit s mkpart loader1 ${LOADER1_START} `expr ${RESERVED1_START} - 1`
	parted -s ${GPTIMG} unit s mkpart reserved1 ${RESERVED1_START} `expr ${RESERVED2_START} - 1`
	parted -s ${GPTIMG} unit s mkpart reserved2 ${RESERVED2_START} `expr ${LOADER2_START} - 1`
	parted -s ${GPTIMG} unit s mkpart loader2 ${LOADER2_START} `expr ${ATF_START} - 1`
	parted -s ${GPTIMG} unit s mkpart atf ${ATF_START} `expr ${BOOT_START} - 1`

	# Create boot partition and mark it as bootable
	parted -s ${GPTIMG} unit s mkpart boot ${BOOT_START} `expr ${ROOTFS_START} - 1`
	parted -s ${GPTIMG} set 6 boot on

	# Create rootfs partition
	parted -s ${GPTIMG} unit s mkpart root ${ROOTFS_START} 100%

	parted ${GPTIMG} print

	# Delete the boot image to avoid trouble with the build cache
	rm -f ${WORKDIR}/${BOOT_IMG}

	# Create boot partition image
	BOOT_BLOCKS=$(LC_ALL=C parted -s ${GPTIMG} unit b print | awk '/ 6 / { print substr($4, 1, length($4 -1)) / 512 /2 }')
	BOOT_BLOCKS=`expr $BOOT_BLOCKS / 63 \* 63`

	mkfs.vfat -n "boot" -S 512 -C ${WORKDIR}/${BOOT_IMG} $BOOT_BLOCKS
	mcopy -i ${WORKDIR}/${BOOT_IMG} -s ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}.bin ::${KERNEL_IMAGETYPE}

	DEVICETREE_DEFAULT=""
	for DTS_FILE in ${KERNEL_DEVICETREE}; do
		DTS_FILE=${DTS_FILE##*/}
		[ -n "${DEVICETREE_DEFAULT}"] && DEVICETREE_DEFAULT="${DTS_FILE}"
		mcopy -i ${WORKDIR}/${BOOT_IMG} -s ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${DTS_FILE} ::${DTS_FILE}
	done

	# Create extlinux config file
	cat > ${WORKDIR}/extlinux.conf <<EOF
default yocto

label yocto
	kernel /${KERNEL_IMAGETYPE}
	devicetree /${DEVICETREE_DEFAULT}
	append ${GPTIMG_APPEND}
EOF

	mmd -i ${WORKDIR}/${BOOT_IMG} ::/extlinux
	mcopy -i ${WORKDIR}/${BOOT_IMG} -s ${WORKDIR}/extlinux.conf ::/extlinux/

	# Burn Boot Partition
	dd if=${WORKDIR}/${BOOT_IMG} of=${GPTIMG} conv=notrunc,fsync seek=${BOOT_START}

	# Burn Rootfs Partition
	dd if=${IMG_ROOTFS} of=${GPTIMG} seek=${ROOTFS_START}

}

generate_rk3036_loader1_image () {

	# Burn bootloader
	mkimage -n rk3036 -T rksd -d ${DEPLOY_DIR_IMAGE}/${SPL_BINARY} ${WORKDIR}/${UBOOT}
	cat ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.bin >>  ${WORKDIR}/${UBOOT}
	dd if=${WORKDIR}/${UBOOT} of=${GPTIMG} conv=notrunc,fsync seek=64

}

generate_rk3288_loader1_image () {

	# Burn bootloader
	mkimage -n rk3288 -T rksd -d ${DEPLOY_DIR_IMAGE}/${SPL_BINARY} ${WORKDIR}/${UBOOT}
	cat ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.bin >>  ${WORKDIR}/${UBOOT}
	dd if=${WORKDIR}/${UBOOT} of=${GPTIMG} conv=notrunc,fsync seek=64

}

generate_rk3399_image () {

	# Burn bootloader
	mkimage -n rk3399 -T rksd -d ${DEPLOY_DIR_IMAGE}/${SPL_BINARY} ${WORKDIR}/${UBOOT}
	cat ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.bin >>  ${WORKDIR}/${UBOOT}
	dd if=${WORKDIR}/${UBOOT} of=${GPTIMG} conv=notrunc,fsync seek=64

}
