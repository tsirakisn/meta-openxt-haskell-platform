DESCRIPTION = "The posix regex backend for regex-base."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5a9760d05990120048023e30741b4d71"

inherit hackage

SRC_URI[md5sum] = "624e53941566b3ef5a8ff918329843f9"
SRC_URI[sha256sum] = "63e76de0610d35f1b576ae65a25a38e04e758ed64b9b3512de95bdffd649485c"

DEPENDS += "hkg-regex-base"

PR = "r1"
