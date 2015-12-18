package batch.model.domain;


public abstract class BaseEntity {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		BaseEntity x = (BaseEntity) obj;
		if (id == null) {
			return x.id == null;
		}
		return id.equals(x.id); 
	}

}
