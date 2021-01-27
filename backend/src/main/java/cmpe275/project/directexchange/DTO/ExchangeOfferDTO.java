/**
 * 
 */
package cmpe275.project.directexchange.DTO;

import java.util.Date;

/**
 * @author kaila
 *
 */

public class ExchangeOfferDTO {

	private long offerId;
	private double amount;
	private String sourceCountry;
	private String destinationCountry;
	private String sourceCurrency;
	private String destinationCurrency;
	private String exchangeRate;
	private Date expiryDate;
	private boolean allowCounters = true;
	private boolean allowSplit = true;
	private String status="open";
	private UserDTO user;
	private String userName;
	
	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getSourceCountry() {
		return sourceCountry;
	}

	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public String getDestinationCurrency() {
		return destinationCurrency;
	}

	public void setDestinationCurrency(String destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isAllowCounters() {
		return allowCounters;
	}

	public void setAllowCounters(boolean allowCounters) {
		this.allowCounters = allowCounters;
	}

	public boolean isAllowSplit() {
		return allowSplit;
	}

	public void setAllowSplit(boolean allowSplit) {
		this.allowSplit = allowSplit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
