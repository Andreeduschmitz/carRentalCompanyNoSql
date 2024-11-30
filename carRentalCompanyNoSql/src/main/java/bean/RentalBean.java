package bean;

import java.sql.Date;

import org.bson.types.ObjectId;

public class RentalBean {
	private ObjectId _id;
	private Date startDate;
	private Date endDate;
	private ObjectId renovationId;
	private ObjectId vehicleId;
	private ObjectId sellerId;
	private ObjectId clientId;
	
	public RentalBean() {}

	public RentalBean(Date startDate, Date endDate, ObjectId renovationId, ObjectId vehicleId, ObjectId sellerId, ObjectId clientId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.renovationId = renovationId;
		this.vehicleId = vehicleId;
		this.sellerId = sellerId;
		this.clientId = clientId;
	}

	public RentalBean(ObjectId rentalId, Date startDate, Date endDate, ObjectId renovationId, ObjectId vehicleId, ObjectId sellerId, ObjectId clientId) {
		this._id = rentalId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.renovationId = renovationId;
		this.vehicleId = vehicleId;
		this.sellerId = sellerId;
		this.clientId = clientId;
	}

	public ObjectId getRentalId() {
		return _id;
	}

	public void setRentalId(ObjectId rentalId) {
		this._id = rentalId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ObjectId getRenovationId() {
		return renovationId;
	}

	public void setRenovationId(ObjectId renovationId) {
		this.renovationId = renovationId;
	}

	public ObjectId getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(ObjectId vehicleId) {
		this.vehicleId = vehicleId;
	}

	public ObjectId getSellerId() {
		return sellerId;
	}

	public void setSellerId(ObjectId sellerId) {
		this.sellerId = sellerId;
	}

	public ObjectId getClientId() {
		return clientId;
	}

	public void setClientId(ObjectId clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
	    return String.format("Locação: Início: %s - Fim: %s - Veículo ID: %s - Vendedor ID: %s - Cliente ID: %s",
	            startDate,
	            endDate,
	            vehicleId,
	            sellerId,
	            clientId);
	}
}
