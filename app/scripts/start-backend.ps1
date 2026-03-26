param(
    [string]$JavaHome = $env:JAVA_HOME
)

$projectDir = Resolve-Path (Join-Path $PSScriptRoot "..")

if (-not $JavaHome) {
    $candidateRoots = @(
        "C:\Program Files\Eclipse Adoptium",
        "C:\Program Files\Java"
    )

    foreach ($root in $candidateRoots) {
        if (Test-Path $root) {
            $jdk = Get-ChildItem -Path $root -Directory -ErrorAction SilentlyContinue |
                Where-Object { $_.Name -like "jdk-17*" } |
                Sort-Object Name -Descending |
                Select-Object -First 1

            if ($jdk) {
                $JavaHome = $jdk.FullName
                break
            }
        }
    }
}

if (-not $JavaHome) {
    Write-Error "No se encontro JDK 17. Define JAVA_HOME o instala Temurin 17."
    exit 1
}

$javaExe = Join-Path $JavaHome "bin\java.exe"
if (-not (Test-Path $javaExe)) {
    Write-Error "JAVA_HOME no es valido: $JavaHome"
    exit 1
}

$env:JAVA_HOME = $JavaHome
if ($env:Path -notlike "*$JavaHome\\bin*") {
    $env:Path = "$JavaHome\bin;" + $env:Path
}

Set-Location $projectDir
Write-Output "JAVA_HOME=$env:JAVA_HOME"

& ".\mvnw.cmd" spring-boot:run

