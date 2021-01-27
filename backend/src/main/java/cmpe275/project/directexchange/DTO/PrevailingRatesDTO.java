/**
 * 
 */
package cmpe275.project.directexchange.DTO;

/**
 * @author kailash
 *
 */
public class PrevailingRatesDTO {
	
	private long id;
	private String fromCurr;
	private String toCurr;
	private double exchangeRate;
	

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFromCurr() {
		return fromCurr;
	}

	public void setFromCurr(String fromCurr) {
		this.fromCurr = fromCurr;
	}

	public String getToCurr() {
		return toCurr;
	}

	public void setToCurr(String toCurr) {
		this.toCurr = toCurr;
	}
	
}
