package bean;

import java.sql.Date;

public class RentalBean {
	private int rentalId;
	private Date startDate;
	private Date endDate;
	private Integer renovationId;
	private int vehicleId;
	private int sellerId;
	private int clientId;

	public RentalBean(Date startDate, Date endDate, Integer renovationId, int vehicleId, int sellerId, int clientId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.renovationId = renovationId;
		this.vehicleId = vehicleId;
		this.sellerId = sellerId;
		this.clientId = clientId;
	}

	public RentalBean(int rentalId, Date startDate, Date endDate, Integer renovationId, int vehicleId, int sellerId, int clientId) {
		this.rentalId = rentalId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.renovationId = renovationId;
		this.vehicleId = vehicleId;
		this.sellerId = sellerId;
		this.clientId = clientId;
	}

	public RentalBean() {}

	public int getRentalId() {
		return rentalId;
	}

	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
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

	public Integer getRenovationId() {
		return renovationId;
	}

	public void setRenovationId(Integer renovationId) {
		this.renovationId = renovationId;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
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
