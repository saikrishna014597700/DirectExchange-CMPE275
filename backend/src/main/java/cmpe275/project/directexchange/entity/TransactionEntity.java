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
import javax.persistence.Table;

/**
 * @author kaila
 *
 */
@Entity
@Table(name = "TRANSACTION")
public class TransactionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long transactionId;

	@Column(name = "USER_OFFER")
	private long offer;

	@Column(name = "ACCEPTED_OFFER")
	private long acceptedOffer;
	
	@Column(name = "TRANSACTION_TIME")
	private Date transactionTime;
	
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "counterUserName")
	private String counterUserName;
	
	@Column(name = "RemittedAmount")
	private double remittedAmount;
	
	@Column(name = "STATUS")
	private String status;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getOffer() {
		return offer;
	}

	public void setOffer(long offer) {
		this.offer = offer;
	}

	public long getAcceptedOffer() {
		return acceptedOffer;
	}

	public void setAcceptedOffer(long acceptedOffer) {
		this.acceptedOffer = acceptedOffer;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
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
