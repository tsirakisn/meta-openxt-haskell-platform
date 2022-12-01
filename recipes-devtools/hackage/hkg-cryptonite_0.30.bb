DESCRIPTION = ""
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f5138d63fd9e7ecca79f04796e43bda4"

inherit hackage

SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = ""

DEPENDS:append = "hkg-basement hkg-memory"
EXTRA_CABAL_CONF:append = "-f-integer-gmp"

PR = "r1"
