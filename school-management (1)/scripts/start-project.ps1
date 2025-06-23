# Script de PowerShell para Windows
Write-Host "🚀 Iniciando Sistema Escolar DAYIVA" -ForegroundColor Green
Write-Host "==================================" -ForegroundColor Green

# Verificar prerrequisitos
Write-Host "📋 Verificando prerrequisitos..." -ForegroundColor Yellow

# Verificar Java
try {
    $javaVersion = java -version 2>&1
    Write-Host "✅ Java encontrado" -ForegroundColor Green
} catch {
    Write-Host "❌ Java no está instalado. Instalar Java 17+" -ForegroundColor Red
    exit 1
}

# Verificar Maven
try {
    $mavenVersion = mvn -version 2>&1
    Write-Host "✅ Maven encontrado" -ForegroundColor Green
} catch {
    Write-Host "❌ Maven no está instalado. Instalar Maven 3.6+" -ForegroundColor Red
    exit 1
}

# Verificar Node.js
try {
    $nodeVersion = node --version 2>&1
    Write-Host "✅ Node.js encontrado: $nodeVersion" -ForegroundColor Green
} catch {
    Write-Host "❌ Node.js no está instalado. Instalar Node.js 18+" -ForegroundColor Red
    exit 1
}

# Verificar npm
try {
    $npmVersion = npm --version 2>&1
    Write-Host "✅ npm encontrado: $npmVersion" -ForegroundColor Green
} catch {
    Write-Host "❌ npm no está instalado" -ForegroundColor Red
    exit 1
}

# Crear directorio de logs
if (!(Test-Path "logs")) {
    New-Item -ItemType Directory -Path "logs"
}

# Iniciar Backend
Write-Host "🏗️ Iniciando Backend (Spring Boot)..." -ForegroundColor Cyan
Set-Location backend

Write-Host "📦 Compilando proyecto..." -ForegroundColor Yellow
mvn clean install -q
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Error compilando el backend" -ForegroundColor Red
    exit 1
}

Write-Host "🚀 Ejecutando Spring Boot..." -ForegroundColor Yellow
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -RedirectStandardOutput "..\logs\backend.log" -RedirectStandardError "..\logs\backend-error.log" -WindowStyle Hidden

Set-Location ..
Write-Host "✅ Backend iniciado" -ForegroundColor Green
Write-Host "📊 Backend corriendo en: http://localhost:8080" -ForegroundColor Blue

# Esperar a que el backend esté listo
Write-Host "⏳ Esperando que el backend esté listo..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# Iniciar Frontend
Write-Host "🎨 Iniciando Frontend (Angular)..." -ForegroundColor Cyan
Set-Location frontend

# Instalar dependencias si es necesario
if (!(Test-Path "node_modules")) {
    Write-Host "📦 Instalando dependencias de npm..." -ForegroundColor Yellow
    npm install
}

Write-Host "🚀 Ejecutando Angular..." -ForegroundColor Yellow
Start-Process -FilePath "ng" -ArgumentList "serve", "--host", "0.0.0.0", "--port", "4200", "--open" -RedirectStandardOutput "..\logs\frontend.log" -RedirectStandardError "..\logs\frontend-error.log" -WindowStyle Hidden

Set-Location ..
Write-Host "✅ Frontend iniciado" -ForegroundColor Green
Write-Host "🎨 Frontend corriendo en: http://localhost:4200" -ForegroundColor Blue

Write-Host ""
Write-Host "🎉 Sistema Escolar DAYIVA iniciado correctamente!" -ForegroundColor Green
Write-Host "==================================" -ForegroundColor Green
Write-Host "🌐 URLs de acceso:" -ForegroundColor White
Write-Host "   Frontend: http://localhost:4200" -ForegroundColor Blue
Write-Host "   Backend:  http://localhost:8080" -ForegroundColor Blue
Write-Host "   H2 Console: http://localhost:8080/h2-console" -ForegroundColor Blue
Write-Host ""
Write-Host "👥 Usuarios de prueba:" -ForegroundColor White
Write-Host "   Admin: admin / admin123" -ForegroundColor Yellow
Write-Host "   Padre: padre1 / padre123" -ForegroundColor Yellow
Write-Host ""
Write-Host "📄 Logs disponibles en:" -ForegroundColor White
Write-Host "   Backend: logs\backend.log" -ForegroundColor Gray
Write-Host "   Frontend: logs\frontend.log" -ForegroundColor Gray
Write-Host ""
Write-Host "Para detener el sistema, cierra esta ventana o usa Ctrl+C" -ForegroundColor Magenta

# Mantener el script corriendo
Read-Host "Presiona Enter para salir"
