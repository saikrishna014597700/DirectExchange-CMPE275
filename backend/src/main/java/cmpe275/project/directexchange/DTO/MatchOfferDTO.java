/**
 * 
 */
package cmpe275.project.directexchange.DTO;

import java.util.List;

/**
 * @author kaila
 *
 */
public class MatchOfferDTO {

	private List<ExchangeOfferDTO> singleOffers;
	private List<SplitOfferDTO> splitOffers;

	public List<ExchangeOfferDTO> getSingleOffers() {
		return singleOffers;
	}

	public void setSingleOffers(List<ExchangeOfferDTO> singleOffers) {
		this.singleOffers = singleOffers;
	}

	public List<SplitOfferDTO> getSplitOffers() {
		return splitOffers;
	}

	public void setSplitOffers(List<SplitOfferDTO> splitOffers) {
		this.splitOffers = splitOffers;
	}
}
