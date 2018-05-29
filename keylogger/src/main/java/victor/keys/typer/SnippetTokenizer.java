package victor.keys.typer;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class SnippetTokenizer {
	
	public static List<String> tokenize(File snippetFile) throws IOException {
		String snippetText = FileUtils.readFileToString(snippetFile, "UTF-8");
		snippetText = snippetText.replace("\r", "");
		List<String> chunks = splitInChunks(snippetText, 4);
		return chunks.stream().map(SnippetTokenizer::toAhkKeys).collect(toList());
	}
	
	
	private static List<String> splitInChunks(String text, int chunkSize) {
		List<String> list = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\W");
//		Pattern pattern = Pattern.compile("[^a-z \n`]");
		Matcher matcher = pattern.matcher(text);
		int lastIndex = 0;
		while (matcher.find()) {
			String alphaNum = text.substring(lastIndex, matcher.end());
			int wordSplitSize = (int) Math.round(3 + 4d * lastIndex/text.length());
			list.addAll(splitMaxChars(alphaNum, wordSplitSize));
			lastIndex = matcher.end();
		}
		list.add(text.substring(lastIndex));
		
		mergeTooSmallChunks(list, 4);
//		addMistakes(list);
		interweaveNOOPs(list);
		return list;
	}
	
	private static void addMistakes(List<String> chunks) {
		Random r = new Random();
		List<Integer> startingWithLetterIds = new ArrayList<>();
		for (int i=0;i<chunks.size();i++) {
			if (chunks.get(i).length() > 0 && Character.isLetter(chunks.get(i).charAt(0))) {
				startingWithLetterIds.add(i);
			}
		}
		List<Integer> selectedIndexes = new ArrayList<>();
		for (int c = 0; c<chunks.size()/7;c++) {
			selectedIndexes.add(startingWithLetterIds.remove(r.nextInt(startingWithLetterIds.size())));
		}
		
		for (int indexToBreak : selectedIndexes) {
			String oldValue = chunks.get(indexToBreak);
			String newValue = ProximChars.getRandomProximal(oldValue.charAt(0))+"£" + oldValue;
			chunks.set(indexToBreak, newValue);
		}
		
		
		
//		Random r = new Random();
//		for (int i = 0; i<chunks.size();i++) {
//			String chunk = chunks.get(i);
//			if (r.nextInt(8)==0) {
//				char wrongChar = (char) (r.nextInt('z'-'a') + 'a');
//				chunk += wrongChar + "{Backspace}";
//				chunks.set(i, chunk);
//			}
//			
//		}
	}
	
	private static String toAhkKeys(String s) {
		return s
				.replace("+", "+=")
				.replace("\"", "+'")
				.replace("{", "~")
				.replace("}", "{}}")
				.replace("~", "{{}")
				.replace("!", "{!}")
				.replace("`", "{Down}")
				.replace("$", "^{Space}")
				.replace("�", "{Backspace}")
				.replace("#", "{Escape}")
				.replace("~", "{End}")
				;
	}

	
	private static void interweaveNOOPs(List<String> list) {
		for (int i = list.size()-1; i > 0; i -= 2) {
			list.add(i, "NOOP");
		}
		for (int i = list.size()-1; i > 0; i --) {
			if (list.get(i).contains("$")) {
				list.add(i+1, "NOOP");
			}
		}
		for (int i = list.size()-1; i > 0; i --) {
			if (list.get(i - 1).equals("NOOP") &&
				list.get(i).equals("NOOP")) {
				list.remove(i);
			}
		}
//		 for (int i = list.size()-1; i > 0; i --) {
//			 String label = "SHIFT_";
//			 label += needsShift(list.get(i)) ? "ON" : "OFF";
//			 list.add(i, label);
//		 }
	}

	private static boolean needsShift(String string) {
		boolean r = !Pattern.matches("[a-z\\s\\,;\\-=\\.\\[\\]]+", string);
		System.out.println(string + " needs shift: " + r);
		return r; // 
	}
	



	private static void mergeTooSmallChunks(List<String> list, int size) {
		 for (int i = 0; i < list.size() - 1; i++) {
			 if (list.get(i).length() + list.get(i+1).length() <= size) {
				 list.set(i, list.get(i) + list.get(i+1));
				 list.remove(i + 1);
			 }
		 }
	}

	private static List<String> splitMaxChars(String text, int size) {
		List<String> list = new ArrayList<>();
		int i=0;
		while (i < text.length()) {
			String newBlock = text.substring(i, Math.min(i+size, text.length()));
			if (newBlock.indexOf("\n") >= 1) {
				newBlock = newBlock.substring(0, newBlock.indexOf("\n")+1);
			}
			list.add(newBlock);
			i += newBlock.length();
		}
		return list;
	}
	
	public static void main(String[] args) throws IOException {
		List<String> tokens = tokenize(new File("C:/workspace/training/talk/clean-code-java8-devoxxfr/src/main/snippet/E__TypeSpecific_Functionality_3.txt"));
		for (String token: tokens) {
			System.out.println("Token: " + token);
		}
		System.out.println(tokens.size() + " tokens");
		
		System.out.println(">"  + needsShift("a "));
	}
}