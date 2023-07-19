package Models;

public class tillTableview {
	private String currency;
	private String currencyCode;
	private Float currentMoney;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Float getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(Float currentMoney) {
		this.currentMoney = currentMoney;
	}

	public tillTableview(String currency, String currencyCode, Float currentMoney) {
		this.currency = currency;
		this.currencyCode = currencyCode;
		this.currentMoney = currentMoney;
	}

}
