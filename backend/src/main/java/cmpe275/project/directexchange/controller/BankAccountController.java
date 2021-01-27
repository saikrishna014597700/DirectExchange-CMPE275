/**
 * 
 */
package cmpe275.project.directexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cmpe275.project.directexchange.DTO.BankAccountDTO;
import cmpe275.project.directexchange.service.BankAccountService;

/**
 * @author kaila
 *
 */
@RestController
@RequestMapping("/bankAccount/")
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;

	@PostMapping("/register")
	public ResponseEntity<String> bankAccountRegistration(@RequestBody BankAccountDTO bankAccountDto) {
		if (bankAccountService.bankAccountRegister(bankAccountDto) == 1) {
			return new ResponseEntity<String>("Successfully Registered", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Failed to Registered", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
