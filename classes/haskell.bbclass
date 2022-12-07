# Some Haskell packages will require case-sensitive name handling (e.g.
# hackage), but bitbake will not handle that. So these should define HPN.
HPN ?= "${PN}"
HPV ?= "${PV}"

SECTION = "devel/haskell"

BB_STRICT_CHECKSUM = "0"

DEPENDS:append:class-target = " \
    ghc-runtime \
"
DEPENDS:append = " \
    ghc-native \
"
PACKAGES = " \
    ${PN} \
    ${PN}-doc \
    ${PN}-staticdev \
    ${PN}-dbg \
    ${PN}-dev \
"
FILES:${PN}:append = " \
    ${libdir}/ghc-*/${HPN}-${HPV}/libH*.so \
    ${libdir}/ghc-*/package.conf.d/*.conf \
    ${bindir}/* \
"
FILES:${PN}-doc:append = " \
    ${datadir}/* \
"
FILES:${PN}-staticdev:append = " \
    ${libdir}/ghc-*/${HPN}-${HPV}/libHS*.a \
"
FILES:${PN}-dbg:append = " \
    ${libdir}/ghc-*/${HPN}-${HPV}/*.o \
    ${libdir}/ghc-*/${HPN}-${HPV}/.debug \
    ${prefix}/src/debug \
"
FILES:${PN}-dev:append = " \
    ${libdir}/ghc-*/${HPN}-${HPV}/* \
"

CONFIGURE_FILES += " \
    ${S}/Setup.hs \
    ${S}/Setup.lhs \
    ${S}/${HPN}.cabal \
"

#RUNGHC = "${STAGING_BINDIR_NATIVE}/runghc -f ${STAGING_BINDIR_NATIVE}/ghc"
RUNGHC = "runghc"

PACKAGE_DB_PATH:class-native = "${STAGING_LIBDIR_NATIVE}/ghc-8.10.7/package.conf.d"
PACKAGE_DB_PATH:class-target = "${STAGING_LIBDIR}/ghc-8.10.7/package.conf.d"

# GHC has been patched to disable generating PIE code, so we need to disable
# PIE to be able to link any haskell programs.
SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""

get_ghc_version() {
    ghc_version=$(ghc-pkg --version)
    echo "${ghc_version##* }"
}

# Bitbake will amend the WORKDIR paths it finds (staging stage 2). This works to
# our advantage for native class, target class need to be configured with their
# target dependencies, so substitute the target paths for WORKDIR starging so
# ghc-pkg finds them.
do_configure:prepend:class-target() {
    ghc_version=$(get_ghc_version)
    for pkgconf in ${STAGING_LIBDIR}/ghc-${ghc_version}/package.conf.d/*.conf; do
        if [ -f "${pkgconf}" ]; then
            sed -i \
                -e "s| /usr/lib| ${STAGING_LIBDIR}|" \
                -e "s| /usr/include| ${STAGING_INCDIR}|" \
                $pkgconf
        fi
    done
}

# TODO: check for Simple?
do_configure:prepend() {
    if [ ! -e "${S}/Setup.hs" ] && [ ! -e "${S}/Setup.lhs" ] ; then
        echo "import Distribution.Simple" > "${S}/Setup.hs"
        echo "main = defaultMain" >> "${S}/Setup.hs"
    fi
}

# use this to pass CFLAGS, it's the only thing that works
CFLAGS:append = "-gdwarf-4 -v"

do_configure() {
    ghc-pkg recache
    ghc-pkg --package-db "${PACKAGE_DB_PATH}" recache
    ghc_version=$(get_ghc_version)

    if [[ -d "${S}/dist" ]]; then
        ${RUNGHC} Setup.*hs clean --verbose
    fi

    if [[ ! -e "${S}/configure" ]]; then
        RUNGHC_OE_OPTS="--with-gcc=ghc-cc --with-ld=ghc-ld"
        GHC_EXTRA_OPTS="-pgmc ghc-cc -pgml ghc-ld"
        HSC2HS_EXTRA_OPTS="-c ghc-cc -l ghc-ld"
    fi

    ${RUNGHC} -v3 Setup.*hs configure \
        ${EXTRA_CABAL_CONF} \
        ${RUNGHC_OE_OPTS} \
        --disable-executable-stripping \
        --disable-library-stripping \
        --ghc-options="-dynload sysdep ${GHC_EXTRA_OPTS} -package-db ${PACKAGE_DB_PATH}" \
        --with-ghc-pkg="${STAGING_BINDIR_NATIVE}/ghc-pkg" \
        --with-hsc2hs="${STAGING_BINDIR_NATIVE}/hsc2hs" \
        --hsc2hs-options="${HSC2HS_EXTRA_OPTS} -x" \
        --ghc-pkg-options="--package-db=${PACKAGE_DB_PATH}" \
        --libsubdir="ghc-${ghc_version}/${HPN}-${HPV}" \
        --dynlibdir="/usr/lib/ghc-${ghc_version}/${HPN}-${HPV}" \
        --enable-shared \
        --prefix="${prefix}" \
        --verbose=3
}

do_compile() {
    ${RUNGHC} -v3 Setup.*hs build \
        --verbose=3
}

do_local_package_conf() {
    ${RUNGHC} Setup.*hs register \
        --gen-pkg-conf \
        --verbose=3
    if [ -f "${S}/${HPN}-${HPV}.conf" ]; then
        sed -i -e "s| ${D}${prefix}| ${prefix}|" ${S}/${HPN}-${HPV}.conf
    fi
}
addtask do_local_package_conf before do_install after do_compile
do_local_package_conf[doc] = "Generate Haskell package configuration."
do_local_package_conf[dirs] = "${B}"

# Amend the rpath to match target environment. This is because a lot of dynamic
# libraries will be installed in non-standard sub-directories of ${libdir}.  It
# could be configured through LD_LIBRARY_PATH, ld.so.conf, etc... but the
# easiest, since we are aware of the target system configuration is to embedded
# a valid RPATH in the binary.
do_fixup_rpath() {
    :
}
do_fixup_rpath:class-target() {
    ghc_version=$(get_ghc_version)

    for f in \
        ${D}${libdir}/ghc-${ghc_version}/${HPN}-${HPV}/libHS${HPN}-${HPV}*.so \
        ${D}${bindir}/* \
        ${D}${sbindir}/*
    do
        if [ ! -e ${f} ]; then
            continue
        fi

        f_type="$(file -b ${f})"
        if [ "${f_type%% *}" = "ELF" ]; then
            if ! RPATH="$(chrpath ${f})" ; then
                continue
            fi
            RPATH=${RPATH##*RPATH=}
            FIXED_RPATH=$(echo $RPATH | sed -e "s|${STAGING_DIR_HOST}||g")
            chrpath -r "${RPATH}" --replace "${FIXED_RPATH}" "${f}"
        fi
    done

}
addtask do_fixup_rpath after do_install before do_package
do_fixup_rpath[doc] = "Amend rpath set by GHC to comply with target's environment."
do_fixup_rpath[dirs] = "${B}"

do_install() {
    ${RUNGHC} Setup.*hs copy --destdir="${D}" --verbose=3

    # Prepare GHC package database files.
    if [ -f "${B}/${HPN}-${HPV}.conf" ]; then
        ghc_version=$(get_ghc_version)
        install -m 755 -d ${D}${libdir}/ghc-${ghc_version}/package.conf.d
        install -m 644 ${B}/${HPN}-${HPV}.conf ${D}${libdir}/ghc-${ghc_version}/package.conf.d
    fi
}
