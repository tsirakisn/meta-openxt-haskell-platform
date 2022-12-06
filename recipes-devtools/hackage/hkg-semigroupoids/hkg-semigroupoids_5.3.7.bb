DESCRIPTION = "Provides a wide array of (semi)groupoids and operations for working with them."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a54c8d25fd04603d961c2a2c3b866355"

inherit hackage

SRC_URI[md5sum] = "8bc1f6326a816d89f17f1b66e6e7b13a"
SRC_URI[sha256sum] = "4f6576eb8d26539017a8fa1dead5d5ff02f7dbbe0ce60efcb2c38cc948e84a73"

DEPENDS += " \
    hkg-base-orphans \
    hkg-bifunctors \
    hkg-comonad \
    hkg-contravariant \
    hkg-distributive \
    hkg-hashable \
    hkg-semigroups \
    hkg-unordered-containers \
"

PR = "r1"
