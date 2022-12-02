DESCRIPTION = "Packages build during extra step"
LICENSE = "GPL-2.0-only & MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6      \
                    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit packagegroup

COMPILER_ERROR = "\
    hkg-data-binary-ieee754 \
    hkg-vhd \
    hkg-xenstore \
"

DEPRECATED = "\
    hkg-network-bytestring \
"

DEPENDS_ON_BROKEN_PKG = "\
    hkg-dbus-core \
"

CANT_FIND_DEPENDS = "\
    hkg-attoparsec \
    hkg-haxml \
"

NEEDS_OLDER_DEPENDS = "\
    hkg-hslogger \
    hkg-http \
"

# building
RDEPENDS:${PN} = "\
    hkg-async \
    hkg-base-orphans \
    hkg-base-unicode-symbols \
    hkg-base64-bytestring \
    hkg-byteorder \
    hkg-call-stack \
    hkg-cereal \
    hkg-comonad \
    hkg-contravariant \
    hkg-curl \
    hkg-either \
    hkg-errors \
    hkg-hashable \
    hkg-hinotify \
    hkg-hsyslog \
    hkg-hunit \
    hkg-hxt \
    hkg-hxt-charproperties \
    hkg-hxt-regex-xmlschema \
    hkg-hxt-unicode \
    hkg-integer-logarithms \
    hkg-json \
    hkg-lifted-base \
    hkg-missingh \
    hkg-mmap \
    hkg-monad-control \
    hkg-monad-loops \
    hkg-monadprompt \
    hkg-network \
    hkg-polyparse \
    hkg-primitive \
    hkg-regex-base \
    hkg-regex-compat \
    hkg-regex-posix \
    hkg-safe \
    hkg-semigroups \
    hkg-semigroupoids \
    hkg-split \
    hkg-storable-endian \
    hkg-tagged \
    hkg-transformers-base \
    hkg-transformers-compat \
    hkg-utf8-string \
    hkg-zlib \
"
