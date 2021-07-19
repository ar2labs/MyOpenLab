SET CLASSPATH=.;%CLASSPATH%;..\bin\Firmata.jar;
REM echo %CLASSPATH%
javac -d ..\bin  *.java
PAUSE
