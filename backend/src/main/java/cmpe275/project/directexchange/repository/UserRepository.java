package cmpe275.project.directexchange.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cmpe275.project.directexchange.entity.UserEntity;

@Transactional
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	@Query("from UserEntity u where u.userName =:userName")
	public UserEntity findByUserName(@Param("userName") String userName);

	@Query("from UserEntity u where u.nickName =:nickName")
	public UserEntity findByNickName(@Param("nickName") String nickName);

}
