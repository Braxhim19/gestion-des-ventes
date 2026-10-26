@echo off
echo ========================================
echo  SPRING BOOT + ORACLE - GESTION VENTES
echo ========================================

REM Vérifier Maven
mvn -v >nul 2>&1
if errorlevel 1 (
    echo Maven non installé
    pause
    exit /b 1
)

REM Nettoyer et compiler
echo.
echo 1. Nettoyage et compilation...
call mvn clean compile

if errorlevel 1 (
    echo Erreur de compilation
    pause
    exit /b 1
)

echo.
echo 2. Démarrage de l'application...
echo.
echo Application disponible sur: http://localhost:8081
echo.
echo Endpoints API:
echo - Test: http://localhost:8081/api/test
echo - Santé: http://localhost:8081/api/health
echo - Info: http://localhost:8081/api/info
echo - Oracle Test: http://localhost:8081/api/oracle-test
echo - Tables: http://localhost:8081/api/tables
echo - Clients: http://localhost:8081/api/clients
echo.
echo Appuyez sur Ctrl+C pour arrêter
echo.

call mvn spring-boot:run