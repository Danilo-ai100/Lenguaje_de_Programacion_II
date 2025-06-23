#!/bin/bash

echo "🛑 Deteniendo Sistema Escolar DAYIVA"
echo "===================================="

# Función para matar proceso por PID
kill_process() {
    local pid_file=$1
    local service_name=$2
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if ps -p $pid > /dev/null 2>&1; then
            echo "🔄 Deteniendo $service_name (PID: $pid)..."
            kill -15 $pid
            sleep 3
            if ps -p $pid > /dev/null 2>&1; then
                echo "🔄 Forzando detención de $service_name..."
                kill -9 $pid
            fi
            echo "✅ $service_name detenido"
        else
            echo "ℹ️  $service_name ya estaba detenido"
        fi
        rm -f "$pid_file"
    else
        echo "ℹ️  No se encontró archivo PID para $service_name"
    fi
}

# Crear directorio de logs si no existe
mkdir -p logs

# Detener servicios
kill_process "logs/backend.pid" "Backend"
kill_process "logs/frontend.pid" "Frontend"

# Detener procesos de Spring Boot
echo "🏗️ Deteniendo Backend (Spring Boot)..."
pkill -f "spring-boot:run" 2>/dev/null
pkill -f "java.*escuela.*backend" 2>/dev/null

# Detener procesos de Angular
echo "🎨 Deteniendo Frontend (Angular)..."
pkill -f "ng serve" 2>/dev/null
pkill -f "angular" 2>/dev/null

# Detener procesos en puertos específicos
echo "🔌 Liberando puertos..."
lsof -ti:8080 | xargs kill -9 2>/dev/null
lsof -ti:4200 | xargs kill -9 2>/dev/null

echo "✅ Sistema detenido correctamente"
echo "📄 Logs conservados en directorio 'logs/'"
