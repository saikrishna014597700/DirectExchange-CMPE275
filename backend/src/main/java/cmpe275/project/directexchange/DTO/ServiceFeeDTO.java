/**
 * 
 */
package cmpe275.project.directexchange.DTO;

import java.util.Date;

import cmpe275.project.directexchange.entity.TransactionEntity;

/**
 * @author kaila
 *
 */

public class ServiceFeeDTO {

	private long id;
	private TransactionEntity transaction;
	private double serviceFee;
	private Date serviceFeeDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TransactionEntity getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionEntity transaction) {
		this.transaction = transaction;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Date getServiceFeeDate() {
		return serviceFeeDate;
	}

	public void setServiceFeeDate(Date serviceFeeDate) {
		this.serviceFeeDate = serviceFeeDate;
	}
}
