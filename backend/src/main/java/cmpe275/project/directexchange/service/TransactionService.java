/**
 * 
 */
package cmpe275.project.directexchange.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmpe275.project.directexchange.DTO.ExchangeOfferDTO;
import cmpe275.project.directexchange.DTO.SystemFinanceDTO;
import cmpe275.project.directexchange.DTO.TransactionDTO;
import cmpe275.project.directexchange.DTO.UserDTO;
import cmpe275.project.directexchange.DTO.UserTransactionsDTO;
import cmpe275.project.directexchange.entity.ExchangeOfferEntity;
import cmpe275.project.directexchange.entity.ServiceFeeEntity;
import cmpe275.project.directexchange.entity.TransactionEntity;
import cmpe275.project.directexchange.entity.UserEntity;
import cmpe275.project.directexchange.entity.UserTransactions;
import cmpe275.project.directexchange.repository.ExchangeOfferRepository;
import cmpe275.project.directexchange.repository.ServiceFeeRepo;
import cmpe275.project.directexchange.repository.TransactionRepository;
import cmpe275.project.directexchange.repository.UserRepository;
import cmpe275.project.directexchange.repository.UserTransactionsRepository;
import cmpe275.project.directexchange.utils.EmailServiceUtil;

/**
 * @author kaila
 *
 */
