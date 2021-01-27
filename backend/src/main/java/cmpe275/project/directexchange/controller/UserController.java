package cmpe275.project.directexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cmpe275.project.directexchange.DTO.UserDTO;
import cmpe275.project.directexchange.service.UserService;
import cmpe275.project.directexchange.utils.EmailServiceUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailServiceUtil emailService;

	@PostMapping("/register")
	public ResponseEntity<String> getTrial(@RequestBody UserDTO userDto) {
		if (userService.registerUser(userDto) == 1)
			return new ResponseEntity<String>("success", HttpStatus.OK);
		else
			return new ResponseEntity<String>("User Already Exists", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/regitrationConfirm/{userName}/{token}")
	public ResponseEntity<String> verifyUser(@PathVariable String userName, @PathVariable String token) {
		if (userService.verifyUser(userName, token) == 1)
			return new ResponseEntity<String>("success", HttpStatus.OK);
		else
			return new ResponseEntity<String>("success", HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String userName, @RequestParam String password) {

		try {
			if (userService.validateLogin(userName, password) == 1) {
				return new ResponseEntity<String>("success", HttpStatus.OK);
			} else if (userService.validateLogin(userName, password) == 2) {
				return new ResponseEntity<String>("validate your token", HttpStatus.OK);
			} else if (userService.validateLogin(userName, password) == 3
					|| userService.validateLogin(userName, password) == 0) {
				return new ResponseEntity<String>("Not a valid user", HttpStatus.UNAUTHORIZED);
			} else {
				return new ResponseEntity<String>("success", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping("/sendMessage")
	public ResponseEntity<String> sendMail(@RequestParam String senderEmail, @RequestParam String receiverEmail,
			@RequestParam String subject, @RequestParam String body) {
		emailService.sendMessage(senderEmail, receiverEmail, body, subject);
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}

}
