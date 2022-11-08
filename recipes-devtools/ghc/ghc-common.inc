DESCRIPTION = "The Glasgow Haskell Compiler."
HOMEPAGE = "https://www.haskell.org/ghc/download_ghc_8.4.4"
LICENSE = "GHCL"
SECTION = "devel/haskell"

DEPENDS += " \
    binutils \
"

S = "${WORKDIR}/ghc-${PV}"
