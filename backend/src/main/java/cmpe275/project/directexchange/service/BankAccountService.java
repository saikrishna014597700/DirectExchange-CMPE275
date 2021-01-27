/**
 * 
 */
package cmpe275.project.directexchange.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmpe275.project.directexchange.DTO.BankAccountDTO;
import cmpe275.project.directexchange.entity.BankAccountEntity;
import cmpe275.project.directexchange.entity.UserEntity;
import cmpe275.project.directexchange.repository.BankAccountRepository;
import cmpe275.project.directexchange.repository.UserRepository;

/**
 * @author kaila
 *
 */
@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepo;
	@Autowired
	private UserRepository userRepo;

	public int bankAccountRegister(BankAccountDTO bankAccountDto) {
		BankAccountEntity bankAccount = new BankAccountEntity();
		UserEntity user = userRepo.findByUserName(bankAccountDto.getUserName());
		if (user == null)
			return -1;
		bankAccountDto.setUser(user);
		BeanUtils.copyProperties(bankAccountDto, bankAccount);
		try {
			bankAccountRepo.save(bankAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public boolean hasTwoAccounts(UserEntity user) {
		List<BankAccountEntity> accounts = bankAccountRepo.findByUser(user);
		if(accounts.size()<2)
			return false;
		Set<String> countries = new HashSet<>();
		for(BankAccountEntity account:accounts) {
			countries.add(account.getCountry());
		}
		return countries.size()>=2;	
	}

}
