rm -rf src/main/gen-java
mkdir src/main/gen-java

#Bare generation
#wsimport -target 2.2 -s src/main/gen-java -Xnocompile src/main/resources/wsdl/responsibility.v1.wsdl

#with binding for WSDL
#wsimport -target 2.2 -s src/main/gen-java -b src/main/resources/wsdl/responsibility.v1.wsdl.bindings.xml -Xnocompile src/main/resources/wsdl/responsibility.v1.wsdl


#with binding for WSDL and XSD
wsimport -target 2.2 -s src/main/gen-java -b src/main/resources/wsdl/responsibility.v1.wsdl.bindings.xml -b src/main/resources/wsdl/responsibility.v1.xsd.bindings.xml -Xnocompile src/main/resources/wsdl/responsibility.v1.wsdl
