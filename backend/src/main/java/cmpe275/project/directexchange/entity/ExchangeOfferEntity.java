/**
 * 
 */
package cmpe275.project.directexchange.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author kaila
 *
 */

@Entity
@Table(name = "ExchangeOffer")
public class ExchangeOfferEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long offerId;
	
	@Column(name = "AMOUNT")
	private double amount;
	
	@Column(name = "SOURCE_COUNTRY")
	private String sourceCountry;
	
	@Column(name = "DESTINATION_COUNTRY")
	private String destinationCountry;
	
	@Column(name = "SOURCE_CURRENCY")
	private String sourceCurrency;
	
	@Column(name = "DESTINATION_CURRENCY")
	private String destinationCurrency;
	
	@Column(name = "EXCHANGE_RATE")
	private String exchangeRate;
	
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;
	
	@Column(name = "ALLOW_COUNTERS")
	private boolean allowCounters = true;
	
	@Column(name = "ALLOW_SPLIT")
	private boolean allowSplit = true;
	
	@Column(name = "STATUS")
	private String status="open";
	
	@ManyToOne
	@JoinColumn(name="userName", nullable=false)
	private UserEntity user;

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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
}
