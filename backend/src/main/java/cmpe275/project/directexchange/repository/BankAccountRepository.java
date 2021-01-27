/**
 * 
 */
package cmpe275.project.directexchange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cmpe275.project.directexchange.entity.BankAccountEntity;
import cmpe275.project.directexchange.entity.UserEntity;

/**
 * @author kaila
 *
 */
public interface BankAccountRepository extends CrudRepository<BankAccountEntity, Long>{

	@Query("from BankAccountEntity u where u.user =:user")
	public List<BankAccountEntity> findByUser(@Param("user") UserEntity user);
}
