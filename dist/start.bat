@echo off
@echo on
set JAVA_HOME=%cd%\jre
echo %JAVA_HOME%
@echo off
%JAVA_HOME%\bin\java.exe -jar %cd%\nosqlClient.jar
rem exit 

rem pause 

CMD