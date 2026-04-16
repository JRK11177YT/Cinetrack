#!/bin/bash
# =============================================================
# CineTrack — Script de inicialización de base de datos
# Compatible con Linux / macOS
# =============================================================
# Uso:
#   bash scripts/setup-db.sh
#   bash scripts/setup-db.sh --password "tupassword"
#   bash scripts/setup-db.sh --host 127.0.0.1 --port 3306 --user root --password ""
# =============================================================

MYSQL_HOST="127.0.0.1"
MYSQL_PORT="3306"
MYSQL_USER="root"
MYSQL_PASSWORD=""

# Parsear argumentos
while [[ "$#" -gt 0 ]]; do
    case $1 in
        --host)     MYSQL_HOST="$2";     shift ;;
        --port)     MYSQL_PORT="$2";     shift ;;
        --user)     MYSQL_USER="$2";     shift ;;
        --password) MYSQL_PASSWORD="$2"; shift ;;
        *) echo "Argumento desconocido: $1"; exit 1 ;;
    esac
    shift
done

echo ""
echo "===================================================="
echo "  CineTrack — Inicialización de Base de Datos"
echo "===================================================="
echo ""

# Verificar que mysql está disponible
if ! command -v mysql &> /dev/null; then
    echo "ERROR: No se encontró el ejecutable 'mysql'."
    echo "  Instala MySQL o XAMPP y asegúrate de que está en el PATH."
    exit 1
fi

# Ruta al script de inicialización
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
INIT_SQL="$PROJECT_ROOT/database/init.sql"

if [ ! -f "$INIT_SQL" ]; then
    echo "ERROR: No se encontró database/init.sql en $PROJECT_ROOT"
    exit 1
fi

echo "MySQL: $(command -v mysql)"
echo "Script SQL: $INIT_SQL"
echo ""

# Construir argumentos de conexión
MYSQL_ARGS="-h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_USER"
if [ -n "$MYSQL_PASSWORD" ]; then
    MYSQL_ARGS="$MYSQL_ARGS -p$MYSQL_PASSWORD"
fi

# Ejecutar el script
echo "Creando base de datos y cargando datos..."
if mysql $MYSQL_ARGS < "$INIT_SQL"; then
    echo ""
    echo "Base de datos 'cinetrack_db' creada e inicializada correctamente."
    echo ""
    echo "Próximos pasos:"
    echo "  1. cd app"
    echo "  2. ./mvnw spring-boot:run"
    echo "  3. Abre http://localhost:8080"
    echo "  4. Login: admin@cinetrack.com / admin123"
    echo ""
else
    echo ""
    echo "ERROR al ejecutar el script SQL."
    echo "Comprueba que MySQL está arrancado y que los datos de conexión son correctos."
    exit 1
fi
