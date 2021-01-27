/**
 * 
 */
package cmpe275.project.directexchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cmpe275.project.directexchange.DTO.CounterOffersDTO;
import cmpe275.project.directexchange.DTO.ExchangeOfferDTO;
import cmpe275.project.directexchange.DTO.MatchOfferDTO;
import cmpe275.project.directexchange.DTO.SystemFinanceDTO;
import cmpe275.project.directexchange.DTO.TransactionDTO;
import cmpe275.project.directexchange.DTO.UserTransactionsDTO;
import cmpe275.project.directexchange.service.ExchangeOfferService;
import cmpe275.project.directexchange.service.TransactionService;

/**
 * @author kaila
 *
 */

@RestController
@RequestMapping("/offer")
public class ExchangeOfferController {

	@Autowired
	private ExchangeOfferService service;

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/create")
	public ResponseEntity<String> createExchangeOffer(@RequestBody ExchangeOfferDTO offer) {
		if (service.saveExchangeOffer(offer) == 1)
			return new ResponseEntity<String>("Successfully created offer", HttpStatus.OK);
		else if (service.saveExchangeOffer(offer) == -1)
			return new ResponseEntity<String>("Two Accounts needed", HttpStatus.OK);
		return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/getMatchingOffer/{offerId}")
	public ResponseEntity<MatchOfferDTO> getMatchinOffer(@PathVariable("offerId") long offerId) {
		ExchangeOfferDTO exchangeDto = service.getOfferById(offerId);
		MatchOfferDTO offers = service.autoMatch(exchangeDto);
		return new ResponseEntity<MatchOfferDTO>(offers, HttpStatus.OK);
	}

	@PostMapping("/acceptOffer")
	public ResponseEntity<String> acceptOffer(@RequestParam("userName") String userName,
			@RequestParam("userOfferId") long userOfferId, @RequestParam("acceptedOfferId") long acceptedOfferId) {
		if (transactionService.saveTransaction(userOfferId, acceptedOfferId))
			return new ResponseEntity<String>("successfully accepted the offer", HttpStatus.OK);
		return new ResponseEntity<String>("Failed to accept", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/counterOffer")
	public ResponseEntity<String> counterOffer(@RequestBody CounterOffersDTO counterOffer) {
		if (service.proposeCounter(counterOffer))
			return new ResponseEntity<String>("successfully proposed counter", HttpStatus.OK);
		else
			return new ResponseEntity<String>("failed to propose counter", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/userOffers/{userName}")
	public ResponseEntity<List<ExchangeOfferDTO>> myOffers(@PathVariable("userName") String userName) {
		List<ExchangeOfferDTO> userOffers = service.getUserOffers(userName);
		return new ResponseEntity<List<ExchangeOfferDTO>>(userOffers, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateExchangeOffer(@RequestBody ExchangeOfferDTO offer) {
		service.updateOffer(offer);
		return new ResponseEntity<String>("Successfully updated offer", HttpStatus.OK);
	}

	@GetMapping("/getOffers/{pageNumber}")
	public ResponseEntity<List<ExchangeOfferDTO>> getOpenOffers(@PathVariable("pageNumber") int pageNumber) {
		String sc = "";
		String dc = "";
		double miSA = 0;
		double maSA = Double.MAX_VALUE;
		double miDA = 0;
		double maDA = Double.MAX_VALUE;
		List<ExchangeOfferDTO> offers = service.getAllOpenOffers(sc, miSA, maSA, dc, miDA, maDA, pageNumber);
		return new ResponseEntity<List<ExchangeOfferDTO>>(offers, HttpStatus.OK);
	}

	@PostMapping("/getOffers")
	public ResponseEntity<List<ExchangeOfferDTO>> getOffersWithFiler(
			@RequestParam("sourceCurrency") String sourceCurrency,
			@RequestParam("minSourceAmount") double minSourceAmount,
			@RequestParam("maxSourceAmount") double maxSourceAmount,
			@RequestParam("destinationCurrency") String destinationCurrency,
			@RequestParam("minDestinationAmount") double minDestinationAmount,
			@RequestParam("maxDestinationAmount") double maxDestinationAmount,
			@RequestParam("pageNumber") int pageNumber) {
		List<ExchangeOfferDTO> offers = service.getAllOpenOffers(sourceCurrency, minSourceAmount, maxSourceAmount,
				destinationCurrency, minDestinationAmount, maxDestinationAmount, pageNumber);
		return new ResponseEntity<List<ExchangeOfferDTO>>(offers, HttpStatus.OK);
	}

	@GetMapping("/getOffer/{offerId}")
	public ResponseEntity<ExchangeOfferDTO> getOffer(@PathVariable("offerId") long offerId) {
		ExchangeOfferDTO exchangeDto = service.getOfferById(offerId);
		return new ResponseEntity<ExchangeOfferDTO>(exchangeDto, HttpStatus.OK);
	}

	@PostMapping("/checkExactMatch")
	public ResponseEntity<String> checkExactMatch(@RequestParam("initialOfferId") long initialOfferId,
			@RequestParam("acceptedOfferId") long acceptedOfferId) {

		int res = service.checkExactMatch(initialOfferId, acceptedOfferId);
		if (res == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Failed to accept", HttpStatus.OK);
	}

	@PostMapping("/changeAndAccept")
	public ResponseEntity<String> changeAndAccept(@RequestParam("userName") String userName,
			@RequestParam("userOfferId") long userOfferId, @RequestParam("acceptedOfferId") long acceptedOfferId) {

		service.changeAndAcceptOffers(userName, userOfferId, acceptedOfferId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	@GetMapping("/getMyCounters/{userName}")
	public ResponseEntity<List<CounterOffersDTO>> getMyCounterOffers(@PathVariable("userName") String userName) {
		return new ResponseEntity<List<CounterOffersDTO>>(service.getMyCounterOffers(userName), HttpStatus.OK);
	}

	
	
	@GetMapping("/getMyTransaction/{userName}")
	public ResponseEntity<List<TransactionDTO>> getMyTransaction(@PathVariable("userName") String userName){
		List<TransactionDTO> transactions = transactionService.getAllTransaction();
		return new ResponseEntity<List<TransactionDTO>>(transactions, HttpStatus.OK);
	}

	@PostMapping("/transactionReport")
	public ResponseEntity<List<UserTransactionsDTO>> getTransactionReport(@RequestParam("userName") String userName,
			@RequestParam("month") int month, @RequestParam("year") int year) {
		List<UserTransactionsDTO> transactions = transactionService.getUserTransactions(userName, month, year);
		return new ResponseEntity<List<UserTransactionsDTO>>(transactions, HttpStatus.OK);
	}

	@GetMapping("/getUserTransactions/{userName}/{month}/{year}")
	public ResponseEntity<List<UserTransactionsDTO>> getUserTransactions(@PathVariable("userName") String userName,
			@PathVariable("month") int month, @PathVariable("year") int year) {
		List<UserTransactionsDTO> trans = transactionService.getUserTransactions(userName, month, year);
		return new ResponseEntity<List<UserTransactionsDTO>>(trans, HttpStatus.OK);
	}

	@GetMapping("/systemFinancials/{month}/{year}")
	public ResponseEntity<SystemFinanceDTO> getSystemFinancials(@PathVariable("month") int month,
			@PathVariable("year") int year) {
		SystemFinanceDTO trans = transactionService.getSystemFinance(month, year);
		return new ResponseEntity<SystemFinanceDTO>(trans, HttpStatus.OK);
	}

	
	@PostMapping("/transferFunds")
	public ResponseEntity<String> transferFunds(@RequestParam("userName") String userName,@RequestParam("transactionId") long transactionId){
		service.transferFunds(userName, transactionId);
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
	
	@PostMapping("/acceptCounter")
	public ResponseEntity<String> acceptCounters(@RequestBody CounterOffersDTO counterOffer){
		service.acceptCounterOffer(counterOffer);
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
}
