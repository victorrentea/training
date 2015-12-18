mkdir src/main/gen

wsimport -target 2.2 -s src/main/gen -Xnocompile src/main/resources/wsdl/responsibility.v1.wsdl
