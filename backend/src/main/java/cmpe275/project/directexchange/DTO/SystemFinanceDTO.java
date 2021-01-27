/**
 * 
 */
package cmpe275.project.directexchange.DTO;

/**
 * @author kaila
 *
 */
public class SystemFinanceDTO {
	
	private int completedTransactions;
	private int incompleteTransactions;
	private double totalRemittedUSD;
	private double totalServiceFee;
	private String month;
	public int getCompletedTransactions() {
		return completedTransactions;
	}
	public void setCompletedTransactions(int completedTransactions) {
		this.completedTransactions = completedTransactions;
	}
	public int getIncompleteTransactions() {
		return incompleteTransactions;
	}
	public void setIncompleteTransactions(int incompleteTransactions) {
		this.incompleteTransactions = incompleteTransactions;
	}
	public double getTotalRemittedUSD() {
		return totalRemittedUSD;
	}
	public void setTotalRemittedUSD(double totalRemittedUSD) {
		this.totalRemittedUSD = totalRemittedUSD;
	}
	public double getTotalServiceFee() {
		return totalServiceFee;
	}
	public void setTotalServiceFee(double totalServiceFee) {
		this.totalServiceFee = totalServiceFee;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}
