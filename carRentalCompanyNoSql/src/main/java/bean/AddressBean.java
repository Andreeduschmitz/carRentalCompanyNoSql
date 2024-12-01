package bean;

import org.bson.types.ObjectId;

public class AddressBean {
	private ObjectId _id;
	private int addressCep;
	private String addressStreet;
	private String addressNeighborhood;
	private int addressNumber;
	private String addressComplement;
	private String clientId;
	
	public AddressBean() {}

	public AddressBean(int adressCep, String adressStreet, String adressNeighborhood, int addressNumber, String adressComplement, String clientId) {
		this.addressCep = adressCep;
		this.addressStreet = adressStreet;
		this.addressNeighborhood = adressNeighborhood;
		this.addressNumber = addressNumber;
		this.addressComplement = adressComplement;
		this.clientId = clientId;
	}

	public AddressBean(ObjectId adressId, int adressCep, String adressStreet, String adressNeighborhood, int addressNumber, String adressComplement, String clientId) {
		this._id = adressId;
		this.addressCep = adressCep;
		this.addressStreet = adressStreet;
		this.addressNeighborhood = adressNeighborhood;
		this.addressNumber = addressNumber;
		this.addressComplement = adressComplement;
		this.clientId = clientId;
	}

	public ObjectId getId() {
		return _id;
	}

	public void setId(ObjectId adressId) {
		this._id = adressId;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
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
