#!/bin/bash -e

LOADER1_SIZE=8000
RESERVED1_SIZE=128
RESERVED2_SIZE=8192
LOADER2_SIZE=8192
ATF_SIZE=8192
BOOT_SIZE=229376

SYSTEM_START=0
LOADER1_START=64
RESERVED1_START=`expr ${LOADER1_START}  + ${LOADER1_SIZE}`
RESERVED2_START=`expr ${RESERVED1_START}  + ${RESERVED1_SIZE}`
LOADER2_START=`expr ${RESERVED2_START}  + ${RESERVED2_SIZE}`
ATF_START=`expr ${LOADER2_START}  + ${LOADER2_SIZE}`
BOOT_START=`expr ${ATF_START}  + ${ATF_SIZE}`
ROOTFS_START=`expr ${BOOT_START}  + ${BOOT_SIZE}`

LOCALPATH=$(pwd)
TOOLPATH=${LOCALPATH}/rkbin/tool
CHIP=""
DEVICE=""
IMAGE=""
DEVICE=""
SEEK="0"

PATH=$PATH:$TOOLPATH

usage() {
	echo -e "\nUsage: rk3x \n"
	echo -e	"		emmc: build/flash_tool.sh -c rk3288  -p system -i out/system.img  \n"
	echo -e "       sdcard: build/flash_tool.sh -c rk3288  -d /dev/sdb -p system  -i out/system.img \n"
	echo -e "\nUsage: rv \n"
	echo -e "       sdcard: build/flash_tool.sh -c rk1108 -i out/firmware.img \n"

}

finish() {
	echo -e "\e[31m FLASH IMAGE FAILED.\e[0m"
	exit -1
}
trap finish ERR

while getopts "c:t:s:d:p:r:d:i:h" flag
do
	case $flag in
		c)
			CHIP="$OPTARG"
			;;
		d)
			DEVICE="$OPTARG"
			;;
		i)
			IMAGE="$OPTARG"
			if [ ! -e ${IMAGE} ] ; then
				echo -e "\e[31m CAN'T FIND IMAGE \e[0m"
				usage
				exit
			fi
			;;
		p)
			PARTITIONS="$OPTARG"
			BPARTITIONS=`echo $PARTITIONS| tr 'a-z' 'A-Z'`
			SEEK=${BPARTITIONS}_START
			eval SEEK=\$$SEEK

			if [ -n "$(echo $SEEK| sed -n "/^[0-9]\+$/p")" ];then  
				echo "PARTITIONS OFFSET: $SEEK sectors."  
			else  
				echo -e "\e[31m INVAILD PARTITION.\e[0m"
				exit
			fi  
			;;
	esac
done

if [ ! $IMAGE ] ; then
	usage
	exit
fi

flash_upgt() 
{
	if [ "${CHIP}" == "rk3288" ] ; then
		sudo upgrade_tool db  ${LOCALPATH}/rkbin/rk32/RK3288UbootLoader_V2.30.06.bin
	elif [ "${CHIP}" == "rk3036" ] ; then
		sudo upgrade_tool db  ${LOCALPATH}/rkbin/rk30//RK3036MiniLoaderAll_V2.19.bin
	elif [ "${CHIP}" == "rk3399" ] ; then
		sudo upgrade_tool db  ${LOCALPATH}/rkbin/rk33/RK3399MiniLoaderAll_V1.05.bin
	elif [ "${CHIP}" == "rk3228" ] ; then
		sudo upgrade_tool db  ${LOCALPATH}/rkbin/rk33/RK3328MiniLoaderAll_V1.05.bin
	elif [ "${CHIP}" == "rv1108" ] ; then
		sudo upgrade_tool db  ${LOCALPATH}/rkbin/rv1x/RK1108_usb_boot.bin
	fi

	sudo upgrade_tool wl ${SEEK} ${IMAGE}

	sleep 3

	sudo upgrade_tool rd
}

flash_sdcard() 
{
	sudo dd if=${IMAGE} of=${DEVICE} seek=${SEEK} conv=notrunc
}

if [ ! $DEVICE ] ; then
	flash_upgt
else
	flash_sdcard
fi
