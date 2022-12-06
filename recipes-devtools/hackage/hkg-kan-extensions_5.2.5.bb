DESCRIPTION = ""
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f5138d63fd9e7ecca79f04796e43bda4"

inherit hackage

SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = ""

DEPENDS:append = "\
    hkg-adjunctions \
    hkg-comonad \
    hkg-contravariant \
    hkg-distributive \
    hkg-free \
    hkg-invariant \
    hkg-profunctors \
    hkg-semigroupoids \
    hkg-tagged \
    hkg-transformers-compat \
"

PR = "r1"
