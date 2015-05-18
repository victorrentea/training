package ejb.lifecycle;

import javax.ejb.Stateless;

@Stateless
public class StatelessWithStateBean implements StatelessWithState {

	private String parameter;
	
	public void setBusinessParameter(String parameter) {
		this.parameter = parameter;
	}

	public String executeBusiness() {
		return parameter;
	}
	
	
	
}
