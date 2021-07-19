@echo off
echo Please wait...
if exist MyOpenLab.jar (
   echo removing jar file
   del MyOpenLab.jar
)

echo cleaning build
gradlew.bat clean
echo concluded!
