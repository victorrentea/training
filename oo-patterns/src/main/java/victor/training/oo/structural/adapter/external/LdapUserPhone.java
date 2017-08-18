package victor.training.oo.structural.adapter.external;

public class LdapUserPhone {
	private String type;
	private String val;
	
	public LdapUserPhone() {
	}
	public LdapUserPhone(String type, String val) {
		this.type = type;
		this.val = val;
	}
	public final String getType() {
		return type;
	}
	public final void setType(String type) {
		this.type = type;
	}
	public final String getVal() {
		return val;
	}
	public final void setVal(String val) {
		this.val = val;
	}
	
}
