require recipes-core/llvm/llvm.inc
require llvm3.7.0.inc

LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=4c0bc17c954e99fd547528d938832bfa"

DEPENDS += "zlib"
RDEPENDS_${PN} += "ncurses-terminfo"
PROVIDES += "llvm"

EXTRA_OECONF += "--enable-targets=x86_64,amdgpu"

SRC_URI = "\
	   git://llvm.org/git/llvm.git;branch=release_37;protocol=http \
	   file://0001-force-link-pass.o.patch \
	   file://0002-AMDGPU-Add-stony-support.patch \
	  "

S = "${WORKDIR}/git"

SRCREV = "39961c5e0256e7128fdaa79da7215267baa83e48"
PV = "3.7.1"

PACKAGECONFIG ??= ""

do_configure_prepend() {
	# Drop "svn" suffix from version string
	sed -i 's/${PV}svn/${PV}/g' ${S}/configure
}
