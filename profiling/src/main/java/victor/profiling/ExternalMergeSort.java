package victor.profiling;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ExternalMergeSort {
	private int counter = 0;
	private static final File TEMP_FOLDER = new File("tmp");
	static {
		TEMP_FOLDER.delete();
		TEMP_FOLDER.mkdirs();
	}
	
	private final byte[] buffer;
	private final File source;
	private final File destination;
	private List<File> tempFiles = new ArrayList<>();
	
	public ExternalMergeSort(File source, File destination, int size) {
		this.source = source;		
		this.destination = destination;
		this.buffer = new byte[size];
	}
	
	public void sort() throws IOException {		
		System.out.println("Splitting the input file in "+(source.length() / buffer.length)+ " files in-memory sorted");
		try (InputStream sourceStream = new FileInputStream(source)) {
			while (sourceStream.read(buffer) > 0) {
				Arrays.sort(buffer);
				File tempFile = nextTempFile();
				tempFiles.add(tempFile);
				try (OutputStream tempStream = new FileOutputStream(tempFile)) {
					tempStream.write(buffer);
				}
				System.gc();
			}
		}
		
		List<File> nextTempFiles;
		
		int passCount = 1;
		while (tempFiles.size() > 1) {
			System.out.println("Pass #"+(passCount++)+": Merging "+tempFiles.size() + " files");
			nextTempFiles = new ArrayList<>();
			for (int i = 0; i + 1 < tempFiles.size(); i += 2) {
				File newTempFile = nextTempFile();
				File file1 = tempFiles.get(i);
				File file2 = tempFiles.get(i+1);
//				System.out.println("Merging file #"+file1.getName()+ " with #"+file2.getName() + " into "+newTempFile.getName());
				nextTempFiles.add(newTempFile);
				try (
				InputStream stream1 = new FileInputStream(file1);
				InputStream stream2 = new FileInputStream(file2);
				OutputStream newTempStream = new FileOutputStream(newTempFile)
						) {
					
					
					int len1 = stream1.read(buffer, 0, buffer.length/3);
					int len2 = stream2.read(buffer, buffer.length/3, buffer.length/3);
					int index1 = 0, index2 = 0, outIndex = 0;
					
					while (len1 > 0 && len2 > 0) {
						if (outIndex == buffer.length - 2 * buffer.length/3) {
							newTempStream.write(buffer, 2 * buffer.length/3, buffer.length - 2 * buffer.length/3);
							outIndex = 0;
						}
						if (buffer[index1] < buffer[buffer.length/3 + index2]) {
							buffer[2 * buffer.length/3 + outIndex ++ ] = buffer[index1 ++];
						} else {
							buffer[2 * buffer.length/3 + outIndex ++ ] = buffer[buffer.length/3 + index2 ++];
						}
						if (index1 >= len1) {
							len1 = stream1.read(buffer, 0, buffer.length/3);
							index1 = 0;
						}
						if (index2 >= len2) {
							len2 = stream2.read(buffer, buffer.length/3, buffer.length/3);
							index2 = 0;
						}
					}
					newTempStream.write(buffer, 2 * buffer.length/3, outIndex);
					if (len1 == 0) {
						newTempStream.write(buffer, buffer.length/3, index2);
					}
					if (len2 == 0) {
						newTempStream.write(buffer, 0, index1);
					}					
				}
			}
			if (tempFiles.size() % 2 == 1) {
				nextTempFiles.add(tempFiles.get(tempFiles.size()-1));
			}
			tempFiles = nextTempFiles;
			System.gc();
		}
		destination.delete();
		System.out.println("Done. Copying result file to " + destination.getAbsolutePath());
		Files.copy(tempFiles.get(0).toPath(), destination.toPath());
	}

	
	
	private File nextTempFile() {
		return new File(TEMP_FOLDER, "temp"+(counter ++ )+".tmp");
	}
	
	public static long parseSize(String value) {
		value = value.replaceAll("\\s+", "").toUpperCase();
		int factor;
		if (value.endsWith("MB")) {
			factor = 1024 * 1024;
			value = value.substring(0, value.length() - 2);
		} else if (value.endsWith("KB")) {
			factor = 1024;
			value = value.substring(0, value.length() - 2);
		} else {
			factor = 1;
		}
		return Integer.parseInt(value) * factor;
	}
	
	public static void main(String[] args) throws IOException {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter file size: ");
			long fileSize = parseSize(scanner.nextLine());
			System.out.print("Enter memory size: ");
			int bufferSize = (int) parseSize(scanner.nextLine());
			System.out.println("\nGenerating input file of " + fileSize + " bytes ...");
			Random r = new Random();
			try (OutputStream os = new BufferedOutputStream(new FileOutputStream("in.dat"))) {
				for (long i = 0; i < fileSize; i++) {
					os.write(r.nextInt(128));
				}
			}
			System.out.println("Done. ENTER to start sorting with a buffer of "+ bufferSize+ " bytes");
			scanner.nextLine();
	//		new ExternalMergeSort(new File("src/main/java/victor/profiling/ExternalMergeSort.java"), new File("out.txt"), 100).sort();
			new ExternalMergeSort(new File("in.dat"), new File("out.dat"), bufferSize).sort();
		}
	}
}
