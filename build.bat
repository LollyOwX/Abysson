@echo off
setlocal

set JAVAC="C:\Users\logan\.jdks\ms-25.0.3\bin\javac.exe"
set SRC_DIR=src
set OUT_DIR=out\production\Abysson
set LOG_FILE=build.log

if not exist %OUT_DIR% mkdir %OUT_DIR%

dir /s /b %SRC_DIR%\*.java > sources.txt

%JAVAC% -d %OUT_DIR% -cp %OUT_DIR% @sources.txt > %LOG_FILE% 2>&1
set BUILD_RESULT=%ERRORLEVEL%

del sources.txt

if %BUILD_RESULT% NEQ 0 (
    echo.
    echo BUILD FALLITA - vedi build.log
    exit /b 1
) else (
    echo BUILD OK > %LOG_FILE%
    exit /b 0
)