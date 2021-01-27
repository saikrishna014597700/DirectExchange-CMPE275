/**
 * 
 */
package cmpe275.project.directexchange.DTO;

import java.util.Date;

/**
 * @author kaila
 *
 */
public class CounterOffersDTO {
	
	private long counterOfferId;
	private long offer;
	private long counterOffer;
	private double proposedAmount;
	private double counterAmount;
	private String proposedUserName;
	private String counterPartyUserName;
	private Date counterTime;
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

	public long getCounterOfferId() {
		return counterOfferId;
	}

	public void setCounterOfferId(long counterOfferId) {
		this.counterOfferId = counterOfferId;
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

	public String getCounterPartyUserName() {
		return counterPartyUserName;
	}

	public void setCounterPartyUserName(String counterPartyUserName) {
		this.counterPartyUserName = counterPartyUserName;
	}

	public String getProposedUserName() {
		return proposedUserName;
	}

	public void setProposedUserName(String proposedUserName) {
		this.proposedUserName = proposedUserName;
	}

	public double getProposedAmount() {
		return proposedAmount;
	}

	public void setProposedAmount(double proposedAmount) {
		this.proposedAmount = proposedAmount;
	}
}
