DESCRIPTION = "Comonads."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c054c5b0812492d2d762118893eb5acd"

inherit hackage

SRC_URI[md5sum] = "97d47d6908e1d12e46dd5394fd7494bc"
SRC_URI[sha256sum] = "5f79b15a4bf87572c3b38610ef9403f4ac6a48dc493c311f0edb241adda5ba0f"

DEPENDS += " \
    hkg-semigroups \
    hkg-transformers \
"

PR = "r1"
