PV="1.10.4"

SRC_URI_remove = " \
    file://ensure-valid-sentinels-for-gst_structure_get-etc.patch \
    file://0001-Makefile.am-don-t-hardcode-libtool-name-when-running.patch \
    file://0001-gstreamer-gl.pc.in-don-t-append-GL_CFLAGS-to-CFLAGS.patch  \
    file://0002-glplugin-enable-gldeinterlace-on-OpenGL-ES.patch \
    file://0003-glcolorconvert-implement-multiple-render-targets-for.patch \
    file://0004-glcolorconvert-don-t-use-the-predefined-variable-nam.patch \
    file://0005-glshader-add-glBindFragDataLocation.patch \
    file://0006-glcolorconvert-GLES3-deprecates-texture2D-and-it-doe.patch \
    file://0008-gl-implement-GstGLMemoryEGL.patch \
    file://0009-glimagesink-Downrank-to-marginal.patch \
"

SRC_URI += " \
    file://0010-gl-pkg-config-don-t-advertise-all-our-dependent-libr.patch \
    file://0011-gstreamer-gl.pc.in-don-t-append-GL_CFLAGS-to-CFLAGS.patch  \
    file://0012-Makefile.am-don-t-hardcode-libtool-name-when-running.patch \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESPATH_prepend := "${THISDIR}/${PN}:"

SRC_URI[md5sum] = "2757103e57a096a1a05b3ab85b8381af"
SRC_URI[sha256sum] = "23ddae506b3a223b94869a0d3eea3e9a12e847f94d2d0e0b97102ce13ecd6966"
