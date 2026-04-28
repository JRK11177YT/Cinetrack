# =============================================================
# CineTrack — Script de inicialización de base de datos
# Compatible con Windows + XAMPP
# =============================================================
# Uso:
#   .\scripts\setup-db.ps1
#   .\scripts\setup-db.ps1 -RootPassword "tupassword"
#   .\scripts\setup-db.ps1 -Host 127.0.0.1 -Port 3306 -RootPassword ""
#
# REQUISITOS:
#   - XAMPP instalado con MySQL arrancado
#   - mysql.exe en el PATH  o  XAMPP instalado en C:\xampp
#
# Lo que hace este script:
#   1. Crea la BD y el usuario  (database/init.sql)
#   2. Carga géneros y películas (database/data.sql)
# =============================================================

param(
    [string]$MySQLHost     = "127.0.0.1",
    [string]$Port          = "3306",
    [string]$RootUser      = "root",
    [string]$RootPassword  = ""
)

Write-Host ""
Write-Host "===================================================="
Write-Host "  CineTrack — Inicializacion de Base de Datos"
Write-Host "  (Windows / XAMPP)"
Write-Host "===================================================="
Write-Host ""

# ── Localizar mysql.exe ──────────────────────────────────────
$mysql = Get-Command mysql -ErrorAction SilentlyContinue | Select-Object -ExpandProperty Source

if (-not $mysql) {
    $xamppPath = "C:\xampp\mysql\bin\mysql.exe"
    if (Test-Path $xamppPath) {
        $mysql = $xamppPath
    } else {
        Write-Host "ERROR: No se encontro mysql.exe."
        Write-Host "  Asegurate de que XAMPP esta instalado en C:\xampp"
        Write-Host "  o anade mysql.exe al PATH del sistema."
        exit 1
    }
}

Write-Host "MySQL: $mysql"

# ── Localizar scripts SQL ────────────────────────────────────
$scriptDir   = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $scriptDir
$initSql     = Join-Path $projectRoot "database\init.sql"
$dataSql     = Join-Path $projectRoot "database\data.sql"

if (-not (Test-Path $initSql)) {
    Write-Host "ERROR: No se encontro database\init.sql en $projectRoot"
    exit 1
}
if (-not (Test-Path $dataSql)) {
    Write-Host "ERROR: No se encontro database\data.sql en $projectRoot"
    exit 1
}

Write-Host "Esquema : $initSql"
Write-Host "Datos   : $dataSql"
Write-Host ""

# ── Construir argumentos de conexion ────────────────────────
$mysqlArgs = @("-h", $MySQLHost, "-P", $Port, "-u", $RootUser)
if ($RootPassword -ne "") {
    $mysqlArgs += "-p$RootPassword"
}

# ── Ejecutar ─────────────────────────────────────────────────
Write-Host "[1/2] Creando esquema, BD y usuario..."
Get-Content $initSql -Raw | & $mysql @mysqlArgs
if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "ERROR al ejecutar init.sql. Abortando."
    exit 1
}

Write-Host "[2/2] Cargando datos de ejemplo..."
Get-Content $dataSql -Raw | & $mysql @mysqlArgs

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "===================================================="
    Write-Host "  Inicializacion completada correctamente."
    Write-Host "===================================================="
    Write-Host ""
    Write-Host "  Base de datos : cinetrack_db"
    Write-Host "  Usuario BD    : cinetrack_user"
    Write-Host "  Password BD   : Cinetrack2024!"
    Write-Host ""
    Write-Host "Proximos pasos:"
    Write-Host "  1. cd app"
    Write-Host "  2. .\mvnw.cmd spring-boot:run"
    Write-Host "  3. Abre http://localhost:8080"
    Write-Host "  4. Login admin: admin@cinetrack.com / admin123"
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "ERROR al ejecutar el script SQL."
    Write-Host "Comprueba que XAMPP / MySQL esta arrancado y que la contrasena de root es correcta."
    Write-Host ""
    Write-Host "Uso con contrasena:"
    Write-Host "  .\scripts\setup-db.ps1 -RootPassword 'tu_password'"
    exit 1
}
