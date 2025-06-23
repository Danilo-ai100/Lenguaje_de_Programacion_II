#!/bin/bash
echo "🎓 Configurando Escuelita Feliz - Sistema Completo"
echo "=================================================="

# Verificar Node.js
echo "📦 Verificando Node.js..."
if ! command -v node &> /dev/null; then
    echo "❌ Node.js no está instalado"
    echo "💡 Instala Node.js desde: https://nodejs.org/"
    exit 1
fi

echo "✅ Node.js versión: $(node --version)"

# Verificar npm
if ! command -v npm &> /dev/null; then
    echo "❌ npm no está instalado"
    exit 1
fi

echo "✅ npm versión: $(npm --version)"

# Instalar Angular CLI globalmente
echo "🔧 Instalando Angular CLI..."
npm install -g @angular/cli

echo "✅ Angular CLI versión: $(ng version --version)"

# Configurar frontend
echo "🎨 Configurando Frontend Angular..."
cd frontend
npm install
echo "✅ Dependencias de Angular instaladas"

# Volver al directorio raíz
cd ..

# Verificar Java
echo "☕ Verificando Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado"
    echo "💡 Instala Java 17+ desde: https://adoptium.net/"
    exit 1
fi

echo "✅ Java versión: $(java --version | head -n 1)"

# Verificar Maven
echo "📦 Verificando Maven..."
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado"
    echo "💡 Instala Maven desde: https://maven.apache.org/"
    exit 1
fi

echo "✅ Maven versión: $(mvn --version | head -n 1)"

# Configurar backend
echo "🏗️ Configurando Backend Spring Boot..."
cd backend
mvn clean install -DskipTests
echo "✅ Backend compilado exitosamente"

cd ..

echo ""
echo "🎉 ¡Configuración completada!"
echo "📋 Próximos pasos:"
echo "   1. Abrir IntelliJ IDEA"
echo "   2. Importar proyecto como Maven"
echo "   3. Ejecutar scripts de inicio"
