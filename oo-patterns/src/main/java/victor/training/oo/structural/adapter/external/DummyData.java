package victor.training.oo.structural.adapter.external;

import java.util.Arrays;
import java.util.Date;

public class DummyData {
	public static final LdapUser ldapUser1 = new LdapUser();
	static {
		ldapUser1.setfName("John");
		ldapUser1.setlName("DOE");
		ldapUser1.setuId("jdoe");
		ldapUser1.setCreationDate(new Date());
		ldapUser1.setWorkEmail("0123456789");
		ldapUser1.setEmailAddresses(Arrays.asList(new LdapUserPhone("WORK", "0123456789")));
	}
	public static final LdapUser ldapUser2 = new LdapUser();
	static {
		ldapUser2.setfName("Jane");
		ldapUser2.setlName("DOE");
		ldapUser2.setuId("janedoe");
		ldapUser2.setCreationDate(new Date());
	}
}
