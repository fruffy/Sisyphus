#!/bin/bash


gradle -p javasymbolsolver compileJava
# cd -
# cp javasymbolsolver/java-symbol-solver-core/build/libs/java-symbol-solver-core.jar 507/lib/
# cp javasymbolsolver/java-symbol-solver-model/build/libs/java-symbol-solver-model.jar 507/lib/
# cp javasymbolsolver/java-symbol-solver-logic/build/libs/java-symbol-solver-logic.jar 507/lib/
mvn clean install -f ./javaparser/pom.xml
mvn clean install -f ./jgrapht/pom.xml
