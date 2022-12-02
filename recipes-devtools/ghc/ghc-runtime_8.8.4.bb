# This recipe only provides the runtime libraries.
# installed-vs-shipped set to silence everything not packaged on purpose.

#require ghc-${PV}.inc

#-----------------------------------

DESCRIPTION = "The Glasgow Haskell Compiler."
HOMEPAGE = "https://www.haskell.org/ghc/download_ghc_8.8.4"
LICENSE = "GHCL"
SECTION = "devel/haskell"

S = "${WORKDIR}/ghc-${PV}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = " \
    https://downloads.haskell.org/~ghc/${PV}/ghc-${PV}-src.tar.xz \
    file://autoconf-2.71-fix.patch \
    file://build.mk \
"
#SRC_URI[md5sum] = "03b80fade0f245d6fad8e39255932e78"
#SRC_URI[sha256sum] = "11117735a58e507c481c09f3f39ae5a314e9fbf49fc3109528f99ea7959004b2"
SRC_URI[sha256sum] = "f0505e38b2235ff9f1090b51f44d6c8efd371068e5a6bb42a2a6d8b67b5ffc2d"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7cb08deb79c4385547f57d6bb2864e0f"

do_configure:append() {
    cp ${WORKDIR}/build.mk ${S}/mk/
}
#-----------------------------------

PACKAGES = " \
    ${PN} \
"

FILES:${PN} = " \
    ${libdir}/ghc-${PV}/array-*/*.so \
    ${libdir}/ghc-${PV}/base-*/*.so \
    ${libdir}/ghc-${PV}/binary-*/*.so \
    ${libdir}/ghc-${PV}/bytestring-*/*.so \
    ${libdir}/ghc-${PV}/containers-*/*.so \
    ${libdir}/ghc-${PV}/deepseq-*/*.so \
    ${libdir}/ghc-${PV}/directory-*/*.so \
    ${libdir}/ghc-${PV}/filepath-*/*.so \
    ${libdir}/ghc-${PV}/ghc-boot-th-*/*.so \
    ${libdir}/ghc-${PV}/ghc-prim-*/*.so \
    ${libdir}/ghc-${PV}/haskell98-*/*.so \
    ${libdir}/ghc-${PV}/integer-gmp-*/*.so \
    ${libdir}/ghc-${PV}/integer-simple-*/*.so \
    ${libdir}/ghc-${PV}/libHSffi-ghc${PV}.so \
    ${libdir}/ghc-${PV}/libHSrts-ghc${PV}.so \
    ${libdir}/ghc-${PV}/libHSrts_debug-ghc${PV}.so \
    ${libdir}/ghc-${PV}/libHSrts_thr-ghc${PV}.so \
    ${libdir}/ghc-${PV}/libHSrts_thr_debug-ghc${PV}.so \
    ${libdir}/ghc-${PV}/mtl-*/*.so \
    ${libdir}/ghc-${PV}/old-locale-*/*.so \
    ${libdir}/ghc-${PV}/old-time-*/*.so \
    ${libdir}/ghc-${PV}/parsec-*/*.so \
    ${libdir}/ghc-${PV}/pretty-*/*.so \
    ${libdir}/ghc-${PV}/process-*/*.so \
    ${libdir}/ghc-${PV}/random-*/*.so \
    ${libdir}/ghc-${PV}/stm-*/*.so \
    ${libdir}/ghc-${PV}/syb-*/*.so \
    ${libdir}/ghc-${PV}/template-haskell-*/*.so \
    ${libdir}/ghc-${PV}/text-*/*.so \
    ${libdir}/ghc-${PV}/time-*/*.so \
    ${libdir}/ghc-${PV}/transformers-*/*.so \
    ${libdir}/ghc-${PV}/unix-*/*.so \
    ${libdir}/ghc-${PV}/utf8-string-*/*.so \
"
INSANE_SKIP:${PN} = "installed-vs-shipped"

CLEANBROKEN = "1"
#EXTRA_OEMAKE = "-d --trace --no-builtin-rules"

#PARALLEL_MAKE = ""

DEPENDS:append = " \
    ghc-native \
    gcc-cross-${TARGET_ARCH} \
    binutils-cross-${TARGET_ARCH} \
"

BUILD_RANLIB:remove = "-D"

do_configure() {
    OE_CC=$(echo $CC | cut -d' ' -f1)
    OE_CFLAGS=$(echo $CC | cut -d' ' -f2-)

    OE_LD=$(echo $LD | cut -d' ' -f1)
    OE_LDFLAGS=$(echo $LD | cut -d' ' -f2-)

    ./configure --prefix=${prefix} --host=${BUILD_SYS} --build=${BUILD_SYS} --target=${TARGET_SYS} \
                CC=${TMPDIR}/hosttools/gcc \
                LD=${OE_LD} \
                LDFLAGS="${OE_LDFLAGS}" \
                AR=${AR} \
                NM=${NM}
}

#do_compile:prepend() {
#    [ -e ${S}/mk/build.mk ] && rm ${S}/mk/build.mk
#    echo "HADDOCK_DOCS=NO" >> ${S}/mk/build.mk
#}

do_sysroot_ghc_pkg_db() {
    ghc-pkg recache
}

do_install() {
    oe_runmake DESTDIR=${D} install
}

addtask do_sysroot_ghc_pkg_db before do_configure after do_prepare_recipe_sysroot
do_sysroot_ghc_pkg_db[doc] = "Amend the paths in the package database local to the recipes' sysroot."
do_sysroot_ghc_pkg_db[dirs] = "${B}"
