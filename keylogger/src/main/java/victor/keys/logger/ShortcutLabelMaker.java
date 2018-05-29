package victor.keys.logger;

import static java.util.Collections.emptyList;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.StringUtils;

import victor.keys.logger.utils.RoundedBorder;

public class ShortcutLabelMaker {

	public static JComponent makeMacShortcut(String shortcutText) {
		shortcutText= shortcutText.replace("Alt", "Opt");
		return makeShortcut(shortcutText);
	}
	public static JComponent makeShortcut(String shortcutText) {
		JPanel panel = new JPanel();
		panel.setBackground(null);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		List<JLabel> labels = breakTextIntoLabels(shortcutText);
		labels.forEach(panel::add);
		return panel;
	}



	private static List<JLabel> breakTextIntoLabels(String s) {
		if (StringUtils.isBlank(s)) {
			return emptyList();
		}
		List<JLabel> list = new ArrayList<>();
		if (s.toUpperCase().contains("CTRL")) {
			list.add(aButton("Ctrl"));
		}
		if (s.toUpperCase().contains("ALT")) {
			list.add(aButton("Alt"));
		}
		if (s.toUpperCase().contains("SHIFT")) {
			list.add(aButton("Shift"));
		}
		if (s.toUpperCase().contains("OPT")) {
			list.add(aButton("⌥"));
		}
		if (s.toUpperCase().contains("CMD")) {
			list.add(aButton("⌘"));
		}
		String lastText = s.substring(s.lastIndexOf('-')+1);
		
		if (lastText.contains("|")) {
			list.add(aButton(lastText.split("\\|")[0]));
			list.add(aLabel("/", false));
			list.add(aButton(lastText.split("\\|")[1]));
		} if (lastText.contains(",")) {
			list.add(aButton(lastText.split(",")[0]));
			list.add(aLabel(",", false));
			list.add(aButton(lastText.split(",")[1]));
		} else {
			list.add(aButton(lastText));
		}
		
		for (int i=list.size()-1; i>=1;i--) {
			list.add(i,new JLabel(" "));
		}
		return list;
	}





	private static JLabel aButton(String text) {
		if (text.toUpperCase().equals("UP")) {
			text = "↑";
		}
		if (text.toUpperCase().equals("DOWN")) {
			text = "↓";
		}
		if (text.toUpperCase().equals("SHIFT")) {
			text = "⇧";
		}
		if (text.toUpperCase().equals("CMD")) {
			text = "⇧";
		}
//		if (text.length() == 1) {
			text = " " + text + " ";
//		}
		JLabel label = aLabel(text, true);
//		label.setBackground(Color.yellow);
//		label.setOpaque(true);
		return label;
	}



	private static JLabel aLabel(String text, boolean border) {
		JLabel label = new JLabel(text, SwingConstants.CENTER);
//		label.setFont(new Font("Verdana", 0, 12));
		
//		label.setFont(new Font("Cambria Math", 0, 16));
		
		label.setFont(new Font(label.getFont().getName(), Font.BOLD, 13));
		label.setForeground(GlobalSettings.TOASTR_FONT_COLOR);
		if (border) {
			label.setBorder(new RoundedBorder(GlobalSettings.TOASTR_FONT_COLOR, 5));
		}
		return label;
	}
}
