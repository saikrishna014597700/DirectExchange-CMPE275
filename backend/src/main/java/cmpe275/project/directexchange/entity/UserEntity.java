package cmpe275.project.directexchange.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author kailash
 *
 */

@Entity
@Table(name = "User")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "NICK_NAME")
	private String nickName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "REPUTATION_RATING")
	private int rating;

	@Column(name = "enabled")
	private boolean enabled;
	
	@OneToMany(mappedBy="user")
    private Set<BankAccountEntity> items;

	public UserEntity() {
		super();
		this.enabled = false;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<BankAccountEntity> getItems() {
		return items;
	}

	public void setItems(Set<BankAccountEntity> items) {
		this.items = items;
	}

}