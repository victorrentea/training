package victor.training.oo.behavioral.template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;

import victor.training.oo.behavioral.template.byhand.EmailCustomerProcessor;
import victor.training.oo.gui.MyFrame;

public class TemplateMethodPlay {
	public static void main(String[] args) {
		final MyFrame myFrame = new MyFrame();

		OutputStream myOutputStream = new MyJTextAreaOutputStream(myFrame.textArea);

		// Look Ma'! An Adapter Pattern! PrintWriter wraps the OutputStream we
		// provided.
		PrintStream printStream = new PrintStream(myOutputStream);

		// override the standard out
		System.setOut(printStream);

		System.out.println("Hello on System.out");

		// Look Ma'! An Observer Pattern
		myFrame.button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Clicked");
			}
		});

		// 2: a custom-made example
		new EmailCustomerProcessor().registerCustomer();
	}
}
