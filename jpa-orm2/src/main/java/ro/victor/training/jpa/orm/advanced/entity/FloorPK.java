package ro.victor.training.jpa.orm.advanced.entity;

import java.io.Serializable;

public class FloorPK implements Serializable {
	private String buildingCode;
	private int number;
	public FloorPK() {
	}
	public FloorPK(String buildingCode, int number) {
		this.buildingCode = buildingCode;
		this.number = number;
	}
	public String getBuildingCode() {
		return buildingCode;
	}
	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildingCode == null) ? 0 : buildingCode.hashCode());
		result = prime * result + number;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FloorPK other = (FloorPK) obj;
		if (buildingCode == null) {
			if (other.buildingCode != null)
				return false;
		} else if (!buildingCode.equals(other.buildingCode))
			return false;
		if (number != other.number)
			return false;
		return true;
	}
	
}