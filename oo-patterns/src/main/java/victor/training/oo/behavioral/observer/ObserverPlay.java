package victor.training.oo.behavioral.observer;

import java.awt.event.ActionListener;

import victor.training.oo.gui.MyFrame;

public class ObserverPlay {
	public static void main(String[] args) {
		MyFrame myFrame = new MyFrame();
		
		// polymorphic assignment - refer o implementare concreta printr-o interfata pe care o implementeaza
		ActionListener actionListener = new MyButtonActionListener();
		
		myFrame.button1.addActionListener(actionListener);
	}
}
