FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://videooutput-use-QT_GSTREAMER_WINDOW_VIDEOSINK-env.patch"

PACKAGECONFIG += " gstreamer"
