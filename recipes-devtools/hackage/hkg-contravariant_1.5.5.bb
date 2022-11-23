DESCRIPTION = "Contravariant functors."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=daf6c5b2c1afd111ff98da4be98babc6"

inherit hackage

SRC_URI[md5sum] = "3b73dd486bc7c62752924d9217762f74"
SRC_URI[sha256sum] = "77b8e9fcc65df51714eb95615b23371aa3bd864e8c6dedb775eb939058d08204"

DEPENDS += "hkg-transformers"

PR = "r1"
