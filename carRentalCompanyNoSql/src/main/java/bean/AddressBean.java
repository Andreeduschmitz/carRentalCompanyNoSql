package bean;

public class AddressBean {
	private int addressId;
	private int addressCep;
	private String addressStreet;
	private String addressNeighborhood;
	private int addressNumber;
	private String addressComplement;
	private int clientId;
	
	public AddressBean(int adressCep, String adressStreet, String adressNeighborhood, int addressNumber,
			String adressComplement, int clientId) {
		this.addressCep = adressCep;
		this.addressStreet = adressStreet;
		this.addressNeighborhood = adressNeighborhood;
		this.addressNumber = addressNumber;
		this.addressComplement = adressComplement;
		this.clientId = clientId;
	}

	public AddressBean(int adressId, int adressCep, String adressStreet, String adressNeighborhood, int addressNumber,
			String adressComplement, int clientId) {
		this.addressId = adressId;
		this.addressCep = adressCep;
		this.addressStreet = adressStreet;
		this.addressNeighborhood = adressNeighborhood;
		this.addressNumber = addressNumber;
		this.addressComplement = adressComplement;
		this.clientId = clientId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int adressId) {
		this.addressId = adressId;
	}

	public int getAddressCep() {
		return addressCep;
	}

	public void setAddressCep(int adressCep) {
		this.addressCep = adressCep;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String adressStreet) {
		this.addressStreet = adressStreet;
	}

	public String getAddressNeighborhood() {
		return addressNeighborhood;
	}

	public void setAddressNeighborhood(String adressNeighborhood) {
		this.addressNeighborhood = adressNeighborhood;
	}

	public int getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(int adressNumber) {
		this.addressNumber = adressNumber;
	}

	public String getAddressComplement() {
		return addressComplement;
	}

	public void setAddressComplement(String adressComplement) {
		this.addressComplement = adressComplement;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
	    return String.format("CEP do endereço: %s - Rua: %s - Bairro: %s - Número: %s%s - ID do Cliente: %s",
	            addressCep,
	            addressStreet,
	            addressNeighborhood,
	            addressNumber,
	            (addressComplement != null && !addressComplement.isEmpty()) ? " - Complemento: " + addressComplement : "",
	            clientId);
	}
}
