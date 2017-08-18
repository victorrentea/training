package victor.training.oo.structural.adapter.external;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LdapUser {
	private String uId;
	private String fName;
	private String lName;
	private Date creationDate;
	private String workEmail;
	private List<LdapUserPhone> emailAddresses = new ArrayList<>();
	
	public final String getuId() {
		return uId;
	}
	public final void setuId(String uId) {
		this.uId = uId;
	}
	public final String getfName() {
		return fName;
	}
	public final void setfName(String fName) {
		this.fName = fName;
	}
	public final String getlName() {
		return lName;
	}
	public final void setlName(String lName) {
		this.lName = lName;
	}
	public final Date getCreationDate() {
		return creationDate;
	}
	public final void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
	public final String getWorkEmail() {
		return workEmail;
	}
	public final void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}
	public final List<LdapUserPhone> getEmailAddresses() {
		return emailAddresses;
	}
	public final void setEmailAddresses(List<LdapUserPhone> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
	
	
	
}
