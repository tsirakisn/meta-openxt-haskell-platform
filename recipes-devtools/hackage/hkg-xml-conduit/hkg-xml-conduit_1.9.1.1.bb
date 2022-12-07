DESCRIPTION = ""
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f5138d63fd9e7ecca79f04796e43bda4"

inherit hackage

SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/patches:"
SRC_URI += "\
    file://fix-build.patch \
"

DEPENDS:append = "\
    hkg-attoparsec \
    hkg-blaze-html \
    hkg-blaze-markup \
    hkg-conduit \
    hkg-conduit-extra \
    hkg-data-default-class \
    hkg-resourcet \
    hkg-xml-types \
"

PR = "r1"
