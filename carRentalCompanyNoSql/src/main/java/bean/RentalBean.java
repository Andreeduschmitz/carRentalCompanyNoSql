package bean;

import java.sql.Date;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class RentalBean {
	@BsonId
	private ObjectId _id;
	private Date startDate;
	private Date endDate;
	private String renovationId;
	private String vehicleId;
	private String sellerId;
	private String clientId;
	
	public RentalBean() {}

	public RentalBean(Date startDate, Date endDate, String renovationId, String vehicleId, String sellerId, String clientId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.renovationId = renovationId;
		this.vehicleId = vehicleId;
		this.sellerId = sellerId;
		this.clientId = clientId;
	}

	public RentalBean(ObjectId rentalId, Date startDate, Date endDate, String renovationId, String vehicleId, String sellerId, String clientId) {
		this._id = rentalId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.renovationId = renovationId;
		this.vehicleId = vehicleId;
		this.sellerId = sellerId;
		this.clientId = clientId;
	}

	@BsonId
	public ObjectId getRentalId() {
		return _id;
	}

	@BsonId
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

	public String getRenovationId() {
		return renovationId;
	}

	public void setRenovationId(String renovationId) {
		this.renovationId = renovationId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
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
