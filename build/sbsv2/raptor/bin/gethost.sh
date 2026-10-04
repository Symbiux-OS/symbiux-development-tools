#!/bin/bash


# Copyright (c) 2006-2010 Nokia Corporation and/or its subsidiary(-ies).
# All rights reserved.
# This component and the accompanying materials are made available
# under the terms of the License "Eclipse Public License v1.0"
# which accompanies this distribution, and is available
# at the URL "http://www.eclipse.org/legal/epl-v10.html".
#
# Initial Contributors:
# Nokia Corporation - initial contribution.
#
# Contributors:
#
# Description:
# Determine hosttype
#
#

# Print out a list of host information in order of significance.
# for use within Makefiles and other scripts.
# The idea is that it should be possible to use it for simple decisions
# e.g. windows/linux and more complex ones e.g. i386/x86_64

MODE=platform
while getopts ":de" OPT; do
	case "$OPT" in
		d) MODE=directory ;;
		e) MODE=exports ;;
		*) printf 'Usage: %s [-d|-e]\n' "$0" >&2; exit 2 ;;
	esac
done

KERNEL_NAME="$(uname -s 2>/dev/null || true)"

if [ "$KERNEL_NAME" = "Linux" ]; then
	ARCH="$(uname -m 2>/dev/null || true)"
	case "$ARCH" in
		i?86) ARCH="i386" ;;
		amd64) ARCH="x86_64" ;;
	esac
	if [[ ! "$ARCH" =~ ^[A-Za-z0-9_]+$ ]]; then
		printf 'Error: unsupported Linux architecture %q.\n' "$ARCH" >&2
		exit 1
	fi

	LIBC_DESCRIPTION="$(LC_ALL=C getconf GNU_LIBC_VERSION 2>/dev/null || true)"
	if [[ ! "$LIBC_DESCRIPTION" =~ ([0-9]+)\.([0-9]+) ]] && command -v ldd >/dev/null 2>&1; then
		LIBC_DESCRIPTION="$(LC_ALL=C ldd --version 2>&1 | sed -n '1p')"
	fi

	if [[ "$LIBC_DESCRIPTION" =~ ([0-9]+)\.([0-9]+) ]]; then
		LIBC="libc${BASH_REMATCH[1]}_${BASH_REMATCH[2]}"
	else
		printf 'Error: unable to determine the GNU libc version.\n' >&2
		exit 1
	fi

	HOSTPLATFORM="linux ${ARCH} ${LIBC}"
	case "$ARCH" in
		i386|x86_64) ARCH32="i386" ;;
		*) ARCH32="$ARCH" ;;
	esac

	HOSTPLATFORM_DIR="linux-${ARCH}-${LIBC}"
	HOSTPLATFORM32_DIR="linux-${ARCH32}-${LIBC}"
elif [[ "${OS:-}" == "Windows_NT" || "$KERNEL_NAME" == CYGWIN* || "$KERNEL_NAME" == MINGW* || "$KERNEL_NAME" == MSYS* ]]; then
	HOSTPLATFORM="win 32"
	HOSTPLATFORM_DIR="win32"
	HOSTPLATFORM32_DIR="win32"
else
	printf 'Error: unsupported host operating system %q.\n' "$KERNEL_NAME" >&2
	exit 1
fi

if [ "$MODE" = "exports" ]; then
	printf 'export HOSTPLATFORM_DIR=%q\n' "$HOSTPLATFORM_DIR"
	printf 'export HOSTPLATFORM32_DIR=%q\n' "$HOSTPLATFORM32_DIR"
	printf 'export HOSTPLATFORM=%q\n' "$HOSTPLATFORM"
elif [ "$MODE" = "directory" ]; then
	printf '%s\n' "$HOSTPLATFORM_DIR"
else
	printf '%s\n' "$HOSTPLATFORM"
fi
