package victor.perf.interning;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonGenerator {

	public static final int N_RECORDS = 1000000;
	
	public static void main(String[] args) throws IOException {
		
		try (PrintWriter writer = new PrintWriter("big.json")) {
			writer.write("[\n");
			for (int i = 0; i<N_RECORDS; i++) {
				writer.write("{\"field1\": \"value1\", \"field2\": \"value2\"}\n");
				if (i < N_RECORDS-1) {
					writer.write(",");
				}
			}
			writer.write("]");
		}
		System.out.println("Done");
	}
}
