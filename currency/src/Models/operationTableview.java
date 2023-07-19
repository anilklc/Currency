package Models;

public class operationTableview {
	private String customerName;
	private String customerSurname;
	private String customerTC;
	private String customerPhone;
	private String customerMail;
	private String sellCode;
	private String buyCode;
	private Float sellPrice;
	private Float buyAmount;
	private Float sellAmount;
	private String operationDate;
	private String name;
	private String surname;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerSurname() {
		return customerSurname;
	}

	public void setCustomerSurname(String customerSurname) {
		this.customerSurname = customerSurname;
	}

	public String getCustomerTC() {
		return customerTC;
	}

	public void setCustomerTC(String customerTC) {
		this.customerTC = customerTC;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerMail() {
		return customerMail;
	}

	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}

	public String getSellCode() {
		return sellCode;
	}

	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}

	public String getBuyCode() {
		return buyCode;
	}

	public void setBuyCode(String buyCode) {
		this.buyCode = buyCode;
	}

	public Float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Float getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(Float buyAmount) {
		this.buyAmount = buyAmount;
	}

	public Float getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(Float sellAmount) {
		this.sellAmount = sellAmount;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public operationTableview(String customerName, String customerSurname, String customerTC, String customerPhone,
			String customerMail, String sellCode, String buyCode, Float sellPrice, Float buyAmount, Float sellAmount,
			String operationDate) {
		this.customerName = customerName;
		this.customerSurname = customerSurname;
		this.customerTC = customerTC;
		this.customerPhone = customerPhone;
		this.customerMail = customerMail;
		this.sellCode = sellCode;
		this.buyCode = buyCode;
		this.sellPrice = sellPrice;
		this.buyAmount = buyAmount;
		this.sellAmount = sellAmount;
		this.operationDate = operationDate;

	}

	public operationTableview(String name, String surname, String customerName, String customerSurname,
			String customerTC, String customerPhone, String customerMail, String sellCode, String buyCode,
			Float sellPrice, Float buyAmount, Float sellAmount, String operationDate) {
		this.name = name;
		this.surname = surname;
		this.customerName = customerName;
		this.customerSurname = customerSurname;
		this.customerTC = customerTC;
		this.customerPhone = customerPhone;
		this.customerMail = customerMail;
		this.sellCode = sellCode;
		this.buyCode = buyCode;
		this.sellPrice = sellPrice;
		this.buyAmount = buyAmount;
		this.sellAmount = sellAmount;
		this.operationDate = operationDate;

	}
}
