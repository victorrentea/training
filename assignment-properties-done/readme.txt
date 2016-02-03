1) Using XML to configure the expected properties definitions proved to be overkill. It just doesn't worth the complexity. Choosing bare .properties format.
2) DID NOT added an explicit compile dependency to Amazon AWS API (set to "runtime" scope in pom.xml). Instead, the enum class name configured in the properties-definitions.properties is loaded (forName) and the given enum value is looked up among the enum's values. This way, the projects using this code won't be bound to Amazon API (won't inherit the dependencey transitively).
3) DID NOT define LONG or DOUBLE property types: I believe there are quite rare and can be added when needed.
4) Very unlikely to use system property override for classpath resource name of the definitions.properties file. This can easily be removed.
5) JsonParse and PropertiesParse do not have to know about .json and .properties convention. That's something in the scope of the FileConfigSource. 
6) I chose UTF-8 as the default encoding for read files
7) Given source URI1 file defines a valid (cf known type) for a key, but the value defined in URI2 property file is INVALID (e.g. "ok" for a Boolean), IF URI1 is listed before URI2 in the list of source URIs, then the final value of the variable is INVALID (that is - error). I believe it's the most reasonable behavior for this case: "the override was invalid -> error"
8) Assumption: AppProperties.getKnownProperties() returns ALL properties (those set + those unset/invalid).
9) I wanted to make AppProperties immutable (thread safety, simplicity), but the required API didn't allowed me to. But since I had to implement the .clear(), I had it invalidating the instance forever, because you can never add/set new keys in that AppProperties instance. So the required API was not entirely consistent: it required .clear() but didn't require any "putProperty()" => that's why it stretched my design a bit.
10) In AppProperties, I stored the keys by normalized value to optimize the resolve time (O(1)).
11) Did not implement and programmatic Singleton pattern to ease testing. Also, if used in a large application, I believe a container solution will be used (e.g. Spring,EJB,SEAM) that would allow to manage a single instance of TrialAppPropertiesManager. 
12) Slight deviation from the assignment description: I'm loading any resource name that follows "classpath:" and not just "classpath:resources/" (as the requirements mention)
13) The JsonParser and PropertiesParser decide for themselves if they can process a certain resource, given the location of that resource + the actual text content of that resource. That way, the parser can be easily modified to tentatively parse the content in their accepts() method, rendering the use of extension conventions useless (.json).   
14) You won't encounter comments in code: please let us discuss this point in the call we'll have.
15) The code is scarcely commented, intentionally. Please allow me to discuss this point with you over the phone. In short: the clean code rules I followed rendered almost all comments useless.

Extension Point: Load properties from LDAP or other non-textual format:
	a) Implement a new ConfigLoader that does not subclass InputStreamConfigLoader.	(already defined such a dummy ConfigLoader in unit tests as an example)
	b) Add it to the LOADERS list of TrialAppPropertiesManager (either changing the code or invoking the respective static add...() method)

Extension Point: Load properties from XML or other textual format:
	a) Implement a subclass of ConfigTextParser 
	b) Add it to the PARSERS list of StringPairStreamLoader (either changing the code or invoking the respective static add...() method)

Extension Point: Customize the parsing of text->objects
	a) Subclass PropertyValueParser and override the appropriate method
	b) set an instance of your class as the setValueParser() of the TrialAppPropertiesManager
	
Extension Point Point: Loading properties definitions from other sources (e.g. XML): 
	By extracting an interface from PropertyDefinitionsManager, alternative implementations could fetch the required properties from other sources. eg: XML file, or 
	(very interesting!!) directly from code, by scanning for all injection points and determine the expected type by reflective inspection. 
	This could allow the migration towards Dependency Inversion (IoC), to clean the code of imperative requests for configuration properties.

Extension Point: add a new type of property (e.g. DATE): 
	a) add a new value to the enum PropertyType, 
	b) add a method to PropertyValueParser - won't break any custom extension
	c) change TrialAppPropertiesManager to delegate to the method created at (b)  

