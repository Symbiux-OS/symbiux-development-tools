#!/usr/bin/env bash

# Copyright (c) 2026 Symbiux OS contributors.
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v1.0.

set -Eeuo pipefail

script_dir="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
rcomp_root="$(CDPATH= cd -- "$script_dir/.." && pwd)"
rcomp_bin="${1:-$rcomp_root/build/rcomp}"
test_sources="$rcomp_root/tsrc"

if [ ! -x "$rcomp_bin" ]; then
	printf 'FAIL: RCOMP binary is not executable: %s\n' "$rcomp_bin" >&2
	exit 1
fi

tmp_base="${TMPDIR:-/tmp}"
test_root="$(mktemp -d "$tmp_base/symbiux-rcomp-test.XXXXXX")"
cleanup() {
	rm -rf -- "$test_root"
}
trap cleanup EXIT

set +e
version_output="$("$rcomp_bin" 2>&1)"
version_status=$?
set -e

if [ "$version_status" -ne 255 ]; then
	printf 'FAIL: RCOMP without arguments returned %s; expected 255.\n' \
		"$version_status" >&2
	exit 1
fi

case "$version_output" in
	*'Resource compiler version 8.4 (Build 002)'*) ;;
	*)
		printf '%s\n' "$version_output" >&2
		printf '%s\n' \
			'FAIL: RCOMP did not report version 8.4 (Build 002).' >&2
		exit 1
		;;
esac

printf '%s\n' 'PASS: RCOMP version and no-argument status'

run_golden_test() {
	fixture="$1"
	golden="$2"
	generated="$test_root/$golden"
	log="$test_root/$fixture.log"

	if ! (
		cd "$test_sources"
		"$rcomp_bin" -u "-s$fixture.RSS" "-h$generated"
	) >"$log" 2>&1; then
		cat "$log" >&2
		printf 'FAIL: RCOMP could not compile %s.RSS.\n' "$fixture" >&2
		exit 1
	fi

	if ! cmp -s "$test_sources/$golden" "$generated"; then
		printf 'FAIL: generated %s differs from its golden file.\n' \
			"$golden" >&2
		diff -u "$test_sources/$golden" "$generated" >&2 || true
		exit 1
	fi

	printf 'PASS: %s matches %s\n' "$fixture" "$golden"
}

run_golden_test TUTEXT TUTEXT0.RSG
run_golden_test TWTEXT TWTEXT0.RSG

printf '%s\n' 'RCOMP tests passed.'
