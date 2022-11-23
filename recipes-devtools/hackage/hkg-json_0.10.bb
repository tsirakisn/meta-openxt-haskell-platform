DESCRIPTION = "JSON library."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc842e62515f35f232f237038e679cb2"

inherit hackage

SRC_URI[md5sum] = "1af33c67594f69048b69d4aeafeea03e"
SRC_URI[sha256sum] = "56192d1e922cc71ad1aaf31baea8ee7e1f1b862f95bc72f60548caee4a484a87"

DEPENDS += "hkg-parsec"

PR = "r1"
