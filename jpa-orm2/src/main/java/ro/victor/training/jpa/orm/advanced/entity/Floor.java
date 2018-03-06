package ro.victor.training.jpa.orm.advanced.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Floor {
	@EmbeddedId
	private FloorPK id;
	
	private int capacity;

	public FloorPK getId() {
		return id;
	}

	public void setId(FloorPK id) {
		this.id = id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	
}
