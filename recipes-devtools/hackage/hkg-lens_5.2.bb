DESCRIPTION = ""
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f5138d63fd9e7ecca79f04796e43bda4"

inherit hackage

SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = ""

DEPENDS:append = "\
    hkg-assoc \
    hkg-base-orphans \
    hkg-bifunctors \
    hkg-call-stack \
    hkg-comonad \
    hkg-contravariant \
    hkg-distributive \
    hkg-exceptions \
    hkg-free \
    hkg-hashable \
    hkg-indexed-traversable \
    hkg-indexed-traversable-instances \
    hkg-kan-extensions \
    hkg-parallel \
    hkg-profunctors \
    hkg-reflection \
    hkg-semigroupoids \
    hkg-strict \
    hkg-tagged \
    hkg-th-abstraction \
    hkg-these \
    hkg-transformers-compat \
    hkg-unordered-containers \
    hkg-vector \
"

PR = "r1"
