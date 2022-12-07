DESCRIPTION = ""
LICENSE = "Apache"
LIC_FILES_CHKSUM = "file://license.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"

inherit hackage

SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = ""

# TODO: race condition during compile step. piping the output of
# ld to a file fixes the race (add to ghc-ld in sysroot). that was
# supposed to help me debug so idk how I'm gonna debug this now.

DEPENDS:append = "\
    glibc \
    hkg-cereal \
    hkg-conduit \
    hkg-exceptions \
    hkg-lens \
    hkg-network \
    hkg-random \
    hkg-split \
    hkg-th-lift \
    hkg-vector \
    hkg-xml-conduit \
    hkg-xml-types \
"

EXTRA_CABAL_CONF:append = "--extra-lib-dirs=${STAGING_DIR_HOST}/lib"

PR = "r1"
