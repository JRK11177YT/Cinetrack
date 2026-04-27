# =============================================================
# CineTrack — Arranque completo del proyecto
# Uso: .\start.ps1
#      .\start.ps1 -DbPassword "tu_contraseña"
# =============================================================

param(
    [string]$DbHost     = "127.0.0.1",
    [int]   $DbPort     = 3306,
    [string]$DbUser     = "root",
    [string]$DbPassword = ""
)

$ErrorActionPreference = "Stop"

Write-Host ""
Write-Host "====================================================" -ForegroundColor Cyan
Write-Host "  CineTrack — Arranque" -ForegroundColor Cyan
Write-Host "====================================================" -ForegroundColor Cyan
Write-Host ""

# ------------------------------------------------------------------
# 1. Buscar ejecutable mysql
# ------------------------------------------------------------------
$mysqlExe = $null
try { $mysqlExe = (Get-Command "mysql" -ErrorAction SilentlyContinue).Source } catch {}

if (-not $mysqlExe) {
    $candidates = @(
        "D:\basededatos\mysql\bin\mysql.exe",
        "C:\xampp\mysql\bin\mysql.exe",
        "C:\xampp8\mysql\bin\mysql.exe",
        "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe",
        "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe"
    )
    foreach ($c in $candidates) {
        if (Test-Path $c) { $mysqlExe = $c; break }
    }
}

if (-not $mysqlExe) {
    Write-Host "ERROR: No se encontro mysql.exe." -ForegroundColor Red
    Write-Host "  Instala XAMPP o MySQL y asegurate de que esta arrancado." -ForegroundColor Yellow
    exit 1
}

# ------------------------------------------------------------------
# 2. Verificar que MySQL esta accesible
# ------------------------------------------------------------------
Write-Host "[1/3] Comprobando conexion a MySQL ($DbHost : $DbPort)..." -ForegroundColor Yellow

$mysqlArgs = @("-h", $DbHost, "-P", $DbPort, "-u", $DbUser, "--connect-timeout=5")
if ($DbPassword -ne "") { $mysqlArgs += "-p$DbPassword" }
$mysqlArgs += @("-e", "SELECT 1;")

try {
    & "$mysqlExe" @mysqlArgs 2>&1 | Out-Null
    if ($LASTEXITCODE -ne 0) { throw }
    Write-Host "  MySQL accesible." -ForegroundColor Green
} catch {
    Write-Host ""
    Write-Host "ERROR: No se puede conectar a MySQL." -ForegroundColor Red
    Write-Host "  -> Abre XAMPP y pulsa Start en MySQL." -ForegroundColor Yellow
    Write-Host "  -> Si tiene contrasena: .\start.ps1 -DbPassword 'tu_pass'" -ForegroundColor Yellow
    exit 1
}

# ------------------------------------------------------------------
# 3. Crear la base de datos si no existe
# ------------------------------------------------------------------
Write-Host "[2/3] Verificando base de datos 'cinetrack_db'..." -ForegroundColor Yellow

$checkArgs = @("-h", $DbHost, "-P", $DbPort, "-u", $DbUser)
if ($DbPassword -ne "") { $checkArgs += "-p$DbPassword" }
$checkArgs += @("cinetrack_db", "-e", "SELECT COUNT(*) FROM peliculas;")

$dbCheck = & "$mysqlExe" @checkArgs 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "  Base de datos ya existe. Saltando inicializacion." -ForegroundColor Green
} else {
    Write-Host "  Base de datos no encontrada. Inicializando..." -ForegroundColor Yellow
    $initSql = Join-Path $PSScriptRoot "database\init.sql"
    if (-not (Test-Path $initSql)) {
        Write-Host "ERROR: No se encontro database\init.sql" -ForegroundColor Red
        exit 1
    }
    # Usamos cmd /c con redireccion nativa para preservar UTF-8.
    # Get-Content | mysql corrompe tildes y caracteres especiales.
    $passArg = if ($DbPassword -ne "") { "-p$DbPassword" } else { "" }
    $cmdLine = if ($passArg) {
        "`"$mysqlExe`" -h $DbHost -P $DbPort -u $DbUser $passArg --default-character-set=utf8mb4 < `"$initSql`""
    } else {
        "`"$mysqlExe`" -h $DbHost -P $DbPort -u $DbUser --default-character-set=utf8mb4 < `"$initSql`""
    }
    cmd /c $cmdLine
    if ($LASTEXITCODE -ne 0) {
        Write-Host "ERROR al inicializar la base de datos." -ForegroundColor Red
        exit 1
    }
    Write-Host "  Base de datos creada y datos cargados." -ForegroundColor Green
}

# ------------------------------------------------------------------
# 4. Arrancar Spring Boot
# ------------------------------------------------------------------
Write-Host "[3/3] Arrancando CineTrack..." -ForegroundColor Yellow
Write-Host ""
Write-Host "  URL:   http://localhost:8080" -ForegroundColor White
Write-Host "  Admin: admin@cinetrack.com / admin123" -ForegroundColor White
Write-Host ""
Write-Host "  Pulsa Ctrl+C para detener el servidor." -ForegroundColor DarkGray
Write-Host ""

$appDir = Join-Path $PSScriptRoot "app"
Set-Location $appDir

$env:SPRING_PROFILES_ACTIVE = "dev"

& ".\mvnw.cmd" spring-boot:run
