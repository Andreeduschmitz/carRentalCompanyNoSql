package bean;

import org.bson.types.ObjectId;

public class SellerBean {
	private ObjectId _id;
	private String sellerName;
	private String sellerPhone;
	private String sellerEmail;
	private boolean isActive;
	
	public SellerBean() {}

	public SellerBean(String sellerName, String sellerPhone, String sellerEmail) {
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
		this.sellerEmail = sellerEmail;
	}

	public SellerBean(ObjectId sellerId, String sellerName, String sellerPhone, String sellerEmail) {
		this._id = sellerId;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
		this.sellerEmail = sellerEmail;
	}
	
	public ObjectId getSellerId() {
		return _id;
	}

	public void setSellerId(ObjectId sellerId) {
		this._id = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
	    return String.format("Vendedor: %s - Telefone: %s - Email: %s",
	            sellerName,
	            sellerPhone,
	            sellerEmail != null ? sellerEmail : "Não informado");
	}
}
