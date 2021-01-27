/**
 * 
 */
package cmpe275.project.directexchange.DTO;

/**
 * @author kaila
 *
 */
public class SplitOfferDTO {

	private ExchangeOfferDTO offer1;
	private ExchangeOfferDTO offer2;

	public ExchangeOfferDTO getOffer1() {
		return offer1;
	}

	public void setOffer1(ExchangeOfferDTO offer1) {
		this.offer1 = offer1;
	}

	public ExchangeOfferDTO getOffer2() {
		return offer2;
	}

	public void setOffer2(ExchangeOfferDTO offer2) {
		this.offer2 = offer2;
	}
}
