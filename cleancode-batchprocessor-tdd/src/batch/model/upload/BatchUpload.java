package batch.model.upload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import batch.model.domain.Merchant;
import batch.model.domain.Shipment;



public class BatchUpload {
	private Merchant merchant;
	private Date appointmentDate;
	private List<BatchOrderLine> orderLines;

	/** produced by the batch upload procesisng */
	private List<Shipment> createdShipments;

    public BatchUpload() {
	}

	public BatchUpload(List<BatchOrderLine> orderLines, Merchant merchant, Date appointmentDate) {
		this.orderLines = orderLines;
		this.merchant = merchant;
		this.appointmentDate = appointmentDate;
	}

	public List<BatchOrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<BatchOrderLine> orderLines) {
		this.orderLines = orderLines;
	}

    public List<BatchOrderLine> getOrderLinesWithoutErrors(){
        List<BatchOrderLine> batchOrderLinesWithoutErrors = new ArrayList<BatchOrderLine>();
        for(BatchOrderLine batchOrderLine: orderLines){
            if(!batchOrderLine.hasErrors()) {
            	batchOrderLinesWithoutErrors.add(batchOrderLine);
            }
        }

        return batchOrderLinesWithoutErrors;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public List<Shipment> getCreatedShipments() {
		return createdShipments;
	}

	public void setCreatedShipments(List<Shipment> createdShipments) {
		this.createdShipments = createdShipments;
	}
	
}
