# yocto-tinkerboard

Yocto OS sources for Asus Tinker board

# Features #
* VPU - Hardware accelerated decoding & encoding
* Gstreamer support for VPU
* Mali Kernel & user space libraries (OpenCL is the best)
* WiFi 
* Video processing libraries (FFMPEG, OpenCV etc.,)
* Nodejs Webserver

You will have to edit build/conf/local.conf to enable/disable the modules you prefer.

# Building Yocto OS #

### $ . ./setup-environment ###
### $ bitbake core-image-sato ###


Rockchip's yocto instructions are here http://rockchip.wikidot.com/yocto-user-guide and have been hugely helpful.
Rockchip linux group - https://github.com/rockchip-linux



