package victor.training.oo.behavioral.memento;

import victor.training.oo.behavioral.memento.Originator.Memento;

public class Caretaker {
	
	public static void main(String[] args) {
		Originator originator = new Originator();
		Memento previousState = originator.generateMemento();
		System.out.println("Before operation: " + originator);
		
		originator.setNewStuff("Change");
		System.out.println("After operation: " + originator);
		
		// Caretaket wants to roll back the changes
		originator.restoreFromMemento(previousState);
		System.out.println("After rollback: " + originator);
	}
}
