@echo off
setlocal enableextensions
md target\dependency
endlocal
cd target\dependency
"%JAVA_HOME%\bin\jar" -xf ../*.jar
cd ..
cd ..
docker build -t microservice .
