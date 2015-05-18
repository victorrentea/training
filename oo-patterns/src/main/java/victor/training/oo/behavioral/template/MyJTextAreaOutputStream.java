package victor.training.oo.behavioral.template;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class MyJTextAreaOutputStream extends OutputStream {

	private final JTextArea textArea;

	public MyJTextAreaOutputStream(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		String existingText = textArea.getText();
		char newChar = (char) b;
		textArea.setText(existingText + newChar);

	}
}
