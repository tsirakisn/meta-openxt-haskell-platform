DESCRIPTION = "MissingH is a library of all sorts of utility functions for Haskell programmers."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://LICENSE;md5=a3f429921821e7b143248149ab577a40"

HPN = "MissingH"

inherit hackage

SRC_URI[md5sum] = "ba700059f985eb2ce90b655a11c0dfc6"
SRC_URI[sha256sum] = "b5ac2cf0e221f068e8eb044ed72d4b19e89797b966046bf434e367f10472be43"

DEPENDS:append = "\
    hkg-hunit \
    hkg-hslogger \
    hkg-old-time \
    hkg-regex-compat \
"
