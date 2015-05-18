package victor.training.oo.structural.adapter;

/**
 * An example of Value Object
 */
public class Phone {

	private final String prefix;
	private final String localNumber;

	public Phone(String prefix, String localNumber) {
		this.prefix = prefix;
		this.localNumber = localNumber;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getLocalNumber() {
		return localNumber;
	}

	@Override
	public String toString() {
		return prefix + "-" + localNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Phone)) {
			return false;
		}
		Phone phone = (Phone) obj;
		return phone.prefix.equals(prefix) && phone.localNumber.equals(localNumber); // What's wrong here: NPE
		// Defensive programming should not fail for nulls 
	}
	
	@Override
	public int hashCode() {
		return 17 * prefix.hashCode() + localNumber.hashCode(); // and here: NPE
		// Defensive programming should not fail for nulls
	}
}
