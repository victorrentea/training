package victor;

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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Undoner {
	static File sourceDir;
	static File destDir;
	
	private static boolean lineContains(String line, String regex) {
		return Pattern.compile(regex).matcher(line).find();
	}
	
	@SuppressWarnings("unchecked")
	static boolean undoFile(File sourceFile, File destination, boolean dryRun) {
		try {
			List<String> inLines;
			try (FileReader reader = new FileReader(sourceFile)) {
				inLines = IOUtils.readLines(reader);
			}
			if (inLines.get(0).contains("SOLUTION")) {
				sourceFile.delete();
				return true;
			}
			List<String> outLines = new ArrayList<>();
			boolean skippingSolution = false;
			boolean uncommentingInitial = false;
			for (String line : inLines) {
				List<String> startSolutionTokens = Arrays.asList("//\\w*SOLUTION(\\w*", "<!--\\w*SOLUTION\\w*(");
				List<String> endSolutionTokens = Arrays.asList("//\\w*SOLUTION\\w*)", "<!--\\w*SOLUTION\\w*)");
				final String origLine = line;
				if (startSolutionTokens.stream().anyMatch(token -> lineContains(origLine, token))) {
					skippingSolution = true;
				} else if (endSolutionTokens.stream().anyMatch(token -> lineContains(origLine, token))) {
					skippingSolution = false;
					continue;
				} else if (lineContains(line, "//\\w*SOLUTION")) {
					continue;
				}
				if (lineContains(line,"//\\w*INITIAL\\w*(")) {
					line = line.replaceAll("//\\w*INITIAL\\w*(", "");
					uncommentingInitial = true;
				} else if (line.contains("//\\w*INITIAL\\w*)")) {
					line = line.replaceAll("//\\w*INITIAL\\w*)", "").replaceFirst("//", "");
					uncommentingInitial = false;
				} else if (line.contains("//\\w*INITIAL")) {
					line = line.replaceAll("//\\w*INITIAL","").replaceFirst("//", "");
				}
				if (uncommentingInitial) {
					line = line.replaceFirst("//", "");
				}
				if (skippingSolution) {
					continue;
				}
				outLines.add(line);
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
	
	public static void main(String[] args) throws IOException {
		List<String> projects = searchUndoableProjects();
		
		
		JFrame frame = new JFrame();
		frame.setSize(300, 120);
		frame.setLayout(new BorderLayout());
//		
//		JCheckBox cleanFolder = new JCheckBox("Clean destination folder", isVictorMachine());
//		if (isVictorMachine()) {
//			cleanFolder.setEnabled(false);
//		}
//		frame.add(cleanFolder, BorderLayout.NORTH);
		final JComboBox<String> projectsCombo = new JComboBox<>(projects.toArray(new String[0]));
		frame.add(projectsCombo);
		JButton button = new JButton("UNDO ("+(isVictorMachine()?"Victor":"Trainee") +")");
		frame.add(button, BorderLayout.SOUTH);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				if (cleanFolder.isSelected()) {
//					cleanDestFolder();
//				}
				File inputSrcFolder = new File("../" + projectsCombo.getSelectedItem() + "/src/main");
				boolean undone;
				if (isVictorMachine()) {
					undone = undoFolders(inputSrcFolder, new File("../../training-undone/"+projectsCombo.getSelectedItem()+"/src/main"), false);
				} else {
					undone = undoFolders(inputSrcFolder, inputSrcFolder, false);
				}
				JOptionPane.showMessageDialog(null, undone ? "Undone " : "Nothing to undo (already undone?)");
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setTitle("Revert solution for project...");
		frame.show();
	}

	private static List<String> searchUndoableProjects() {
		File rootTraining = new File("..");
		List<String> projectNames= new ArrayList<>();
		for (File subFile : rootTraining.listFiles()) {
			if (subFile.isDirectory() && undoFolders(subFile, subFile, true)) {
				System.out.println("Found UNDO-able project folder: " + subFile.getAbsolutePath());
				projectNames.add(subFile.getName());
			}
		}
		projectNames.remove("undoner");
		return projectNames;
	}
	
	public static boolean undoFolders(File baseSourceFolder, File baseDestFolder, boolean dryTest) {
		if (!baseSourceFolder.isDirectory()) {
			throw new IllegalArgumentException("Must be a folder: " + baseSourceFolder);
		}
		
		baseDestFolder.mkdirs();
		if (!baseDestFolder.isDirectory()) {
			throw new IllegalArgumentException("Must be a folder: " + baseDestFolder);
		}
		boolean performedChanges = false;
		for (File file : (Collection<File>) FileUtils.listFiles(baseSourceFolder, new String[] {"java"}, true)) {
			File destFile = new File(baseDestFolder, file.getAbsolutePath().substring(baseSourceFolder.getAbsolutePath().length()));
			if (undoFile(file, destFile, dryTest)) {
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
		return new File("d:/workspace/training").isDirectory();
	}
}
