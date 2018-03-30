package batch.model.domain;

import java.util.SortedSet;
import java.util.TreeSet;


public class ServicePlatform extends BaseEntity {

	private String name;

	private final SortedSet<TimeRange> serviceSlots = new TreeSet<TimeRange>();

	public ServicePlatform(Long id) {
		super.setId(id);
	}

	public ServicePlatform() {
	}

	public ServicePlatform(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SortedSet<TimeRange> getServiceSlots() {
		return serviceSlots;
	}

}
