# =============================================================
# CineTrack — Script de inicialización de base de datos
# Compatible con XAMPP (Windows)
# =============================================================
# Uso:
#   .\scripts\setup-db.ps1
#   .\scripts\setup-db.ps1 -Password "tupassword"
#   .\scripts\setup-db.ps1 -Host "127.0.0.1" -Port 3307 -User "root" -Password ""
# =============================================================

param(
    [string]$MySqlHost = "127.0.0.1",
    [int]$Port         = 3306,
    [string]$User      = "root",
    [string]$Password  = ""
)

$ErrorActionPreference = "Stop"

Write-Host ""
Write-Host "====================================================" -ForegroundColor Cyan
Write-Host "  CineTrack — Inicialización de Base de Datos" -ForegroundColor Cyan
Write-Host "====================================================" -ForegroundColor Cyan
Write-Host ""

# Buscar ejecutable mysql
$mysqlExe = $null

# 1. Intentar desde PATH
try {
    $mysqlExe = (Get-Command "mysql" -ErrorAction SilentlyContinue).Source
} catch {}

# 2. Rutas comunes de XAMPP en Windows
if (-not $mysqlExe) {
    $candidates = @(
        "C:\xampp\mysql\bin\mysql.exe",
        "C:\xampp8\mysql\bin\mysql.exe",
        "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe",
        "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe",
        "C:\Program Files (x86)\MySQL\MySQL Server 8.0\bin\mysql.exe"
    )
    foreach ($c in $candidates) {
        if (Test-Path $c) {
            $mysqlExe = $c
            break
        }
    }
}

if (-not $mysqlExe) {
    Write-Host "ERROR: No se encontro el ejecutable 'mysql'." -ForegroundColor Red
    Write-Host "  - Asegurate de que MySQL/XAMPP esta instalado." -ForegroundColor Yellow
    Write-Host "  - O agrega la carpeta bin de MySQL al PATH." -ForegroundColor Yellow
    exit 1
}

Write-Host "MySQL encontrado en: $mysqlExe" -ForegroundColor DarkGray

# Ruta al script de inicializacion
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $scriptRoot
$initSql = Join-Path $projectRoot "database\init.sql"

if (-not (Test-Path $initSql)) {
    Write-Host "ERROR: No se encontro database\init.sql en $projectRoot" -ForegroundColor Red
    exit 1
}

Write-Host "Script SQL: $initSql" -ForegroundColor DarkGray
Write-Host ""

# Construir argumentos de conexion
$args = @("-h", $MySqlHost, "-P", $Port, "-u", $User)
if ($Password -ne "") {
    $args += "-p$Password"
}

# Ejecutar el script
Write-Host "Creando base de datos y cargando datos..." -ForegroundColor Yellow
try {
    Get-Content $initSql | & $mysqlExe @args
    if ($LASTEXITCODE -ne 0) { throw "MySQL devolvio codigo de error $LASTEXITCODE" }
} catch {
    Write-Host ""
    Write-Host "ERROR al ejecutar el script SQL: $_" -ForegroundColor Red
    Write-Host "Comprueba que MySQL esta arrancado (XAMPP > Start MySQL)." -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "Base de datos 'cinetrack_db' creada e inicializada correctamente." -ForegroundColor Green
Write-Host ""
Write-Host "Proximos pasos:" -ForegroundColor Cyan
Write-Host "  1. cd app" -ForegroundColor White
Write-Host "  2. .\mvnw.cmd spring-boot:run" -ForegroundColor White
Write-Host "  3. Abre http://localhost:8080" -ForegroundColor White
Write-Host "  4. Login: admin@cinetrack.com / admin123" -ForegroundColor White
Write-Host ""
