DESCRIPTION = "Packages build during extra step"
LICENSE = "GPL-2.0-only & MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6      \
                    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit packagegroup

CONFIGURE_ERROR = "\
    hkg-network \
"

COMPILER_ERROR = "\
    hkg-data-binary-ieee754 \
    hkg-transformers-base \
    hkg-vhd \
"

# not technically needed
CANT_FIND_DEPENDS = "\
    hkg-distributive \
"

LINKER_ERROR = "\
    hkg-base64-bytestring \
"

DEPENDS_ON_BROKEN_PKG = "\
    hkg-async \
    hkg-dbus-core \
    hkg-either \
    hkg-errors \
    hkg-haxml \
    hkg-hinotify \
    hkg-hslogger \
    hkg-http \
    hkg-hxt \
    hkg-hxt-charproperties \
    hkg-hxt-regex-xmlschema \
    hkg-hxt-unicode \
    hkg-lifted-base \
    hkg-missingh \
    hkg-monad-control \
    hkg-network-bytestring \
    hkg-regex-compat \
    hkg-semigroupoids \
    hkg-xenstore \
"

MISSING_NEW_PKGS = "\
    hkg-attoparsec \
"

# building
RDEPENDS:${PN} = "\
    hkg-base-orphans \
    hkg-base-unicode-symbols \
    hkg-byteorder \
    hkg-call-stack \
    hkg-cereal \
    hkg-comonad \
    hkg-contravariant \
    hkg-curl \
    hkg-hashable \
    hkg-hsyslog \
    hkg-hunit \
    hkg-integer-logarithms \
    hkg-json \
    hkg-mmap \
    hkg-monad-loops \
    hkg-monadprompt \
    hkg-polyparse \
    hkg-primitive \
    hkg-regex-base \
    hkg-regex-posix \
    hkg-safe \
    hkg-semigroups \
    hkg-split \
    hkg-storable-endian \
    hkg-tagged \
    hkg-transformers-compat \
    hkg-utf8-string \
    hkg-zlib \
"
