package batch.model.domain;

public class Box extends BaseEntity {

	private GoodCategory goodCategory;

	private BoxType type;

	private Shipment shipment;

	public BoxType getType() {
		return type;
	}

	public void setType(BoxType type) {
		this.type = type;
	}

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public GoodCategory getGoodCategory() {
		return goodCategory;
	}

	public void setGoodCategory(GoodCategory goodCategory) {
		this.goodCategory = goodCategory;
	}

}