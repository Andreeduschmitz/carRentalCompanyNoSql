package bean;

public class ClientBean {
	private int clientId;
	private String clientName;
	private long clientCpf;
	private String clientPhone;
	private String clientEmail;
	private boolean isActive;

	public ClientBean(String clientName, long clientCpf, String clientPhone, String clientEmail) {
		super();
		this.clientName = clientName;
		this.clientCpf = clientCpf;
		this.clientPhone = clientPhone;
		this.clientEmail = clientEmail;
	}

	public ClientBean(int clientId, String clientName, long clientCpf, String clientPhone, String clientEmail) {
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientCpf = clientCpf;
		this.clientPhone = clientPhone;
		this.clientEmail = clientEmail;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public long getClientCpf() {
		return clientCpf;
	}

	public void setClientCpf(long clientCpf) {
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
