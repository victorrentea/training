package victor.keys.logger.utils;
//package victor.swing.utils;
//
//import java.awt.Font;
//import java.awt.GraphicsEnvironment;
//
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//public class ListJavaFonts {
//
//	public static void main(String[] args) {
//		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//
//		JFrame f = new JFrame();
//		JPanel mainPanel = new JPanel();
//		f.getContentPane().add(mainPanel);
//		for (int i = 0; i < fonts.length; i++) {
//			System.out.println(fonts[i]);
//			JLabel label = new JLabel(fonts[i]+":" + ((char) 241));
//			label.setFont(new Font(fonts[i], 0, 10));
//			mainPanel.add(label);
//		}
//		f.setSize(300, 200);
//		f.setVisible(true);
//	}
//
//}