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

export QT_QPA_PLATFORM=eglfs
export QT_QPA_EGLFS_KMS_CONFIG=/tmp/qt.json

cat > /tmp/qt.json <<EOF
{
  "device": "/dev/dri/card0",
  "hwcursor": true,
  "pbuffers": true
}
EOF

/usr/share/rockery-git/rockery &> /dev/null &
