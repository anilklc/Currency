package Models;

public class XMLView {
	private String unit;
	private String currency;
	private String forexBuying;
	private String forexSelling;
	private String currencyCode;

	public XMLView(String unit, String currency, String forexBuying, String forexSelling, String currencyCode) {
		this.unit = unit;
		this.currency = currency;
		this.forexBuying = forexBuying;
		this.forexSelling = forexSelling;
		this.currencyCode = currencyCode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getForexBuying() {
		return forexBuying;
	}

	public void setForexBuying(String forexBuying) {
		this.forexBuying = forexBuying;
	}

	public String getForexSelling() {
		return forexSelling;
	}

	public void setForexSelling(String forexSelling) {
		this.forexSelling = forexSelling;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
