package victor.training.concurrency;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Undoner {
	static File sourceDir;
	static File destDir;
	
	static void process(File source, File destination) throws IOException {
		@SuppressWarnings("unchecked")
		List<String> inLines = IOUtils.readLines(new FileReader(source));
		List<String> outLines = new ArrayList<>();
		boolean skippingSolution = false;
		boolean uncommentingInitial = false;
		for (String line : inLines) {
			if (line.contains("// SOLUTION(")) {
				skippingSolution = true;
			} else if (line.contains("// SOLUTION)")) {
				skippingSolution = false;
				continue;
			} else if (line.contains("// SOLUTION")) {
				continue;
			}
			if (line.contains("// INITIAL(")) {
				line = line.replace("// INITIAL(", "");
				uncommentingInitial = true;
			} else if (line.contains("// INITIAL)")) {
				line = line.replace("// INITIAL)", "").replaceFirst("//", "");
				uncommentingInitial = false;
			} else if (line.contains("// INITIAL")) {
				line = line.replaceAll("// INITIAL","").replaceFirst("//", "");
			}
			if (uncommentingInitial) {
				line = line.replaceFirst("//", "");
			}
			if (skippingSolution) {
				continue;
			}
			outLines.add(line);
		}
		
		if (destination.isFile()) {
			destination.delete();
		}
		destination.getParentFile().mkdirs();		
		destination.createNewFile();
		
		FileWriter writer = new FileWriter(destination);
		IOUtils.writeLines(outLines, "\n", writer);
		writer.close();
		System.out.println(destination);
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		
		File baseSourceDir = new File("src/main/java");
		File baseDestDir = new File("d:/workspace-training/ccc-test/src");
		System.out.println(baseSourceDir.isDirectory());
		for (File file : (Collection<File>) FileUtils.listFiles(baseSourceDir, new String[] {"java"}, true)) {
//			System.out.println("file ;" +file);
			process(file, new File(baseDestDir, file.getAbsolutePath().substring(baseSourceDir.getAbsolutePath().length())));
		}
		
//		process(new File("src/main/java/victor/training/concurrency/monitor/WaitNotifyPlay.java"), 
//				new File("d:/workspace-training/ccc-test/src/victor/training/concurrency/monitor/WaitNotifyPlay.java"));
//
//		process(new File("src/main/java/victor/training/concurrency/monitor/DeadlockPlay.java"), 
//				new File("d:/workspace-training/ccc-test/src/victor/training/concurrency/monitor/DeadlockPlay.java"));
}
}
