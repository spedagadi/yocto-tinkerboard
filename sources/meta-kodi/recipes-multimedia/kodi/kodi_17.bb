SUMMARY = "Kodi Media Center"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

FILESPATH =. "${FILE_DIRNAME}/kodi-17:"

DEPENDS = " \
            cmake-native \
            curl-native \
            gperf-native \
            jsonschemabuilder-native \
            nasm-native \
            swig-native \
            yasm-native \
            zip-native \
            avahi \
            boost \
            bzip2 \
            curl \
            dcadec \
            enca \
            expat \
            faad2 \
            ffmpeg \
            fontconfig \
            fribidi \
            giflib \
            jasper \
            libass \
            libcdio \
            libcec \
            libdvdcss \
            libdvdread \
            libmad \
            libmicrohttpd \
            libmms \
            libmms \
            libmodplug \
            libpcre \
            libplist \
            libsamplerate0 \
            libsdl-image \
            libsdl-mixer \
            libsquish \
            libssh \
            libtinyxml \
            libusb1 \
            libxinerama \
            libxmu \
            libxrandr \
            libxslt \
            libxtst \
            lzo \
            mpeg2dec \
            python \
            samba \
            sqlite3 \
            taglib \
            virtual/egl \
            virtual/libsdl \
            wavpack \
            yajl \
            zlib \
          "

SRCREV = "a10c5048f2487bd9b2dc1f35d2fee48a25945a70"

# 'patch' doesn't support binary diffs
PATCHTOOL = "git"

PV = "17+gitr${SRCPV}"
SRC_URI = "git://github.com/xbmc/xbmc.git;branch=Krypton \
           file://0001-library-tvshows-switch-genre-and-recently-added.patch \
           file://0002-library-movies-switch-genre-and-recently-added.patch \
           file://0003-estuary-move-recently-added-entries-to-the-top-in-ho.patch \
           file://0002-configure-don-t-try-to-run-stuff-to-find-tinyxml.patch \
           file://0004-add-support-to-read-frequency-output-if-using-intel-.patch \
           file://0005-Revert-posix-move-libdvd-to-depends.patch \
           file://kodi-rpi-001-reduce-priority-of-python-threads.patch \
           file://kodi-rpi-002-avoid-memcpy-on-every-demuxer-packet.patch \
           file://kodi-rpi-003-load-OSD-dialogs-on-startup.patch \
           file://kodi-rpi-004-speedup-thumb-loading.patch \
          "

inherit autotools-brokensep gettext python-dir

S = "${WORKDIR}/git"

# breaks compilation
CCACHE = ""
ASNEEDED = ""

CACHED_CONFIGUREVARS += " \
    ac_cv_path_PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python" \
"

ACCEL ?= ""
ACCEL_x86 = "vaapi vdpau"
ACCEL_x86-64 = "vaapi vdpau"

PACKAGECONFIG ??= "${ACCEL} opengl x11"
PACKAGECONFIG[opengl] = "--enable-gl,--enable-gles,glew"
PACKAGECONFIG[openglesv2] = "--enable-gles,--enable-gl,"
PACKAGECONFIG[vaapi] = "--enable-vaapi,--disable-vaapi,libva"
PACKAGECONFIG[vdpau] = "--enable-vdpau,--disable-vdpau,libvdpau"
PACKAGECONFIG[mysql] = "--enable-mysql,--disable-mysql,mysql5"
PACKAGECONFIG[x11] = "--enable-x11,--disable-x11,libxinerama libxmu libxrandr libxtst"
PACKAGECONFIG[pulseaudio] = "--enable-pulse,--disable-pulse,pulseaudio"
PACKAGECONFIG[lcms] = "--enable-lcms2,--disable-lcms2,lcms"

EXTRA_OECONF = " \
    --enable-libusb \
    --enable-airplay \
    --disable-optical-drive \
    --with-ffmpeg=shared \
    --enable-texturepacker=no \
    --disable-dvdcss \
"

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

do_configure() {
    git add . || true
    git commit -a -m "OE patches" || true
    ( for i in $(find ${S} -name "configure.*") ; do
	cd $(dirname $i) && gnu-configize --force || true
        autoreconf -sfi || true
    done )

    ( cd ${S}/tools/depends && autoreconf -sfi && autoconf -f || true )

    git add . || true
    git commit -a -m "OE autoreconf" || true

    make -C tools/depends/target/crossguid PREFIX=${STAGING_DIR_HOST}${prefix}
    BOOTSTRAP_STANDALONE=1 make -f bootstrap.mk JSON_BUILDER="${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder" 
    BOOTSTRAP_STANDALONE=1 make -f codegenerator.mk JSON_BUILDER="${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder" 
    oe_runconf
}

do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o    -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

INSANE_SKIP_${PN} = "rpaths"

FILES_${PN} += "${datadir}/xsessions ${datadir}/icons ${libdir}/xbmc ${datadir}/xbmc"
FILES_${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug"

# kodi uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS_${PN}_append = " libcec \
                             python \
                             python-ctypes \
                             python-lang \
                             python-re \
                             python-netclient \
                             python-html \
                             python-difflib \
                             python-json \
                             python-zlib \
                             python-shell \
                             python-sqlite3 \
                             python-compression \
                             python-xmlrpc \
                             libcurl \
                             xdpyinfo \
                             xrandr \
                             ${@bb.utils.contains('PACKAGECONFIG', 'opengl', 'mesa-demos', '', d)} \
                             tzdata-africa \
                             tzdata-americas \
                             tzdata-antarctica \
                             tzdata-arctic \
                             tzdata-asia \
                             tzdata-atlantic \
                             tzdata-australia \
                             tzdata-europe \
                             tzdata-pacific \
                           "
RRECOMMENDS_${PN}_append_libc-glibc = " glibc-charmap-ibm850 \
                                        glibc-gconv-ibm850 \
					glibc-gconv-unicode \
                                        glibc-gconv-utf-32 \
					glibc-charmap-utf-8 \
					glibc-localedata-en-us \
                                      "
