package batch.model.domain;

import java.util.HashSet;
import java.util.Set;

public class Merchant extends BaseEntity {

	private String name;

	private Set<Shop> shops = new HashSet<Shop>();

	private Set<BoxType> allowedBoxTypes = new HashSet<BoxType>();

	public Set<Shop> getShops() {
		return shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

	public Set<BoxType> getAllowedBoxTypes() {
		return allowedBoxTypes;
	}

	public void setAllowedBoxTypes(Set<BoxType> allowedBoxTypes) {
		this.allowedBoxTypes = allowedBoxTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Shop getShopForPlatform(ServicePlatform platform) {
		for (Shop shop : shops) {
			if (shop.getPlatform().equals(platform)) {
				return shop;
			}
		}
		return null;
	}

}
