#!/bin/sh
### BEGIN INIT INFO
# Provides:          autostart
# Required-Start:    $remote_fs $all
# Required-Stop:     $remote_fs $all
# Default-Start:     5
# Default-Stop:
# Short-Description: Start application at boot time
# Description:       This script will run the application as the
#					 last tep in the boot process.
### END INIT INFO

export QT_QPA_PLATFORM=wayland-egl
export XDG_RUNTIME_DIR=/run/user/0
export WAYLAND_DISPLAY=wayland-0



cd /usr/share/rockery-git/
sleep 1 && ./rockery -f &> /dev/null &
