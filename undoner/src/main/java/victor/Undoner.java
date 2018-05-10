package victor;

import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Undoner {
	static File sourceDir;
	static File destDir;
	
	private static boolean lineContains(String line, String regex) {
		return Pattern.compile(regex).matcher(line).find();
	}
	
	@SuppressWarnings("unchecked")
	static boolean undoFile(File sourceFile, File destination, boolean dryRun, boolean curatEntityAnnot) {
		try {
			List<String> inLines;
			try (FileReader reader = new FileReader(sourceFile)) {
				inLines = IOUtils.readLines(reader);
			}
			if (inLines.size() >= 1 && inLines.get(0).contains("SOLUTION")) {
				if (!dryRun) {
					sourceFile.delete();
				} 
				return true;
			}
			List<String> outLines = stripSolutionLines(inLines);
			
			if (curatEntityAnnot) {
				outLines = eliminaAdnotariDinEntitati(sourceFile, outLines);
			}
			
			if (!dryRun) {
				if (destination.isFile()) {
					destination.delete();
				}
				destination.getParentFile().mkdirs();		
				destination.createNewFile();
				FileWriter writer = new FileWriter(destination);
				IOUtils.writeLines(outLines, "\n", writer);
				writer.close();
			}
			return !outLines.equals(inLines);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static List<String> eliminaAdnotariDinEntitati(File sourceFile, List<String> outLines) {
		boolean isAnEntity = sourceFile.getAbsolutePath().contains("entity");
		if (isAnEntity) {
			System.out.println("Will remove entity annotations from file: " + sourceFile.getName());
			return outLines.stream()
					.map(Undoner::removeAnnotations)
					.collect(toList());
		}
		return outLines;
	}
	
	private static String removeAnnotations(String line) {
		if (line.contains("@")) {
			return "";
		} return line;
	}

	private static List<String> stripSolutionLines(List<String> inLines) {
		List<String> outLines = new ArrayList<>();
		boolean skippingSolution = false;
		boolean uncommentingInitial = false;
		for (String line : inLines) {
			List<String> startSolutionTokens = Arrays.asList("//\\s*SOLUTION\\s*\\(", "<!--\\s*SOLUTION\\s*\\(");
			List<String> endSolutionTokens = Arrays.asList("//\\s*SOLUTION\\s*\\)", "<!--\\s*SOLUTION\\s*\\)");
			final String origLine = line;
			if (startSolutionTokens.stream().anyMatch(token -> lineContains(origLine, token))) {
				skippingSolution = true;
			} else if (endSolutionTokens.stream().anyMatch(token -> lineContains(origLine, token))) {
				skippingSolution = false;
				continue;
			} else if (lineContains(line, "//\\s*SOLUTION")) {
				continue;
			}
			if (lineContains(line,"//\\s*INITIAL\\s*\\(")) {
				line = line.replaceAll("//\\s*INITIAL\\s*\\(", "");
				uncommentingInitial = true;
			} else if (lineContains(line, "//\\s*INITIAL\\s*\\)")) {
				line = line.replaceAll("//\\s*INITIAL\\s*\\)", "").replaceFirst("//", "");
				uncommentingInitial = false;
			} else if (lineContains(line, "//\\s*INITIAL")) {
				line = line.replaceAll("//\\s*INITIAL","").replaceFirst("//", "");
			}
			if (uncommentingInitial) {
				line = line.replaceFirst("//", "");
			}
			if (skippingSolution) {
				continue;
			}
			outLines.add(line);
		}
		return outLines;
	}
	
	public static void main(String[] args) throws IOException {
		List<String> projects = searchUndoableProjects();
		
		
		JFrame frame = new JFrame();
		frame.setSize(300, 150);
		frame.setLayout(new BorderLayout());
//		
//		JCheckBox cleanFolder = new JCheckBox("Clean destination folder", isVictorMachine());
//		if (isVictorMachine()) {
//			cleanFolder.setEnabled(false);
//		}
//		frame.add(cleanFolder, BorderLayout.NORTH);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		JComboBox<String> projectsCombo = new JComboBox<>(projects.stream().sorted().collect(toList()).toArray(new String[0]));
		panel2.add(projectsCombo, BorderLayout.CENTER);
		panel2.add(new JLabel("Revert solution for project:"), BorderLayout.NORTH);
		final JCheckBox clearEntityAnnotations = new JCheckBox("Clear Entity annotations");
		panel2.add(clearEntityAnnotations, BorderLayout.SOUTH);
		frame.add(panel2, BorderLayout.CENTER);
		JButton button = new JButton("UNDO ("+(isVictorMachine()?"Victor":"Trainee") +")");
		frame.add(button, BorderLayout.SOUTH);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				if (cleanFolder.isSelected()) {
//					cleanDestFolder();
//				}
				File inputSrcFolder = new File(getFolderContainingTheProjects(), projectsCombo.getSelectedItem() + "");// + "/src/main");
				boolean undone;
				if (isVictorMachine()) {
					try {
						FileUtils.copyDirectoryToDirectory(inputSrcFolder, new File("../../training-undone/"));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					File rootToUndone = new File("../../training-undone/"+projectsCombo.getSelectedItem());
					undone = undoFolders(rootToUndone, rootToUndone, false, clearEntityAnnotations.isSelected());
				} else {
					undone = undoFolders(inputSrcFolder, inputSrcFolder, false, clearEntityAnnotations.isSelected());
				}
				JOptionPane.showMessageDialog(null, undone ? "Undone. Good luck!\n\nRemember: To see the solved version, HARD reset your worspace." : "Nothing to undo (already undone?)");
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setTitle("Revert solution for project...");
		frame.show();
	}

	private static List<String> searchUndoableProjects() {
		File rootTraining = getFolderContainingTheProjects();
		List<String> projectNames= new ArrayList<>();
		for (File subFile : rootTraining.listFiles()) {
			if (subFile.isDirectory() && undoFolders(subFile, subFile, true, true)) {
				System.out.println("Found UNDO-able project folder: " + subFile.getAbsolutePath());
				projectNames.add(subFile.getName());
			}
		}
		projectNames.remove("undoner");
		return projectNames;
	}

	private static File getFolderContainingTheProjects() {
//		return new File("C:\\workspace");
		return new File("..");
	}
	
	public static boolean undoFolders(File baseSourceFolder, File baseDestFolder, boolean dryTest, boolean curatEntityAnnot) {
		if (!baseSourceFolder.isDirectory()) {
			throw new IllegalArgumentException("Must be a folder: " + baseSourceFolder);
		}
		
		baseDestFolder.mkdirs();
		if (!baseDestFolder.isDirectory()) {
			throw new IllegalArgumentException("Must be a folder: " + baseDestFolder);
		}
		boolean performedChanges = false;
		for (File file : (Collection<File>) FileUtils.listFiles(baseSourceFolder, new String[] {"java", "html", "jsp", "php"}, true)) {
			File destFile = new File(baseDestFolder, file.getAbsolutePath().substring(baseSourceFolder.getAbsolutePath().length()));
			if (undoFile(file, destFile, dryTest, curatEntityAnnot)) {
				performedChanges = true;
				if (dryTest) {
					break;
				}
				System.out.println("Undone "+destFile.getAbsolutePath());
			}
		}
		return performedChanges;
	}
	
	public static boolean isVictorMachine() {
		return new File("c:/workspace/training").isDirectory();
	}
}
