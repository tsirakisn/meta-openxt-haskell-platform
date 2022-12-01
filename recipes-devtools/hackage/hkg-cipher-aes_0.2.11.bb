DESCRIPTION = ""
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f5138d63fd9e7ecca79f04796e43bda4"

inherit hackage

SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = ""

# NOTE: we should really try to deprecate this. it's very old.

DEPENDS:append = "hkg-crypto-cipher-types hkg-securemem"

PR = "r1"