@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repo;

	@Autowired
	private ExchangeOfferRepository offerRepo;

	@Autowired
	private EmailServiceUtil mailService;

	@Autowired
	private UserTransactionsRepository userTransactionRepo;

	@Autowired
	private PrevailingRatesService prevailingService;

	@Autowired
	private ServiceFeeRepo serviceFeeRepo;

	@Autowired
	private UserRepository userRepo;

	public boolean saveTransaction(long offerId, long acceptedOfferId) {

		TransactionEntity transaction = new TransactionEntity();
		transaction.setOffer(offerId);
		transaction.setAcceptedOffer(acceptedOfferId);
		transaction.setTransactionTime(Calendar.getInstance().getTime());

		Optional<ExchangeOfferEntity> offer1 = offerRepo.findById(transaction.getAcceptedOffer());
		Optional<ExchangeOfferEntity> offer2 = offerRepo.findById(transaction.getOffer());
		mailService.sendNotification(offer1.get().getUser().getUserName(), offer2.get().getUser().getUserName(),
				offer2.get().getOfferId(), "transaction");
		offer1.get().setStatus("transaction");
		offer2.get().setStatus("transaction");
		offerRepo.save(offer1.get());
		offerRepo.save(offer2.get());
		double amount1 = offer1.get().getAmount();
		if (!offer1.get().getSourceCurrency().equalsIgnoreCase("USD")) {
			amount1 = offer1.get().getAmount() * (prevailingService.getRate(offer1.get().getSourceCurrency(), "USD"));
		}
		double amount2 = offer1.get().getAmount();
		if (!offer1.get().getSourceCurrency().equalsIgnoreCase("USD")) {
			amount2 = offer1.get().getAmount() * (prevailingService.getRate(offer1.get().getSourceCurrency(), "USD"));
		}
		transaction.setRemittedAmount(amount1 + amount2);

		TransactionEntity t = repo.save(transaction);
//		saveUserTransaction(offer1.get().getUser().getUserName(),offer1.get().getSourceCurrency(), offer1.get().getAmount(), amount1, t.getTransactionId());
		return true;

	}

	public TransactionDTO getTransaction(long transactionId) {
		TransactionEntity transaction = repo.findById(transactionId).get();
		TransactionDTO transactionDto = new TransactionDTO();
		BeanUtils.copyProperties(transaction, transactionDto);
		ExchangeOfferDTO offerDto = new ExchangeOfferDTO();
		long offer = transaction.getOffer();
		long accOffer = transaction.getAcceptedOffer();
		ExchangeOfferEntity offerEntity = offerRepo.findById(offer).get();
		BeanUtils.copyProperties(offerEntity, offerDto);
		offerDto.setUserName(offerEntity.getUser().getUserName());
		UserDTO user = new UserDTO();
		UserEntity uu = userRepo.findByUserName(offerDto.getUserName());
		BeanUtils.copyProperties(uu, user);
		offerDto.setUser(user);
		ExchangeOfferDTO acceptedOfferDto = new ExchangeOfferDTO();
		ExchangeOfferEntity accOfferEntity = offerRepo.findById(accOffer).get();
		BeanUtils.copyProperties(accOfferEntity, acceptedOfferDto);
		acceptedOfferDto.setUserName(accOfferEntity.getUser().getUserName());
		UserDTO userAcc = new UserDTO();
		UserEntity uuu = userRepo.findByUserName(acceptedOfferDto.getUserName());
		BeanUtils.copyProperties(uuu, userAcc);
		acceptedOfferDto.setUser(userAcc);
		transactionDto.setAcceptedOffer(acceptedOfferDto);
		transactionDto.setOffer(offerDto);
		return transactionDto;
	}

	public List<TransactionDTO> getAllTransaction() {
		List<TransactionEntity> list = (List<TransactionEntity>) repo.findAll();
		List<TransactionDTO> dtoList = new ArrayList<>();
		for (TransactionEntity transaction : list) {
			if (transaction.getStatus() == null) {
				TransactionDTO dto = new TransactionDTO();
				BeanUtils.copyProperties(transaction, dto);
				ExchangeOfferEntity offer = offerRepo.findById(transaction.getOffer()).get();
				ExchangeOfferEntity acceptedOffer = offerRepo.findById(transaction.getAcceptedOffer()).get();
				ExchangeOfferDTO offerDto = new ExchangeOfferDTO();
				ExchangeOfferDTO acceptedOfferDto = new ExchangeOfferDTO();
				BeanUtils.copyProperties(offer, offerDto);
				BeanUtils.copyProperties(acceptedOffer, acceptedOfferDto);
				offerDto.setUserName(offer.getUser().getUserName());
				acceptedOfferDto.setUserName(acceptedOffer.getUser().getUserName());
				dto.setOffer(offerDto);
				dto.setAcceptedOffer(acceptedOfferDto);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	public List<TransactionDTO> getMyTransactions(String userName) {
		List<TransactionEntity> list = (List<TransactionEntity>) repo.findByUserName(userName);
		List<TransactionDTO> dtoList = new ArrayList<>();
		for (TransactionEntity transaction : list) {
			TransactionDTO dto = new TransactionDTO();
			BeanUtils.copyProperties(transaction, dto);
			ExchangeOfferEntity offer = offerRepo.findById(transaction.getOffer()).get();
			ExchangeOfferEntity acceptedOffer = offerRepo.findById(transaction.getAcceptedOffer()).get();
			ExchangeOfferDTO offerDto = new ExchangeOfferDTO();
			ExchangeOfferDTO acceptedOfferDto = new ExchangeOfferDTO();
			BeanUtils.copyProperties(offer, offerDto);
			BeanUtils.copyProperties(acceptedOffer, acceptedOfferDto);
			dto.setOffer(offerDto);
			dto.setAcceptedOffer(acceptedOfferDto);
			dtoList.add(dto);
		}
		return dtoList;
	}

//	public boolean saveUserTransaction(String userName, String currency, double rate, double amount,
//			long transactionId) {
//		UserTransactions userTransaction = new UserTransactions();
//		userTransaction.setUserName(userName);
//		userTransaction.setTransactionId(transactionId);
//		Date transactionDate = Calendar.getInstance().getTime();
//		userTransaction.setTransactionDate(transactionDate);
//		if (rate == 0)
//			rate = prevailingService.getRate(currency, "USD");
//		userTransactionRepo.save(userTransaction);
//		return true;
//	}

	public List<UserTransactionsDTO> getUserTransactions(String userName, int month, int year) {
		List<UserTransactions> transactionList = userTransactionRepo.getByUserName(userName, month, year);
		List<UserTransactionsDTO> transactionDTOList = new ArrayList<>();
		for (UserTransactions transaction : transactionList) {
			UserTransactionsDTO userTransactionDTO = new UserTransactionsDTO();
			BeanUtils.copyProperties(transaction, userTransactionDTO);
			transactionDTOList.add(userTransactionDTO);
		}
		return transactionDTOList;
	}

	public SystemFinanceDTO getSystemFinance(int month, int year) {
		List<TransactionEntity> transactions = repo.findByDate(month, year);
		int completedTransactions = 0;
		double remittedAmount = 0;
		for (TransactionEntity transaction : transactions) {
			if (transaction.getStatus()!=null && transaction.getStatus().equalsIgnoreCase("completed")) {
				completedTransactions++;
				remittedAmount += transaction.getRemittedAmount();
			}
		}

		SystemFinanceDTO finance = new SystemFinanceDTO();
		finance.setCompletedTransactions(completedTransactions);
		finance.setIncompleteTransactions(transactions.size() - completedTransactions);
		finance.setTotalRemittedUSD(remittedAmount);

		List<ServiceFeeEntity> serviceFee = serviceFeeRepo.findByDate(month, year);
		double serviceFeeTotal = 0;
		for (ServiceFeeEntity serv : serviceFee) {
			serviceFeeTotal += serv.getServiceFee();
		}
		finance.setTotalServiceFee(serviceFeeTotal);
		finance.setMonth(month + "");
		return finance;
	}

}
