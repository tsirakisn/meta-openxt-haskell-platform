# Some Haskell packages will require case-sensitive name handling (e.g.
# hackage), but bitbake will not handle that. So these should define HPN.
HPN ?= "${PN}"
HPV ?= "${PV}"

SECTION = "devel/haskell"

DEPENDS_append_class-target += " \
    ghc-runtime \
"
DEPENDS_append = " \
    ghc-native \
"
PACKAGES = " \
    ${PN} \
    ${PN}-doc \
    ${PN}-staticdev \
    ${PN}-dbg \
    ${PN}-dev \
"
FILES_${PN}_append = " \
    ${libdir}/ghc-*/${HPN}-${HPV}/libH*.so \
    ${libdir}/ghc-*/package.conf.d/*.conf \
    ${bindir}/* \
"
FILES_${PN}-doc_append = " \
    ${datadir}/* \
"
FILES_${PN}-staticdev_append = " \
    ${libdir}/ghc-*/${HPN}-${HPV}/libHS*.a \
"
FILES_${PN}-dbg_append = " \
    ${libdir}/ghc-*/${HPN}-${HPV}/*.o \
    ${libdir}/ghc-*/${HPN}-${HPV}/.debug \
    ${prefix}/src/debug \
"
FILES_${PN}-dev_append = " \
    ${libdir}/ghc-*/${HPN}-${HPV}/* \
"

CONFIGURE_FILES += " \
    ${S}/Setup.hs \
    ${S}/Setup.lhs \
    ${S}/${HPN}.cabal \
"

RUNGHC = "runghc"

PACKAGE_DB_PATH_class-native = "${STAGING_LIBDIR_NATIVE}/ghc-8.10.7/package.conf.d"
PACKAGE_DB_PATH_class-target = "${STAGING_LIBDIR}/ghc-8.10.7/package.conf.d"

get_ghc_version() {
    ghc_version=$(ghc-pkg --version)
    echo "${ghc_version##* }"
}

# Bitbake will amend the WORKDIR paths it finds (staging stage 2). This works to
# our advantage for native class, target class need to be configured with their
# target dependencies, so substitute the target paths for WORKDIR starging so
# ghc-pkg finds them.
do_configure_prepend_class-target() {
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

# generate the Setup.hs file if it doesn't exist
do_configure_prepend() {
    if [ ! -e "${S}/Setup.hs" ] && [ ! -e "${S}/Setup.lhs" ] ; then
        echo "import Distribution.Simple" > "${S}/Setup.hs"
        echo "main = defaultMain" >> "${S}/Setup.hs"
    fi
}

do_configure() {
    ghc-pkg recache
    ghc-pkg --package-db "${PACKAGE_DB_PATH}" recache

    ghc_version=$(get_ghc_version)

    if [[ -d "${S}/dist" ]]; then
        ${RUNGHC} Setup.*hs clean --verbose
    fi

    # some packages supply their own configure script. if this script
    # exists, we don't need to use the ghc-* wrappers as the OE env
    # vars will be automatically used. we still need to differentiate
    # the two cases as the ghc-* arguments will actually cause these
    # packages to fail, so only utilize them if we need to.
    if [[ "${DISABLE_GHC_WRAPPERS}" != "1" ]] && [[ ! -e "${S}/configure" ]]; then
        RUNGHC_OE_OPTS="--with-gcc=ghc-cc --with-ld=ghc-ld"
        GHC_EXTRA_OPTS="-pgmc ghc-cc -pgml ghc-ld"
        HSC2HS_EXTRA_OPTS="-c ghc-cc -l ghc-ld"
    fi

    ${RUNGHC} Setup.*hs configure \
        ${RUNGHC_OE_OPTS} \
        ${EXTRA_CABAL_CONF} \
        --disable-executable-stripping \
        --disable-library-stripping \
        --ghc-options="-fPIE -dynload sysdep ${GHC_EXTRA_OPTS} -package-db ${PACKAGE_DB_PATH}" \
        --with-ghc-pkg="${STAGING_BINDIR_NATIVE}/ghc-pkg" \
        --with-hsc2hs="${STAGING_BINDIR_NATIVE}/hsc2hs" \
        --hsc2hs-options="${HSC2HS_EXTRA_OPTS} -x" \
        --ghc-pkg-options="--package-db=${PACKAGE_DB_PATH}" \
        --libsubdir="ghc-${ghc_version}/${HPN}-${HPV}" \
        --dynlibdir="${libdir}/ghc-${ghc_version}/${HPN}-${HPV}" \
        --enable-shared \
        --prefix="${prefix}" \
        --verbose
}

do_compile() {
    ${RUNGHC} Setup.*hs build \
        --verbose
}

do_local_package_conf() {
    ${RUNGHC} Setup.*hs register \
        --gen-pkg-conf \
        --verbose
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
do_fixup_rpath_class-target() {
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
    ${RUNGHC} Setup.*hs copy --destdir="${D}" --verbose

    # Prepare GHC package database files.
    if [ -f "${B}/${HPN}-${HPV}.conf" ]; then
        ghc_version=$(get_ghc_version)
        install -m 755 -d ${D}${libdir}/ghc-${ghc_version}/package.conf.d
        install -m 644 ${B}/${HPN}-${HPV}.conf ${D}${libdir}/ghc-${ghc_version}/package.conf.d
    fi
}
