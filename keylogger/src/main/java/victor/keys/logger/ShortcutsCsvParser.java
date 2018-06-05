package victor.keys.logger;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;

import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lombok.SneakyThrows;
import victor.keys.logger.KeyCombination.FirstKey;
import victor.keys.logger.utils.DetermineActiveIDE;
import victor.keys.logger.utils.DetermineActiveIDE.IDE;

public class ShortcutsCsvParser {
	@SneakyThrows
	public Map<IDE, List<KeyCombination>> parse(File file) {
		Map<IDE, List<KeyCombination>> map = new HashMap<>();
		map.put(IDE.ECLIPSE, parseForIDE(file, IDE.ECLIPSE));
		map.put(IDE.IDEA, parseForIDE(file, IDE.IDEA));
		return map;
	}

	private List<KeyCombination> parseForIDE(File file, IDE ide) throws FileNotFoundException, IOException {
		List<KeyCombination> list = new ArrayList<>();
		Reader in = new FileReader(file);
		for (CSVRecord record : CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in)) {
			String title = record.get("Title");
			String eclipseWin = record.get("Eclipse Win");
			String eclipseMac = record.get("Eclipse Mac");
			String ideaWin = record.get("Idea Win");
			String ideaMac = record.get("Idea Mac");
			
			Shortcut shortcut = new Shortcut(title, eclipseWin, eclipseMac, ideaWin, ideaMac);
			list.addAll(computeKeyCombinations(shortcut, ide));
		}
		return list;
	}
	
	private List<KeyCombination> computeKeyCombinations(Shortcut shortcut, IDE ide) {
		String shortcutStr = getShortcutForCurrentIDE(shortcut, ide);
		return multiply(shortcutStr.toUpperCase()).stream()
			.map(str -> parseCombination(str,shortcut))
			.collect(toList());
	}
	
	private String getShortcutForCurrentIDE(Shortcut shortcut, IDE ide) {
		switch (ide) {
		case ECLIPSE: return shortcut.getEclipseWin();
		case IDEA: return shortcut.getIdeaWin();
		default: 
			throw new IllegalStateException("Could not determine IDE under use. Please restart the tool after you've started your IDE");
		}
	}

	private List<String> multiply(String s) {
		if (StringUtils.isBlank(s)) {
			return emptyList();
		}
		List<String> list = singletonList(s);
		if (s.contains("|")) {
			String s1 = s.substring(0, s.indexOf("|"));
			String s2 = s1.substring(0, s1.lastIndexOf("-") + 1) + s.substring(s.indexOf("|") + 1);
			list = asList(s1, s2);
		}
		if (s.contains("[")) {
			String s1 = s.replaceAll("\\[.*\\]", "").replace("--", "-");
			String s2 = s.replace("[","").replaceAll("]", "");
			list = asList(s1, s2);
		}
//		System.out.println("Multiplied " + s + " to " + list);
		return list; 
	}
	
	private KeyCombination parseCombination(String str, Shortcut shortcut) {
		FirstKey firstKey;
		Character secondKey;
		if (str.contains(",")) {
			firstKey = parseFirstKey(str.split(",")[0]);
			secondKey = str.toLowerCase().charAt(str.indexOf(",")+1);
		} else {
			firstKey = parseFirstKey(str);
			secondKey = null;
		}
		KeyCombination comb = new KeyCombination(firstKey, secondKey, shortcut);
//		System.out.println(str + " -> " + comb);
		return comb;
	}

	@SneakyThrows
	private FirstKey parseFirstKey(String firstKeyStr) {
		String key = firstKeyStr.substring(firstKeyStr.lastIndexOf('-') + 1);
		key = key.replace("ENTER", "RETURN").replace("BACKSPACE", "BACK");
		Field constant = GlobalKeyEvent.class.getDeclaredField("VK_"+key);
		
		return new FirstKey(
				firstKeyStr.contains("CTRL"),
				firstKeyStr.contains("ALT"),
				firstKeyStr.contains("SHIFT"),
				constant.getInt(null));
	}
	
}
