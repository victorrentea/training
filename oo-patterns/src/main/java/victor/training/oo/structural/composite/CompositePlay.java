package victor.training.oo.structural.composite;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;

import victor.training.oo.gui.MyFrame;

public class CompositePlay {
	public static void main(String[] args) {
		MyFrame myFrame = new MyFrame();

		myFrame.panel1.setBackground(Color.YELLOW);
		myFrame.panel2.setBackground(Color.RED);
		
		JComponent components[] = new JComponent[] {myFrame.textArea, myFrame.button1, myFrame.textField};
		for (JComponent component : components) {
			component.setFont(new Font("Times New Roman", 1, 20));
			//.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}

	}
}
