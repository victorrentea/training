package batch.model.domain;

public class Shop extends BaseEntity {

	private String name;
	
	private ServicePlatform platform;

	private Merchant merchant;

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public ServicePlatform getPlatform() {
		return platform;
	}

	public void setPlatform(ServicePlatform platform) {
		this.platform = platform;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
