package victor.training.oo.creational.factorymethod;

import java.io.File;

public class FileGenerator {
	public File createAnafFile() {
//		OutputFile outputFile = new OutputFile("anaf", '|'); // INITIAL
		OutputFile outputFile = OutputFile.aCsv("anaf", '|'); // SOLUTION
		
		File file = new File(outputFile.getFileName());
		System.out.println("Writing ANAF to file " + file.getAbsolutePath());
		// write stuff to file
		return file;
	}
	
	public File createCreditBureauFile(int month) {
//		OutputFile outputFile = new OutputFile("credit-"+month); // INITIAL
		OutputFile outputFile = OutputFile.csvWithSemicolon("credit-"+month); // SOLUTION
		
		File file = new File(outputFile.getFileName());
		System.out.println("Writing Credit to file " + file.getAbsolutePath());
		// write stuff to file
		return file;
	}
	
	public static void main(String[] args) {
		FileGenerator generator = new FileGenerator();
		generator.createAnafFile();
		generator.createCreditBureauFile(12);
		
//		System.out.println(new OutputFile("bank", "txt").getFileName()); // INITIAL
//		System.out.println(new OutputFile("countries").getFileName()); // INITIAL
		System.out.println(OutputFile.withSemicolon("bank", "txt").getFileName()); // SOLUTION
		System.out.println(OutputFile.csvWithSemicolon("countries").getFileName()); // SOLUTION
	}
}
