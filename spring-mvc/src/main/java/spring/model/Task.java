package spring.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {

	private String id = UUID.randomUUID().toString();
	private String status = "PENDING";
	private String resultUri;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResultUri() {
		return resultUri;
	}

	public void setResultUri(String resultUri) {
		this.resultUri = resultUri;
	}

}
