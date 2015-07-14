package victor.training.oo.behavioral.memento;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Originator implements Serializable {

	public class Memento {
		private final byte[] bytes;

		private Memento(byte[] bytes) {
			this.bytes = bytes;
		}
	}
	
	private String newStuff = "OldStuff";

	
	public String getNewStuff() {
		return newStuff;
	}


	public void setNewStuff(String newStuff) {
		this.newStuff = newStuff;
	}


	@Override
	public String toString() {
		return "Originator(newStuff="+newStuff+")";
	}


	public Memento generateMemento() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.close();
			return new Memento(baos.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public void restoreFromMemento(Memento previousState) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(previousState.bytes));
			Originator previousMe = (Originator) ois.readObject();

			// restore state
			this.newStuff = previousMe.newStuff;
			
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
}
