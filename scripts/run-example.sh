#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 1 ]]; then
  echo "Використання: ./scripts/run-example.sh 01-hello" >&2
  exit 2
fi

example="$1"
case "$example" in
  01-hello|01-basics-exercises|02-values|03-conditions-loops|04-functions|05-collections-optionals|06-structs-enums|07-protocols-generics|08-errors-async) ;;
  *) echo "Невідомий приклад: $example" >&2; exit 2 ;;
esac

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
source="$root/examples/$example/main.swift"
build_dir="$root/build"
output="$build_dir/$example"

mkdir -p "$build_dir"
if [[ "$example" == "08-errors-async" ]]; then
  swiftc -parse-as-library "$source" -o "$output"
else
  swiftc "$source" -o "$output"
fi
"$output"
