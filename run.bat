@echo off
:: compile
javac -d bin -cp "lib/*" src/*.java src/program/*.java

:: run program
java -cp "bin;lib/*" Main

pause

