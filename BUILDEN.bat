@echo off
cd /d "%~dp0"
where gradle >nul 2>nul
if errorlevel 1 (
 echo Gradle nicht gefunden. Nutze den GitHub-Actions-Build aus README.md.
 pause
 exit /b 1
)
gradle build --stacktrace
pause
