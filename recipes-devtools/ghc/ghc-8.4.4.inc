require ghc-common.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = " \
    https://downloads.haskell.org/~ghc/${PV}/ghc-${PV}-src.tar.xz \
    file://fix-gcc-build.patch \
    file://build.mk \
"
SRC_URI[md5sum] = "03b80fade0f245d6fad8e39255932e78"
SRC_URI[sha256sum] = "11117735a58e507c481c09f3f39ae5a314e9fbf49fc3109528f99ea7959004b2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7cb08deb79c4385547f57d6bb2864e0f"
do_compile() {
    cp ${WORKDIR}/build.mk ${S}/mk/
    oe_runmake #DESTDIR="${D}"
}

do_install() {
    # There is a race somewhere triggered when installing utils/hsc2hs/template-hsc.h
    # Work-around until upgrade.
    install -m 0755 -d ${D}/${libdir}/${BPN}-${PV}
    oe_runmake install DESTDIR="${D}"
}
