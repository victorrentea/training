package victor.training.oo.creational.factorymethod;

public class OutputFile {
	private final String baseName;
	private final String extension;
	private final char separator;
	
	public OutputFile(String baseName, String extension, char separator) {
		this.baseName = baseName;
		this.extension = extension;
		this.separator = separator;
	}
	
//	public OutputFile(String baseName) { // INITIAL(
//		this(baseName, "csv", ';');
//	} // INITIAL)
	public static OutputFile csvWithSemicolon(String baseName) { // SOLUTION(
		return new OutputFile(baseName, "csv", ';');
	} // SOLUTION)
	
	
//	public OutputFile(String baseName, String extension) { // INITIAL( 
//		this(baseName, extension, ';');
//	} // INITIAL)
	public static OutputFile withSemicolon(String baseName, String extension) { // SOLUTION(
		return new OutputFile(baseName, extension, ';');
	} 
	// SOLUTION)
	
	
//	public OutputFile(String baseName, char separator) { // INITIAL(
//		this(baseName, "csv", separator);
//	} // INITIAL)
	public static OutputFile aCsv(String baseName, char separator) { // SOLUTION(
		return new OutputFile(baseName, "csv", separator);
	} // SOLUTION)
	
	
	
	public String getFileName() {
		return baseName + "." + extension;
	}
	
	public char getSeparator() {
		return separator;
	}
	
}
