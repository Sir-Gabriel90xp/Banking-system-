@echo off
setlocal
cd /d "%~dp0"

echo Starting Banking System frontend on http://localhost:4200/
echo.
echo If port 4200 is already in use, the frontend is probably already running.
echo.

npm.cmd run start
