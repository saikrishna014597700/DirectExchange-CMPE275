/**
 * 
 */
package cmpe275.project.directexchange.DTO;

import java.util.Date;

/**
 * @author kaila
 *
 */
public class TransactionDTO {

	private long transactionId;
	private ExchangeOfferDTO offer;
	private ExchangeOfferDTO acceptedOffer;
	private Date transactionTime;
	private String userName;
	private String counterUserName;
	private double remittedAmount;
	private String status;

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public ExchangeOfferDTO getAcceptedOffer() {
		return acceptedOffer;
	}

	public void setAcceptedOffer(ExchangeOfferDTO acceptedOffer) {
		this.acceptedOffer = acceptedOffer;
	}

	public ExchangeOfferDTO getOffer() {
		return offer;
	}

	public void setOffer(ExchangeOfferDTO offer) {
		this.offer = offer;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCounterUserName() {
		return counterUserName;
	}

	public void setCounterUserName(String counterUserName) {
		this.counterUserName = counterUserName;
	}

	public double getRemittedAmount() {
		return remittedAmount;
	}

	public void setRemittedAmount(double remittedAmount) {
		this.remittedAmount = remittedAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
