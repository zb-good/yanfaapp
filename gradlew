#!/usr/bin/env sh
set -e

if ! command -v gradle >/dev/null 2>&1; then
  echo "Gradle is not installed on this build machine." >&2
  echo "Install Gradle or use a build image that includes Gradle." >&2
  exit 1
fi

exec gradle "$@"
