/**
 * 
 */
package cmpe275.project.directexchange.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cmpe275.project.directexchange.DTO.CounterOffersDTO;
import cmpe275.project.directexchange.DTO.ExchangeOfferDTO;
import cmpe275.project.directexchange.DTO.MatchOfferDTO;
import cmpe275.project.directexchange.DTO.SplitOfferDTO;
import cmpe275.project.directexchange.DTO.TransactionDTO;
import cmpe275.project.directexchange.DTO.UserDTO;
import cmpe275.project.directexchange.DTO.UserTransactionsDTO;
import cmpe275.project.directexchange.entity.CounterOffersEntity;
import cmpe275.project.directexchange.entity.ExchangeOfferEntity;
import cmpe275.project.directexchange.entity.ServiceFeeEntity;
import cmpe275.project.directexchange.entity.TransactionEntity;
import cmpe275.project.directexchange.entity.UserEntity;
import cmpe275.project.directexchange.entity.UserTransactions;
import cmpe275.project.directexchange.repository.CounterOfferRepository;
import cmpe275.project.directexchange.repository.ExchangeOfferRepository;
import cmpe275.project.directexchange.repository.ServiceFeeRepo;
import cmpe275.project.directexchange.repository.TransactionRepository;
import cmpe275.project.directexchange.repository.UserRepository;
import cmpe275.project.directexchange.repository.UserTransactionsRepository;
import cmpe275.project.directexchange.utils.EmailServiceUtil;

/**
 * @author kailash
 *
 */
@Service
public class ExchangeOfferService {

	@Autowired
	private ExchangeOfferRepository repo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CounterOfferRepository counterOfferRepo;

	@Autowired
	private ExchangeOfferRepository offerRepo;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private EmailServiceUtil emailService;

	@Autowired
	private ServiceFeeRepo serviceFeeRepo;

	@Autowired
	private PrevailingRatesService prevailingService;

	@Autowired
	private BankAccountService bankService;

	@Autowired
	private TransactionRepository transactionrepo;

	@Autowired
	private UserTransactionsRepository userTranRepo;

	public int saveExchangeOffer(ExchangeOfferDTO offerDto) {
		makeExpiries();
		UserEntity user = userRepo.findByUserName(offerDto.getUserName());
		if (bankService.hasTwoAccounts(user)) {
			ExchangeOfferEntity offer = new ExchangeOfferEntity();
			BeanUtils.copyProperties(offerDto, offer);
			offer.setUser(userRepo.findByUserName(offerDto.getUserName()));
			repo.save(offer);
			return 1;
		} else
			return -1;
	}

