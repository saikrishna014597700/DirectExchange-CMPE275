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
@Table(name = "CounterOffers")
public class CounterOffersEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long counterId;
	
	@Column(name = "offerId")
	private long offer;
	
	@Column(name = "counterOfferId")
	private long counterOffer;
	
	@Column
	private double counterAmount;
	
	@Column
	private String proposedUserName;
	
	@Column
	private String counterPartyUserName;

	@Column
	private double proposedAmount;
	
	@Column
	private Date counterTime;
	
	@Column
	private String status;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCounterTime() {
		return counterTime;
	}

	public void setCounterTime(Date counterTime) {
		this.counterTime = counterTime;
	}

	public double getProposedAmount() {
		return proposedAmount;
	}

	public void setProposedAmount(double proposedAmount) {
		this.proposedAmount = proposedAmount;
	}

	public long getCounterId() {
		return counterId;
	}

	public void setCounterId(long counterId) {
		this.counterId = counterId;
	}

	public long getOffer() {
		return offer;
	}

	public void setOffer(long offer) {
		this.offer = offer;
	}

	public long getCounterOffer() {
		return counterOffer;
	}

	public void setCounterOffer(long counterOffer) {
		this.counterOffer = counterOffer;
	}

	public double getCounterAmount() {
		return counterAmount;
	}

	public void setCounterAmount(double counterAmount) {
		this.counterAmount = counterAmount;
	}

	public String getProposedUserName() {
		return proposedUserName;
	}

	public void setProposedUserName(String proposedUserName) {
		this.proposedUserName = proposedUserName;
	}

	public String getCounterPartyUserName() {
		return counterPartyUserName;
	}

	public void setCounterPartyUserName(String counterPartyUserName) {
		this.counterPartyUserName = counterPartyUserName;
	}
	
}
