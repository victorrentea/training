package victor.training.spring.service;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MyWSClient {

	private static MyWSClient INSTANCE;

	private MyWSClient() {

	}

	public static MyWSClient getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MyWSClient();
		}
		return INSTANCE;
	}

	private List<String> endpointURLs;

	public List<String> getEndpointURLs() {
		return endpointURLs;
	}

	public void setEndpointURLs(List<String> endpointURLs) {
		this.endpointURLs = endpointURLs;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int callWebService(String param) {
		// DO serious stuff
		return 1; // call real webservice endpoint method 
	}

}
