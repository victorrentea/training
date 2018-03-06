package ro.victor.training.jpa.orm.advanced.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class NoPK {

	public static class NoPK_PK implements Serializable {
		private int a,b,c;

		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
		public int getB() {
			return b;
		}
		public void setB(int b) {
			this.b = b;
		}
		public int getC() {
			return c;
		}
		public void setC(int c) {
			this.c = c;
		}
		@Override
		public String toString() {
			return "NoPK_PK [a=" + a + ", b=" + b + ", c=" + c + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + a;
			result = prime * result + b;
			result = prime * result + c;
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
			NoPK_PK other = (NoPK_PK) obj;
			if (a != other.a)
				return false;
			if (b != other.b)
				return false;
			if (c != other.c)
				return false;
			return true;
		}
		
		
	}
	
	@EmbeddedId
	private NoPK_PK id;

	public NoPK_PK getId() {
		return id;
	}

	public void setId(NoPK_PK id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "NoPK [id=" + id + "]";
	}
	
	
}
