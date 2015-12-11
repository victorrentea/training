package victor.training.java7;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public class FileWatchService {
	public static void main(String[] args) throws IOException, InterruptedException {
		Path tmpDir = Paths.get("tmp");
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Path monitoredFolder = tmpDir;
		monitoredFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
		
		tmpDir.toFile().mkdirs();
		new FileChanger(tmpDir).start();
		while (true) {
			System.out.println("Waiting for event");
			WatchKey watchKey = watchService.take();
			for (WatchEvent<?> event : watchKey.pollEvents()) {
				System.out.println("Detected event " + event.kind().name() + " on file " + event.context().toString());
			}
			watchKey.reset();
		}
	}
}
