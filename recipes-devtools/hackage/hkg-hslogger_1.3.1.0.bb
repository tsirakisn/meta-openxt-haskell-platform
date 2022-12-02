DESCRIPTION = "A logging framework for Haskell, roughly similar to Python's logging module."
LICENSE = "LGPL"
LIC_FILES_CHKSUM="file://LICENSE;md5=4828ddb0f563948f833140ead96b205b"

inherit hackage

SRC_URI[md5sum] = "f65a5326d28f9cdad6887a32525d70dc"
SRC_URI[sha256sum] = "de0960b39cb893604ae0bdf823e505c8b7dfa6fe9d8311dca4488d1fcc58c039"

DEPENDS += "\
    hkg-network \
    hkg-network-bsd \
    hkg-old-locale \
"

EXTRA_CABAL_CONF:append = "-fnetwork--gt-3_0_0"
