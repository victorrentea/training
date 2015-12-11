package victor.training.java7;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

// never do this at home: never create threads by hand in real applications
public class FileChanger extends Thread {
	private final Path baseDir;
	
	public FileChanger(Path baseDir) {
		this.baseDir = baseDir;
	}

	@Override
	public void run() {
		Random random = new Random();
		try {
			while (true) {
				sleep(1000);
				if (random.nextInt() % 2 == 0) {
					String fileName = "file" + random.nextInt(1000000); 
					System.out.println("  Creating file: " + fileName);
					try {
						Files.createFile(baseDir.resolve(fileName));
					} catch (FileAlreadyExistsException e) {
						// swallow: don't do this
					}
				} else {
					if (baseDir.toFile().list().length == 0) {
						System.out.println("  Wanted to delete a file but none found :(");
						continue;
					}
					Path aFile = Files.list(baseDir).findFirst().get();
					System.out.println("  Deleting file: " + aFile.toFile().getName());
					Files.delete(aFile);
				}
			}
		} catch (InterruptedException | IOException e) {
			throw new RuntimeException(e);
		}

	}

}
