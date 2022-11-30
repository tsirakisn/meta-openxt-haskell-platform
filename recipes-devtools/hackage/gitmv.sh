pkg="$1"
next="$2"

recipe="$(ls hkg-${pkg}_*.bb)"

if [[ -z "$recipe" ]]; then
    echo "recipe not found"
    exit 1
fi

git mv "$recipe" "hkg-${pkg}_$next.bb"
