#!/bin/bash
#The command to be built:
#/usr/bin/java -jar /Users/alfredo/Github/Json2Model/bin/j2m.jar
JAVA_EXEC="$( which java)"
FLAG=" -jar "

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)"
EXEC="j2m.jar"
TARGET=$DIR"/"$EXEC
TARGET_ARGS=" $@"

CDM="$JAVA_EXEC"

$JAVA_EXEC$FLAG$TARGET$TARGET_ARGS