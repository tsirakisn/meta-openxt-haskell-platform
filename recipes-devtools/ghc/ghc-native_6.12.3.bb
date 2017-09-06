inherit native
require ghc-${PV}.inc

# This requires a ghc6 capable compiler to be already installed on the host in
# order to bootstrap the build.
# ghc-6.12.3 can be found: https://www.haskell.org/ghc/download_ghc_6_12_3.html
# Get the binary package as it is not trivial to get it to build as intended, I
# also recommend
# http://www.edsko.net/2013/02/10/comprehensive-haskell-sandboxes/ to separate
# the installation from the host rootfs.
#
# Static libraries are not -fPIC, This makes x86_64 attempts to build the
# compiler fail miserably, example using the binary package with an HelloWord:
#
#   Hello.hs:main = putStrLn "Hello, World!"
#
#   $ ghc -dynamic -o hello Hello.hs
#   /usr/bin/ld: warning: libgmp.so.3, needed by /home/user/.ghc-env/ghc6-x86_64/local//lib/ghc-6.12.3/haskell98-1.0.1.1/libHShaskell98-1.0.1.1-ghc6.12.3.so, may conflict with libgmp.so.10
#   /usr/bin/ld: /home/user/.ghc-env/ghc6-x86_64/local//lib/ghc-6.12.3/libHSrtsmain.a(Main.o): relocation R_X86_64_32 against symbol `ZCMain_main_closure' can not be used when making a shared object; recompile with -fPIC
#   /usr/bin/ld: Hello.o: relocation R_X86_64_PC32 against symbol `newCAF' can not be used when making a shared object; recompile with -fPIC
#   /usr/bin/ld: final link failed: Bad value
#   collect2: error: ld returned 1 exit status
#
# This will have to be dealt with later.

do_configure() {
    ./configure --prefix=${prefix} --enable-shared
    echo "STANDARD_OPTS += \"-I${STAGING_INCDIR_NATIVE}\"" >> rts/ghc.mk
}
