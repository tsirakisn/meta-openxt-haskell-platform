DESCRIPTION = ""
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f5138d63fd9e7ecca79f04796e43bda4"

inherit hackage

SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = ""

DEPENDS:append = \"
    hkg-comonad \
    hkg-contravariant \
    hkg-distributive \
    hkg-free \
    hkg-profunctors \
    hkg-semigroupoids \
    hkg-semigroups \
    hkg-tagged \
    hkg-transformers-compat \
    hkg-void \
"

PR = "r1"
