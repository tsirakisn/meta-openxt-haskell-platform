DESCRIPTION = "A fast parser combinator library, aimed particularly at dealing efficiently with network protocols and complicated text/binary file formats."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=842154b7fc56299acd68b1fe3fcd79b2"

inherit hackage

SRC_URI[md5sum] = "51de2b1aad524ba69cfa74e1c71371f1"
SRC_URI[sha256sum] = "cb940184849e16428ce3b04f5a468c7b79aad9ea15b24b06c4f693f96fa273e2"

DEPENDS:append = "\
    hkg-scientific \
"

#do_compile() {
#    ${RUNGHC} -v3 Setup.*hs build attoparsec-internal \
#        --verbose=3
#
#    ghc-pkg --package-db dist/package.conf.inplace recache
#
#    ${RUNGHC} -v3 Setup.*hs build attoparsec-external \
#        --verbose=3
#}

PR = "r1"
