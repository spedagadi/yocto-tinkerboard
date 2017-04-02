#
# Since we need LLVM 3.7.0 but meta-oe currently only supports
# 3.3, override the minimum bits needed to get it to work.
# Eventually this will need to be reconciled with upstream
#

# Ideally this would be done as a patch but since llvm3.inc already 
# manipulates this file with sed we have to do similar.  We also need
# to do this as an append to ensure proper sequencing.
do_configure_append_x86-64() {
    # Fix paths in llvm-config
    sed -ri "s#lib/${LLVM_DIR}#${baselib}/${LLVM_DIR}#g" ${S}/tools/llvm-config/llvm-config.cpp
}

#
# Override the do_install() provided by llvm3.inc to use the lib64
# directory naming.
#
# This should probably be cleaned up at some point in the stock LLVM
# sources but at the moment they do not appear to handle lib64 very well.
#
do_install() {
    cd ${LLVM_BUILD_DIR}
    oe_runmake DESTDIR=${LLVM_INSTALL_DIR} install

    mv ${LLVM_INSTALL_DIR}${bindir}/${HOST_SYS}-llvm-config-host ${LLVM_INSTALL_DIR}/llvm-config-host

    install -d ${D}${bindir}/${LLVM_DIR}
    mv ${LLVM_INSTALL_DIR}${bindir}/* ${D}${bindir}/${LLVM_DIR}/

    install -d ${D}${includedir}/${LLVM_DIR}
    mv ${LLVM_INSTALL_DIR}${includedir}/* ${D}${includedir}/${LLVM_DIR}/

    install -d ${D}${libdir}/${LLVM_DIR}

    # The LLVM sources have "/lib" embedded and so we cannot completely rely on the ${libdir} variable
    if [ -d ${LLVM_INSTALL_DIR}${libdir}/ ]; then
        mv ${LLVM_INSTALL_DIR}${libdir}/* ${D}${libdir}/${LLVM_DIR}/
    elif [ -d ${LLVM_INSTALL_DIR}${prefix}/lib ]; then
        mv ${LLVM_INSTALL_DIR}${prefix}/lib/* ${D}${libdir}/${LLVM_DIR}/
    elif [ -d ${LLVM_INSTALL_DIR}${prefix}/lib64 ]; then
        mv ${LLVM_INSTALL_DIR}${prefix}/lib64/* ${D}${libdir}/${LLVM_DIR}/
    fi

    ln -s ${LLVM_DIR}/libLLVM-${PV}${SOLIBSDEV} ${D}${libdir}/libLLVM-${PV}${SOLIBSDEV}

    install -d ${D}${docdir}/${LLVM_DIR}
    mv ${LLVM_INSTALL_DIR}${prefix}/docs/llvm/* ${D}${docdir}/${LLVM_DIR}
}