	public List<ExchangeOfferDTO> getAllOpenOffers(String sourceCurrency, double minSourceAmount,
			double maxSourceAmount, String destinationCurrency, double minDestinationAmount,
			double maxDestinationAmount, int pageNumber) {
		makeExpiries();
		String sc = "";
		String dc = "";
		double miSA = 0;
		double maSA = Double.MAX_VALUE;
		if (sourceCurrency != null && !sourceCurrency.isEmpty()) {
			sc = sourceCurrency;
			miSA = minSourceAmount;
			if (maxSourceAmount != 0)
				maSA = maxSourceAmount;
		}
		double miDA = 0;
		double maDA = Double.MAX_VALUE;
		if (destinationCurrency != null && !destinationCurrency.isEmpty()) {
			sc = destinationCurrency;
			miDA = minDestinationAmount;
			if (maxDestinationAmount != 0)
				maDA = maxDestinationAmount;
		}
		try {
			Page<ExchangeOfferEntity> openofPage = repo.findAllOpenOffers(
					PageRequest.of(pageNumber - 1, 10, Sort.by(Sort.Direction.ASC, "amount")), sc, dc, miSA, maSA, miDA,
					maDA);
			List<ExchangeOfferDTO> browseOffers = new ArrayList<ExchangeOfferDTO>();
			for (ExchangeOfferEntity offer : openofPage) {
				ExchangeOfferDTO off = new ExchangeOfferDTO();
				BeanUtils.copyProperties(offer, off);
				UserEntity user = offer.getUser();
				off.setUserName(user.getUserName());
				UserDTO userdto = new UserDTO();
				BeanUtils.copyProperties(user, userdto);
				off.setUser(userdto);
				browseOffers.add(off);

			}
			return browseOffers;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public MatchOfferDTO autoMatch(ExchangeOfferDTO exchangeDto) {

		makeExpiries();
		double rate = 0;
		if (exchangeDto.getExchangeRate().equalsIgnoreCase("Prevailing")) {
			rate = prevailingService.getRate(exchangeDto.getSourceCurrency(), exchangeDto.getDestinationCurrency());
		} else {
			rate = Double.parseDouble(exchangeDto.getExchangeRate());
		}
		double highAmount = exchangeDto.getAmount() * 1.1 * rate;
		double lowAmount = exchangeDto.getAmount() * 0.9 * rate;
		String sourceCurrency = exchangeDto.getDestinationCurrency();
		String destinationCurrency = exchangeDto.getSourceCurrency();
		String sourceCountry = exchangeDto.getDestinationCountry();
		String destinationCountry = exchangeDto.getSourceCountry();
		Page<ExchangeOfferEntity> matchingOffers = repo.findMatchingOffers(PageRequest.of(0, 10), highAmount, lowAmount,
				exchangeDto.getOfferId(), 0, sourceCurrency, destinationCurrency, sourceCountry, destinationCountry);
		List<ExchangeOfferDTO> offerList = new ArrayList<>();
		for (ExchangeOfferEntity offer : matchingOffers) {
			ExchangeOfferDTO offerDTO = new ExchangeOfferDTO();
			BeanUtils.copyProperties(offer, offerDTO);
			offerDTO.setUserName(offer.getUser().getUserName());
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(offer.getUser(), userDto);
			offerDTO.setUser(userDto);
			offerList.add(offerDTO);
		}
		MatchOfferDTO matchOffer = new MatchOfferDTO();
		matchOffer.setSingleOffers(offerList);
		offerList = new ArrayList<>();
		matchingOffers = repo.getOpenOffersBySourceAndDestination(PageRequest.of(0, 10), sourceCurrency,
				destinationCurrency, sourceCountry, destinationCountry);
		List<SplitOfferDTO> map = new ArrayList<>();
		for (ExchangeOfferEntity offer : matchingOffers) {
			double aAmount = exchangeDto.getAmount();
			ExchangeOfferDTO dto = new ExchangeOfferDTO();
			BeanUtils.copyProperties(offer, dto);
			dto.setUserName(offer.getUser().getUserName());
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(offer.getUser(), userDto);
			dto.setUser(userDto);
			double bAmount = offer.getAmount();
			map.addAll(getSplitOffers(aAmount, bAmount, exchangeDto.getOfferId(), dto, offer));
		}
		matchOffer.setSplitOffers(map);
		return matchOffer;
	}

	public ExchangeOfferDTO getOfferById(Long offerId) {
		makeExpiries();
		ExchangeOfferEntity offer = repo.findById(offerId).get();
		ExchangeOfferDTO offerDto = new ExchangeOfferDTO();
		BeanUtils.copyProperties(offer, offerDto);
		offerDto.setUserName(offer.getUser().getUserName());
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(offer.getUser(), userDto);
		offerDto.setUser(userDto);
		return offerDto;
	}

	private List<SplitOfferDTO> getSplitOffers(double aAmount, double bAmount, long offerId, ExchangeOfferDTO dto,
			ExchangeOfferEntity offerAcc) {
		makeExpiries();
		double rate = 0;
		if (dto.getExchangeRate().equalsIgnoreCase("Prevailing")) {
			rate = prevailingService.getRate(dto.getDestinationCurrency(), dto.getSourceCurrency());
		} else {
			rate = Double.parseDouble(dto.getExchangeRate());
		}
		double highAmount = Math.abs(aAmount * rate * 1.1 - bAmount);
		double lowAmount = Math.abs(aAmount * rate * 0.9 - bAmount);
		String sourceCurrency = dto.getSourceCurrency();
		String destinationCurrency = dto.getDestinationCurrency();
		String sourceCountry = dto.getSourceCountry();
		String destinationCountry = dto.getDestinationCountry();
		Page<ExchangeOfferEntity> offers = repo.findMatchingOffers(PageRequest.of(0, 10), highAmount, lowAmount,
				offerId, 0, sourceCurrency, destinationCurrency, sourceCountry, destinationCountry);
		List<SplitOfferDTO> offerList = new ArrayList<>();
		for (ExchangeOfferEntity offer : offers) {
			ExchangeOfferDTO offerDTO = new ExchangeOfferDTO();
			BeanUtils.copyProperties(offer, offerDTO);
			offerDTO.setUserName(offer.getUser().getUserName());
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(offer.getUser(), userDTO);
			offerDTO.setUser(userDTO);
			SplitOfferDTO splitOffer = new SplitOfferDTO();
			splitOffer.setOffer2(dto);
			splitOffer.setOffer1(offerDTO);
			offerList.add(splitOffer);
		}

		highAmount = (aAmount + bAmount) * 1.1;
		lowAmount = (aAmount + bAmount) * 0.9;
		offers = repo.findMatchingOffers(PageRequest.of(0, 10), highAmount, lowAmount, offerId, offerAcc.getOfferId(),
				sourceCurrency, destinationCurrency, sourceCountry, destinationCountry);
		for (ExchangeOfferEntity offer : offers) {
			ExchangeOfferDTO offerDTO = new ExchangeOfferDTO();
			BeanUtils.copyProperties(offer, offerDTO);
			offerDTO.setUserName(offer.getUser().getUserName());
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(offer.getUser(), userDTO);
			offerDTO.setUser(userDTO);
			SplitOfferDTO splitOffer = new SplitOfferDTO();
			splitOffer.setOffer2(dto);
			splitOffer.setOffer1(offerDTO);
			offerList.add(splitOffer);
		}
		return offerList;
	}

	public List<ExchangeOfferDTO> getUserOffers(String userName) {
		makeExpiries();
		UserEntity user = userRepo.findByUserName(userName);
//		List<ExchangeOfferEntity> offers = repo.findUserOffers(user);
		List<ExchangeOfferEntity> offers = repo.findUserOpenOffers(user);
		List<ExchangeOfferEntity> offersOther = repo.findUserOtherOffers(user);
		
		List<ExchangeOfferDTO> offerDtoList = new LinkedList<>();
		for (ExchangeOfferEntity offer : offers) {
			ExchangeOfferDTO dto = new ExchangeOfferDTO();
			BeanUtils.copyProperties(offer, dto);
			dto.setUserName(userName);
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(offer.getUser(), userDTO);
			dto.setUser(userDTO);
			offerDtoList.add(dto);
		}
		for (ExchangeOfferEntity offer : offersOther) {
			ExchangeOfferDTO dto = new ExchangeOfferDTO();
			BeanUtils.copyProperties(offer, dto);
			dto.setUserName(userName);
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(offer.getUser(), userDTO);
			dto.setUser(userDTO);
			offerDtoList.add(dto);
		}
		return offerDtoList;
	}

	public boolean proposeCounter(CounterOffersDTO counterOfferDto) {

		makeExpiries();
		CounterOffersEntity counterOffer = new CounterOffersEntity();
		BeanUtils.copyProperties(counterOfferDto, counterOffer);
		counterOffer.setCounterTime(Calendar.getInstance().getTime());
		counterOfferRepo.save(counterOffer);
		Optional<ExchangeOfferEntity> offer1 = offerRepo.findById(counterOfferDto.getOffer());
		Optional<ExchangeOfferEntity> offer2 = offerRepo.findById(counterOfferDto.getCounterOffer());
		offer1.get().setStatus("CounterMade");
		offer2.get().setStatus("CounterMade");
		offerRepo.save(offer1.get());
		offerRepo.save(offer2.get());
		emailService.sendNotification(offer1.get().getUser().getUserName(), offer2.get().getUser().getUserName(),
				offer2.get().getOfferId(), "counter");
		return true;
	}

	public boolean updateOffer(ExchangeOfferDTO exchangeOfferDTO) {
		makeExpiries();
		ExchangeOfferEntity offer = new ExchangeOfferEntity();
		BeanUtils.copyProperties(exchangeOfferDTO, offer);
		repo.save(offer);
		return true;
	}

	public boolean changeAndAcceptOffers(String userName, long userOfferId, long acceptedOfferId) {
		makeExpiries();
		ExchangeOfferEntity userOffer = repo.findById(userOfferId).get();
		double rate = 0;
		if (userOffer.getExchangeRate().equalsIgnoreCase("Prevailing")) {
			rate = prevailingService.getRate(userOffer.getDestinationCurrency(), userOffer.getSourceCurrency());
		} else {
			rate = Double.parseDouble(userOffer.getExchangeRate());
		}
		userOffer.setAmount(repo.findById(acceptedOfferId).get().getAmount() * rate);
		repo.save(userOffer);
		if (transactionService.saveTransaction(userOfferId, acceptedOfferId))
			return true;
		return false;
	}

	public boolean transferFunds(String userName, long transactionId) {
		makeExpiries();
		TransactionDTO transactionDto = transactionService.getTransaction(transactionId);
		System.out.println("hre transaction:" + transactionDto.getAcceptedOffer());
		System.out.println("hre transaction:" + transactionDto.getOffer());
		ExchangeOfferEntity exchangeOffer = repo.findById(transactionDto.getOffer().getOfferId()).get();
		ExchangeOfferEntity acceptedOffer = repo.findById(transactionDto.getAcceptedOffer().getOfferId()).get();

		if (exchangeOffer.getUser().getUserName().equals(userName)) {
			exchangeOffer.setStatus("FundsTransfered");
			repo.save(exchangeOffer);
		} else {
			acceptedOffer.setStatus("FundsTransfered");
			repo.save(acceptedOffer);
		}
		if (exchangeOffer.getStatus().equals("FundsTransfered")
				&& acceptedOffer.getStatus().equals("FundsTransfered")) {
			ServiceFeeEntity serviceFee = new ServiceFeeEntity();
			TransactionEntity transaction = new TransactionEntity();
			BeanUtils.copyProperties(transactionDto, transaction);
			transaction.setOffer(transactionDto.getOffer().getOfferId());
			transaction.setAcceptedOffer(transactionDto.getAcceptedOffer().getOfferId());
			serviceFee.setTransaction(transaction);

			double offerRate = 1;
			if (!transactionDto.getOffer().getSourceCurrency().equals("USD"))
				offerRate = prevailingService.getRate(transactionDto.getOffer().getSourceCurrency(), "USD");
			double accOfferRate = 1;
			if (!transactionDto.getAcceptedOffer().getSourceCurrency().equals("USD"))
				accOfferRate = prevailingService.getRate(transactionDto.getAcceptedOffer().getSourceCurrency(), "USD");

			double serviceFeePaid = (exchangeOffer.getAmount() * 0.05*offerRate) + (acceptedOffer.getAmount() * 0.05*accOfferRate);
			serviceFee.setServiceFee(serviceFeePaid);
			serviceFee.setServiceFeeDate(Calendar.getInstance().getTime());
			transaction.setStatus("completed");
			transactionrepo.save(transaction);
			serviceFeeRepo.save(serviceFee);

			UserTransactionsDTO userTransactions = new UserTransactionsDTO();
			userTransactions.setServiceFee(exchangeOffer.getAmount() * 0.05*offerRate);
			userTransactions.setSourceAmount(exchangeOffer.getAmount());
			userTransactions.setDestinationAmount(acceptedOffer.getAmount());
			userTransactions.setSourceCurrency(exchangeOffer.getSourceCurrency());
			userTransactions.setDestinationCurrency(exchangeOffer.getDestinationCurrency());
			userTransactions.setTransactionDate(Calendar.getInstance().getTime());
			userTransactions.setTransactionId(transactionId);
			userTransactions.setUserName(exchangeOffer.getUser().getUserName());

			UserTransactionsDTO userTransaction = new UserTransactionsDTO();
			userTransaction.setServiceFee(acceptedOffer.getAmount() * 0.05*accOfferRate);
			userTransaction.setSourceAmount(acceptedOffer.getAmount());
			userTransaction.setDestinationAmount(exchangeOffer.getAmount());
			userTransaction.setSourceCurrency(acceptedOffer.getSourceCurrency());
			userTransaction.setDestinationCurrency(acceptedOffer.getDestinationCurrency());
			userTransaction.setTransactionDate(Calendar.getInstance().getTime());
			userTransaction.setTransactionId(transactionId);
			userTransaction.setUserName(acceptedOffer.getUser().getUserName());

			UserTransactions userTransction = new UserTransactions();
			UserTransactions userTransctions = new UserTransactions();

			BeanUtils.copyProperties(userTransactions, userTransction);
			userTranRepo.save(userTransction);
			BeanUtils.copyProperties(userTransaction, userTransctions);
			userTranRepo.save(userTransctions);

		}
		return false;
	}

	public List<CounterOffersDTO> getMyCounterOffers(String userName) {
		makeExpiries();
		List<CounterOffersEntity> counterList = counterOfferRepo.findByCounterParty(userName);
		List<CounterOffersDTO> resultList = new LinkedList<>();
		for (CounterOffersEntity counter : counterList) {
			CounterOffersDTO counterOffer = new CounterOffersDTO();
			BeanUtils.copyProperties(counter, counterOffer);
			resultList.add(counterOffer);
		}
		return resultList;
	}

	private void makeExpiries() {
		try {
			cancelTransaction();
			List<CounterOffersEntity> counterList = (List<CounterOffersEntity>) counterOfferRepo.findAll();
			for (CounterOffersEntity counter : counterList) {
				long now = Calendar.getInstance().getTime().getTime();
				long time = counter.getCounterTime().getTime();
				if (now - time > 300000) {
					counter.setStatus("Expired");
					counterOfferRepo.save(counter);
				}
			}
			userRating();
		} catch (Exception e) {

		}
	}

	private void cancelTransaction() {
		List<TransactionDTO> transactionList = transactionService.getAllTransaction();
		try {
			for (TransactionDTO transaction : transactionList) {
				long now = Calendar.getInstance().getTime().getTime();
				long time = transaction.getTransactionTime().getTime();
				if (now - time > 600000) {
					long offerId = transaction.getOffer().getOfferId();
					long acceptedOfferId = transaction.getAcceptedOffer().getOfferId();
					ExchangeOfferEntity offer = repo.findById(offerId).get();
					ExchangeOfferEntity offerAccepted = repo.findById(acceptedOfferId).get();
					offer.setStatus("Cancelled");
					offerAccepted.setStatus("Cancelled");
					transaction.setStatus("Cancelled");
					TransactionEntity tran = new TransactionEntity();
					BeanUtils.copyProperties(transaction, tran);
					transactionrepo.save(tran);
					repo.save(offer);
					repo.save(offerAccepted);
				}
			}
		} catch (Exception e) {

		}
	}

	private void userRating() {
		try {
			List<UserEntity> userList = (List<UserEntity>) userRepo.findAll();
			for (UserEntity user : userList) {
				List<ExchangeOfferEntity> offers = repo.findUserOffers(user);
				int count = 0;
				for (ExchangeOfferEntity offer : offers) {
					if (offer.getStatus().equalsIgnoreCase("Cancelled")) {
						count++;
					}
				}
				if (transactionService.getMyTransactions(user.getUserName()) != null
						&& transactionService.getMyTransactions(user.getUserName()).size() > 0) {
					float percent = count / (float) offers.size();
					int rating = Math.round(((1 - percent) * 4)) + 1;
					user.setRating(rating);
					userRepo.save(user);
				}
			}
		} catch (Exception e) {

		}
	}

	/**
	 * @param initialOfferId
	 * @param acceptedOfferId
	 * @return
	 */
	public int checkExactMatch(long initialOfferId, long acceptedOfferId) {
		// TODO Auto-generated method stub
		ExchangeOfferEntity exchangeOffer = repo.findById(initialOfferId).get();
		ExchangeOfferEntity acceptedOffer = repo.findById(acceptedOfferId).get();
		double rate = prevailingService.getRate(acceptedOffer.getSourceCurrency(), exchangeOffer.getSourceCurrency());
		if (acceptedOffer.getAmount() * rate ==exchangeOffer.getAmount()) {
			return 1;
		}
		return 0;
	}

	public boolean acceptCounterOffer(CounterOffersDTO counter) {

		ExchangeOfferEntity exchangeOffer = repo.findById(counter.getOffer()).get();
		ExchangeOfferEntity counterOffer = repo.findById(counter.getCounterOffer()).get();

		counterOffer.setAmount(counter.getCounterAmount());
		exchangeOffer.setStatus("transaction");
		counterOffer.setStatus("transaction");

		repo.save(exchangeOffer);
		repo.save(counterOffer);
		CounterOffersEntity co = new CounterOffersEntity();
		BeanUtils.copyProperties(counter, co);
		co.setStatus("completed");
		counterOfferRepo.save(co);
		transactionService.saveTransaction(counter.getOffer(), counter.getCounterOffer());
		return true;
	}

}
