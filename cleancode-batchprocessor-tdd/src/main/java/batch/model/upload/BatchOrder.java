package batch.model.upload;

import java.util.ArrayList;
import java.util.List;

import batch.model.domain.Shipment;



/** equivalent to a Shipment */
public class BatchOrder {
    
	private List<BatchOrderLine> lines = new ArrayList<BatchOrderLine>();
    private Shipment shipment;

	public BatchOrder(BatchOrderLine firstOrderLine) {
		lines.add(firstOrderLine);
	}
	
	public void addLine(BatchOrderLine line) {
		lines.add(line);
	}

    public List<BatchOrderLine> getLines() {
		return lines;
	}

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
    
    /**
     * Used to get a representative line for the entire consolidated order.
     * All the consumer-specific info on all the lines is the same.
     */
    private BatchOrderLine getFirstLine() {
		return lines.get(0);
	}

    public String getOrderReference() {
		return getFirstLine().getOrderReference();
	}
    
	public String getEmail() {
		return getFirstLine().getEmail(); 
	}

	public String getPostalCode() {
		return getFirstLine().getPostalCode(); 
	}

	public String getStreet() {
		return getFirstLine().getStreet();
	}
	public String getStreetNr() {
		return getFirstLine().getStreetNr();
	}
	public String getTown() {
		return getFirstLine().getTown();
	}

	public String getFirstName() {
		return getFirstLine().getFirstName();
	}

	public String getLastName() {
		return getFirstLine().getLastName();
	}
	
	public String getPhoneNumber() {
		return getFirstLine().getPhoneNumber();
	}

	public String getFloor() {
		return getFirstLine().getFloor();
	}

	public String getDescription() {
		return getFirstLine().getDescription();
	}

	public String getPrice() {
		return getFirstLine().getPrice();
	}
}
