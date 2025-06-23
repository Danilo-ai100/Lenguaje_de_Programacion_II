#!/bin/bash

echo "🚀 Iniciando Sistema Escolar DAYIVA"
echo "=================================="

# Verificar prerrequisitos
echo "📋 Verificando prerrequisitos..."

# Verificar Java
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado. Instalar Java 17+"
    exit 1
fi

# Verificar Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado. Instalar Maven 3.6+"
    exit 1
fi

# Verificar Node.js
if ! command -v node &> /dev/null; then
    echo "❌ Node.js no está instalado. Instalar Node.js 18+"
    exit 1
fi

# Verificar npm
if ! command -v npm &> /dev/null; then
    echo "❌ npm no está instalado. Instalar npm"
    exit 1
fi

echo "✅ Todos los prerrequisitos están instalados"

# Crear directorios de logs
mkdir -p logs

# Función para limpiar procesos al salir
cleanup() {
    echo "🛑 Deteniendo servicios..."
    kill $BACKEND_PID 2>/dev/null
    kill $FRONTEND_PID 2>/dev/null
    exit 0
}

# Capturar señales para limpieza
trap cleanup SIGINT SIGTERM

# Función para matar procesos en puertos específicos
kill_port() {
    local port=$1
    local pid=$(lsof -ti:$port)
    if [ ! -z "$pid" ]; then
        echo "🔄 Matando proceso en puerto $port (PID: $pid)"
        kill -9 $pid
        sleep 2
    fi
}

# Matar procesos existentes
kill_port 8080
kill_port 4200

# Iniciar Backend (Spring Boot)
echo "🏗️ Iniciando Backend (Spring Boot)..."
cd backend
mvn clean install -q
if [ $? -ne 0 ]; then
    echo "❌ Error compilando el backend"
    exit 1
fi

mvn spring-boot:run > ../logs/backend.log 2>&1 &
BACKEND_PID=$!
cd ..

echo "✅ Backend iniciado (PID: $BACKEND_PID)"
echo "📊 Backend corriendo en: http://localhost:8080"

# Esperar a que el backend esté listo
echo "⏳ Esperando que el backend esté listo..."
sleep 10

# Verificar que el backend esté corriendo
if ! curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "⚠️ Backend aún no está listo, esperando más..."
    sleep 10
fi

# Iniciar Frontend (Angular)
echo "🎨 Iniciando Frontend (Angular)..."
cd frontend

# Instalar dependencias si es necesario
if [ ! -d "node_modules" ]; then
    echo "📦 Instalando dependencias de npm..."
    npm install
fi

ng serve --host 0.0.0.0 --port 4200 --open > ../logs/frontend.log 2>&1 &
FRONTEND_PID=$!
cd ..

echo "✅ Frontend iniciado (PID: $FRONTEND_PID)"
echo "🎨 Frontend corriendo en: http://localhost:4200"

echo ""
echo "🎉 Sistema Escolar DAYIVA iniciado correctamente!"
echo "=================================="
echo "🌐 URLs de acceso:"
echo "   Frontend: http://localhost:4200"
echo "   Backend:  http://localhost:8080"
echo "   H2 Console: http://localhost:8080/h2-console"
echo ""
echo "👥 Usuarios de prueba:"
echo "   Admin: admin / admin123"
echo "   Padre: padre1 / padre123"
echo ""
echo "📋 Para detener el sistema, presiona Ctrl+C"
echo "📄 Logs disponibles en:"
echo "   Backend: logs/backend.log"
echo "   Frontend: logs/frontend.log"

# Mantener el script corriendo
wait
