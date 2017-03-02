package victor.training.java8.voxxed;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.jooq.lambda.Unchecked;

public class UncheckedAndLambdas {
	public static void main(String[] args) {
		File dir = new File(".");
		Arrays.stream(dir.listFiles()).forEach(file -> {
			try {
				System.out.println(file.getCanonicalPath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		
			// Ouch, my fingers hurt! All this typing!
		});
		
		
		// TODO use Unchecked.consumer from JOOL library
		Arrays.stream(dir.listFiles()).forEach(Unchecked.consumer(file -> {
			System.out.println(file.getCanonicalPath());
		}));
	}
}
