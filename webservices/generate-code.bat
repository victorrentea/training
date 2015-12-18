mkdir src\main\gen

#Bare generation
#wsimport -target 2.2 -s src/main/gen -Xnocompile src/main/resources/wsdl/responsibility.v1.wsdl

#with binding for WSDL
#wsimport -target 2.2 -s src/main/gen -b src/main/resources/wsdl/responsibility.v1.wsdl.bindings.xml -Xnocompile src/main/resources/wsdl/responsibility.v1.wsdl


#with binding for WSDL and XSD
wsimport -target 2.2 -s src/main/gen -b src/main/resources/wsdl/responsibility.v1.wsdl.bindings.xml -b src/main/resources/wsdl/responsibility.v1.xsd.bindings.xml -Xnocompile src/main/resources/wsdl/responsibility.v1.wsdl
