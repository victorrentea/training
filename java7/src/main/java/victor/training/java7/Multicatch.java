package victor.training.java7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Multicatch {

	public static void main(String[] args) {
		multicatch();
	}
	
	// INITIAL(
//		private static int multicatch() {
//			BufferedReader reader = null;
//			try {
//				reader = new BufferedReader(new FileReader("abc.txt"));
//				String firstLine = reader.readLine();
//				return Integer.parseInt(firstLine);
//			} catch (FileNotFoundException e) {
//				System.err.println("File not found");
//				throw new RuntimeException("Could not read number from file", e);
//			} catch (IOException e) {
//				throw new RuntimeException("Could not read number from file", e);
//			} catch (NumberFormatException e) {
//				throw new RuntimeException("Could not read number from file", e);
//			} finally {
//				try {
//					reader.close();
//				} catch (IOException e) {
//					throw new RuntimeException("What the heck?", e);
//				}
//			}
//			// TODO: what about AutoCloseable ? :)
//		}
		// INITIAL)
		
		// SOLUTION(
		private static int multicatch() { 
			try (BufferedReader reader = new BufferedReader(new FileReader("abc.txt"))){
				String firstLine = reader.readLine();
				return Integer.parseInt(firstLine);
			} catch (IOException|NumberFormatException e) {
				System.err.println("File not found");
				throw new RuntimeException("Could not read number from file", e);
			}
		} 
		// SOLUTION)
}
