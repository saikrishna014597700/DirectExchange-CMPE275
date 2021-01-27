package cmpe275.project.directexchange.service;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmpe275.project.directexchange.DTO.UserDTO;
import cmpe275.project.directexchange.entity.UserEntity;
import cmpe275.project.directexchange.entity.VerificationToken;
import cmpe275.project.directexchange.repository.TokenRepository;
import cmpe275.project.directexchange.repository.UserRepository;
import cmpe275.project.directexchange.utils.EmailServiceUtil;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	EmailServiceUtil email;
	
	public int registerUser(UserDTO user) {
		UserEntity userEntity  = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		if(user.getPassword()==null || user.getPassword().isEmpty())
			userEntity.setPassword("password");
		UserEntity userNameAlready = userRepository.findByUserName(user.getUserName());
		UserEntity nickNameAlready = userRepository.findByNickName(user.getNickName());
		if(userNameAlready==null && nickNameAlready==null ) {
			String token = UUID.randomUUID().toString();
			userRepository.save(userEntity);
			email.createVerificationToken(userEntity, token);
			email.userVerificationMessage(userEntity,token);
		}
		else
			return -1;
		return 1;
	}
	
	public int verifyUser(String userName,String token) {
		VerificationToken vt = tokenRepository.findByToken(token);
		
		if(vt==null)
			return -1;
		UserEntity user = userRepository.findByUserName(userName);
		if(user==null)
			return -2;
		if(vt.getUser().equals(user)) {
			user.setEnabled(true);
			userRepository.save(user);
			return 1;
		}
		return 0;
	}

	/**
	 * @param userName
	 * @param password
	 * @return
	 */
	public int validateLogin(String userName, String password) {
		
		System.out.println("User name:: "+userName);
		// TODO Auto-generated method stub
		UserEntity user = userRepository.findByUserName(userName);
		
		
		if(user != null) {
			System.out.println("User exists in DB :: "+user.getNickName());
		if(user.getPassword().equals(password) && user.isEnabled()) {
			return 1;
		}else if(user.getPassword().equals(password) && !user.isEnabled()) {
			return 2;
		}else if(!user.getPassword().equals(password)) {
			return 3;
		}
		}
		return 0;
	}
}
