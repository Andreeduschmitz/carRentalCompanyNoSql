package bean;

import org.bson.types.ObjectId;

public class ClientBean {
	private ObjectId _id;
	private String clientName;
	private String clientCpf;
	private String clientPhone;
	private String clientEmail;
	private boolean isActive;
	
	public ClientBean() {}

	public ClientBean(String clientName, String clientCpf, String clientPhone, String clientEmail, boolean isActive) {
		this.clientName = clientName;
		this.clientCpf = clientCpf;
		this.clientPhone = clientPhone;
		this.clientEmail = clientEmail;
		this.isActive = isActive;
	}

	public ClientBean(ObjectId clientId, String clientName, String clientCpf, String clientPhone, String clientEmail, boolean isActive) {
		this._id = clientId;
		this.clientName = clientName;
		this.clientCpf = clientCpf;
		this.clientPhone = clientPhone;
		this.clientEmail = clientEmail;
		this.isActive = isActive;
	}

	public ObjectId getClientId() {
		return _id;
	}

	public void setClientId(ObjectId clientId) {
		this._id = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientCpf() {
		return clientCpf;
	}

	public void setClientCpf(String clientCpf) {
		this.clientCpf = clientCpf;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
	    return String.format("Cliente: %s - CPF: %s - Telefone: %s - Email: %s",
	            clientName,
	            clientCpf,
	            clientPhone,
	            clientEmail != null ? clientEmail : "Não informado");
	}
}
