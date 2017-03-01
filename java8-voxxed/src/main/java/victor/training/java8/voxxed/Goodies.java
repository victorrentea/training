package victor.training.java8.voxxed;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.jooq.lambda.Unchecked;

public class Goodies {
	public static void main(String[] args) {
		listAbsoluteFilesInDirectory(new File("."));
	}

	public static void listAbsoluteFilesInDirectory(File dir) {
		Arrays.stream(dir.listFiles()).forEach(file -> {
			try {
				System.out.println(file.getCanonicalPath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			// Ouch, my fingers hurt! All this typing!
		});

		// instead ... 
		System.out.println("==========");

		Arrays.stream(dir.listFiles()).forEach(Unchecked.consumer(file -> {
			System.out.println(file.getCanonicalPath());
		}));
	}
}
