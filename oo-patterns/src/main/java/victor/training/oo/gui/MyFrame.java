package victor.training.oo.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyFrame extends JFrame {

	public JButton button1 = new JButton("Press Me");
	public JPanel panel1 = new JPanel();
	public JPanel panel2 = new JPanel();
	public JTextArea textArea = new JTextArea(3, 8);
	public JTextField textField = new JTextField(8);

	public MyFrame() {

		getContentPane().setLayout(new GridLayout(1, 2, 20, 20));
		getContentPane().add(button1);
		getContentPane().add(panel1);
		panel1.add(panel2);
		panel2.add(textArea);
		panel1.add(textField);

		setTitle("OO example: Observer, Composite, TemplateMethod");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
	}

}
