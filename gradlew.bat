@echo off
where gradle >nul 2>nul
if errorlevel 1 (
  echo Gradle is not installed on this build machine.
  echo Install Gradle or use a build image that includes Gradle.
  exit /b 1
)

gradle %*
