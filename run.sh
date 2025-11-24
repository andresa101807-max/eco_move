#!/usr/bin/env bash
set -euo pipefail

# Eco Move - Run helper
# Requisitos: Java 17, Maven Wrapper (./mvnw)
# Uso rápido:
#   ./run.sh run                 # ejecuta con spring-boot:run
#   ./run.sh build && ./run.sh jar-run  # empaqueta y ejecuta el JAR
# Opciones comunes:
#   --port 8083 | --seed true|false | --log INFO|DEBUG | --args "--foo=bar"

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$ROOT_DIR"

MVN_CMD="./mvnw"
if [[ ! -x "$MVN_CMD" ]]; then
  MVN_CMD="mvn"
fi

# Defaults basados en src/main/resources/application.properties
PORT="8083"
SEED="false"
LOG="INFO"
EXTRA_ARGS=""

function usage() {
  cat <<EOF
Eco Move - Script de ejecución

Comandos:
  run            Ejecuta con spring-boot:run (desarrollo)
  build          Compila y empaqueta el JAR (target/*.jar)
  jar-run        Ejecuta el JAR empaquetado
  run-bg         Ejecuta en segundo plano con spring-boot:run (guarda PID en .run.pid)
  jar-run-bg     Ejecuta el JAR en segundo plano (guarda PID en .run.pid)
  status         Muestra el estado del proceso en segundo plano
  stop           Detiene el proceso en segundo plano
  clean          mvn clean
  test           Ejecuta pruebas
  help           Muestra esta ayuda

Opciones:
  --port <num>     Puerto HTTP (default: 8083)
  --seed <bool>    Habilita seeding de datos 'app.seed.enabled' (default: false)
  --log <level>    Nivel logging del paquete 'co.edu.umanizales.eco_move' (default: INFO)
  --args "..."      Argumentos adicionales para la app (por ejemplo, "--foo=bar")

Ejemplos:
  ./run.sh run --port 8083 --seed true
  ./run.sh build && ./run.sh jar-run --log DEBUG
EOF
}

# parse global options after the first arg (command)
CMD="${1:-help}"
shift || true

while [[ $# -gt 0 ]]; do
  case "$1" in
    --port)
      PORT="${2:-}"; shift 2 ;;
    --seed)
      SEED="${2:-}"; shift 2 ;;
    --log)
      LOG="${2:-}"; shift 2 ;;
    --args)
      EXTRA_ARGS="${2:-}"; shift 2 ;;
    --help|-h)
      usage; exit 0 ;;
    *)
      echo "Opción desconocida: $1" >&2
      usage; exit 1 ;;
  esac
done

JAVA_OPTS=(
  "-Dserver.port=${PORT}"
  "-Dapp.seed.enabled=${SEED}"
  "-Dlogging.level.co.edu.umanizales.eco_move=${LOG}"
)

# utilidades para background
LOG_DIR="$ROOT_DIR/logs"
PID_FILE="$ROOT_DIR/.run.pid"
mkdir -p "$LOG_DIR"

start_bg() {
  local cmd=("$@")
  nohup "${cmd[@]}" >"$LOG_DIR/app.out" 2>&1 &
  local pid=$!
  echo "$pid" > "$PID_FILE"
  echo "Iniciado en segundo plano. PID=$pid"
  echo "Logs: $LOG_DIR/app.out"
}

status_bg() {
  if [[ -f "$PID_FILE" ]]; then
    local pid
    pid="$(cat "$PID_FILE" 2>/dev/null || true)"
    if [[ -n "$pid" && -e "/proc/$pid" ]] || ps -p "$pid" >/dev/null 2>&1; then
      echo "Proceso en ejecución. PID=$pid"
      return 0
    else
      echo "PID registrado ($pid) no está activo"
      return 1
    fi
  else
    echo "No hay PID registrado (.run.pid no existe)"
    return 1
  fi
}

stop_bg() {
  if [[ -f "$PID_FILE" ]]; then
    local pid
    pid="$(cat "$PID_FILE" 2>/dev/null || true)"
    if [[ -n "$pid" ]]; then
      if kill "$pid" >/dev/null 2>&1; then
        echo "Señal enviada a PID $pid"
        rm -f "$PID_FILE"
      else
        echo "No se pudo detener PID $pid (puede no estar corriendo)"
        rm -f "$PID_FILE"
      fi
    else
      echo "Archivo PID vacío; eliminando"
      rm -f "$PID_FILE"
    fi
  else
    echo "No hay proceso en segundo plano registrado"
  fi
}

case "$CMD" in
  clean)
    exec "$MVN_CMD" clean
    ;;
  build)
    exec "$MVN_CMD" -q -DskipTests package
    ;;
  test)
    exec "$MVN_CMD" -q test
    ;;
  run)
    # Desarrollo con spring-boot:run
    exec "$MVN_CMD" -q spring-boot:run \
      -Dspring-boot.run.mainClass=co.edu.umanizales.eco_move.EcoMoveApplication \
      -Dspring-boot.run.jvmArguments="${JAVA_OPTS[*]}" \
      -Dspring-boot.run.arguments="${EXTRA_ARGS}"
    ;;
  run-bg)
    # Desarrollo en segundo plano con spring-boot:run
    start_bg "$MVN_CMD" -q spring-boot:run \
      -Dspring-boot.run.mainClass=co.edu.umanizales.eco_move.EcoMoveApplication \
      -Dspring-boot.run.jvmArguments="${JAVA_OPTS[*]}" \
      -Dspring-boot.run.arguments="${EXTRA_ARGS}"
    ;;
  jar-run)
    # Ejecutar el JAR empaquetado
    JAR_FILE=""
    if ls target/*-SNAPSHOT.jar >/dev/null 2>&1; then
      JAR_FILE="$(ls -1 target/*-SNAPSHOT.jar | head -n1)"
    elif ls target/*.jar >/dev/null 2>&1; then
      JAR_FILE="$(ls -1 target/*.jar | head -n1)"
    fi
    if [[ -z "$JAR_FILE" ]]; then
      echo "No se encontró JAR en target/. Ejecuta: ./run.sh build" >&2
      exit 1
    fi
    echo "Usando JAR: $JAR_FILE"
    exec java ${JAVA_OPTS[*]} -jar "$JAR_FILE" ${EXTRA_ARGS}
    ;;
  jar-run-bg)
    # Ejecutar el JAR empaquetado en segundo plano
    JAR_FILE=""
    if ls target/*-SNAPSHOT.jar >/dev/null 2>&1; then
      JAR_FILE="$(ls -1 target/*-SNAPSHOT.jar | head -n1)"
    elif ls target/*.jar >/dev/null 2>&1; then
      JAR_FILE="$(ls -1 target/*.jar | head -n1)"
    fi
    if [[ -z "$JAR_FILE" ]]; then
      echo "No se encontró JAR en target/. Ejecuta: ./run.sh build" >&2
      exit 1
    fi
    echo "Usando JAR: $JAR_FILE"
    start_bg java ${JAVA_OPTS[*]} -jar "$JAR_FILE" ${EXTRA_ARGS}
    ;;
  status)
    status_bg || true
    ;;
  stop)
    stop_bg
    ;;
  help|*)
    usage
    ;;
esac
