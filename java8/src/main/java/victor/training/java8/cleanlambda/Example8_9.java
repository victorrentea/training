package victor.training.java8.cleanlambda;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import victor.training.java8.cleanlambda.Example8_9.Document.Type;

public class Example8_9 {
	
	enum Attribute {
		COUNTRY_ISO
	, TYPE1_ATTR1}
	
	@interface Inject {
		
	}
	
	
	//////////
	
	@Inject
	private DocumentRepository documentRepository;

	private Map<Attribute, Supplier<List<ValueToDocument>>> attributeSuppliers = new HashMap<>();

	@PostConstruct
	public void setupSuppliers() {
	     attributeSuppliers.put(Attribute.COUNTRY_ISO, documentRepository::getAllByCountryIso);
	     // [HOLE] FOR (Exercise 9)
	}

	// --- Given ----
	public class DocumentRepository { 
	     public List<ValueToDocument> getAllByCountryIso() {/* ... */return null;}										public List<ValueToDocument> getAllTypes() {return null;}
	}

	      
      ///////////////////////////////////////
	
	{	
		attributeSuppliers.put(Attribute.TYPE1_ATTR1, ofType(documentRepository::getAllTypes, Type.TYPE1));
	}
		public static Supplier<List<ValueToDocument>> ofType(
		         Supplier<List<ValueToDocument>> originalSupplier, Document.Type docType) {
			
		    return () -> originalSupplier.get().stream()
		                    .filter(v -> v.getDocument().getType() == docType)
		                    .collect(toList());
		}
		// ---- Given ----
		public class ValueToDocument {
		  private String value;
		  private Document document;   /* ... */	                                                                        public Document getDocument() {		return document;	}
		}
static	public class Document { enum Type {TYPE1} /* ... */														private Type type; public Type getType() {return type;} 
		}

}
