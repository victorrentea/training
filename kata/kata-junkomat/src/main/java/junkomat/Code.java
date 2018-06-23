package junkomat;

class Code {
	private int code;

	public Code(int code) {
		this.code = code;
	}
	
	public String toString() {
		return "Code[" + code + "]";
	}

	public int hashCode() {
		return code;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Code other = (Code) obj;
		if (code != other.code)
			return false;
		return true;
	}

}