DESCRIPTION = ""
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f5138d63fd9e7ecca79f04796e43bda4"

inherit hackage

SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/patches:"
SRC_URI += "\
    file://bump-supported-base-lib.patch \
"

DEPENDS:append = "\
    hkg-old-locale \
"

PR = "r1"