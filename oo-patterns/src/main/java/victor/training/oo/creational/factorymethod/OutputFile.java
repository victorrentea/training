package victor.training.oo.creational.factorymethod;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputFile {
	private final String baseName;
	private final String extension;
	private final char separator;
	private final boolean appendTimestamp;
	
	public OutputFile(String baseName, String extension, char separator, boolean appendTimestamp) {
		this.baseName = baseName;
		this.extension = extension;
		this.separator = separator;
		this.appendTimestamp = appendTimestamp;
	}
	
//	public OutputFile(String baseName) { // INITIAL(
//		this(baseName, "csv", ';', true);
//	} // INITIAL)
	public static OutputFile timestampedCsvWithSemicolon(String baseName) { // SOLUTION(
		return new OutputFile(baseName, "csv", ';', true);
	} // SOLUTION)
	
	
//	public OutputFile(String baseName, String extension) { // INITIAL( 
//		this(baseName, extension, ';', true);
//	} // INITIAL)
	public static OutputFile timestampedWithSemicolon(String baseName, String extension) { // SOLUTION(
		return new OutputFile(baseName, extension, ';', true);
	} 
	// SOLUTION)
	
	
//	public OutputFile(String baseName, char separator) { // INITIAL(
//		this(baseName, "csv", separator, true);
//	} // INITIAL)
	public static OutputFile timestampedCsv(String baseName, char separator) { // SOLUTION(
		return new OutputFile(baseName, "csv", separator, true);
	} // SOLUTION)
	
	
	
//	public OutputFile(String baseName, boolean appendTimestamp) { // INITIAL(
//		this(baseName, "csv", ';', appendTimestamp);
//	} // INITIAL)
	public static OutputFile csvWithSemicolon(String baseName, boolean appendTimestamp) { // SOLUTION(
		return new OutputFile(baseName, "csv", ';', appendTimestamp);
	} // SOLUTION)
	
	public String getFileName() {
		String timestamp = "";
		if (appendTimestamp) {
			timestamp = "-" + new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		return baseName + timestamp + "." + extension;
	}
	
	public char getSeparator() {
		return separator;
	}
	
}
