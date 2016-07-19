@echo OFF
rem Batch script for windows
rem The command to be build:
rem "C:\Program Files\Java\jre1.8.0_60\bin\java.exe" -jar "C:\bin\j2m.jar"

for %%i in (java.exe) do (set JAVA_EXEC=%%~$PATH:i)
set FLAG= -jar 

set DIR=%cd%
set EXEC=j2m.jar

set TARGET="%DIR%\%EXEC%"
set cmd=%JAVA_EXEC%%FLAG%%TARGET%

rem execute the command
%cmd%
