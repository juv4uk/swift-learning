param(
    [Parameter(Mandatory = $true)]
    [ValidateSet(
        "01-hello",
        "01-basics-exercises",
        "02-values",
        "03-conditions-loops",
        "04-functions",
        "05-collections-optionals",
        "06-structs-enums",
        "07-protocols-generics",
        "08-errors-async"
    )]
    [string]$Example
)

$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
$source = Join-Path $root "examples\$Example\main.swift"
$buildDir = Join-Path $root "build"
$output = Join-Path $buildDir "$Example.exe"

if (-not (Test-Path -LiteralPath $source)) {
    throw "Не знайдено приклад: $source"
}

New-Item -ItemType Directory -Path $buildDir -Force | Out-Null
if ($Example -eq "08-errors-async") {
    swiftc -parse-as-library $source -o $output
} else {
    swiftc $source -o $output
}
if ($LASTEXITCODE -ne 0) {
    throw "Компіляція завершилася з кодом $LASTEXITCODE"
}

& $output
if ($LASTEXITCODE -ne 0) {
    throw "Приклад завершився з кодом $LASTEXITCODE"
}
