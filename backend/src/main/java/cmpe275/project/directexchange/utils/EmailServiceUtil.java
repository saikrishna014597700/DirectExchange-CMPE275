package cmpe275.project.directexchange.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import cmpe275.project.directexchange.DTO.UserDTO;
import cmpe275.project.directexchange.entity.UserEntity;
import cmpe275.project.directexchange.entity.VerificationToken;
import cmpe275.project.directexchange.repository.TokenRepository;
import cmpe275.project.directexchange.repository.UserRepository;

@Component
public class EmailServiceUtil {
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TokenRepository tokenRepo;
	
	@Autowired
	UserRepository userRepo;

	public void userVerificationMessage(UserEntity user,String token) {
		String recipientAddress = user.getUserName();
		System.out.println("Recipient address "+recipientAddress);
		String subject = "Registration Confirmation";
		String confirmationUrl = "/user/regitrationConfirm/"+user.getUserName()+"/" + token;

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText("\r\nPlease click the below URL for account verification\n" + "http://18.189.194.254:8080" + confirmationUrl+"\n If password is not set, the default password is password ");
		emailSender.send(email);
	}

	public void createVerificationToken(UserEntity user, String token) {
		VerificationToken myToken = new VerificationToken();
		myToken.setUser(user);
		myToken.setToken(token);
		myToken.setExpiry();
		tokenRepo.save(myToken);
	}
	
	public boolean sendNotification(String from,String to,long offerId,String status) {
		String subject ;
		if(status.equalsIgnoreCase("transaction"))
			subject = "Offer entered into transaction";
		else
			subject = "Counter offer from "+userRepo.findByUserName(from).getNickName();

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(to);
		email.setSubject(subject);
		String body = "";
		if(status.equalsIgnoreCase("transaction"))
			body = "User "+userRepo.findByUserName(from).getNickName()+" has accepted your offer with offer id :"+offerId;
		else
			body = "User "+userRepo.findByUserName(from).getNickName()+" made a counter offer to your offer with id :"+offerId;
		email.setText(body);
		emailSender.send(email);
		return true;
	}
	
	public boolean sendMessage(String from,String to,String body,String subject) {

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(to);
		email.setSubject(subject);
		body=body+"\nFrom,\n"+userRepo.findByUserName(from).getNickName();
		email.setText(body);
		emailSender.send(email);
		return true;
	}

}
