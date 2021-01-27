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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author kaila
 *
 */

@Entity
@Table(name = "SERVICE_FEE")
public class ServiceFeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne()
	private TransactionEntity transaction;

	@Column(name = "SERVICE_FEE")
	private double serviceFee;

	@Column
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
