/**
 * 
 */
package cmpe275.project.directexchange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cmpe275.project.directexchange.entity.TransactionEntity;

/**
 * @author kaila
 *
 */
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {
	@Query("from TransactionEntity t where t.userName=:userName or t.counterUserName=:userName")
	public List<TransactionEntity> findByUserName(@Param("userName") String userName);
	
	@Query("from TransactionEntity t where MONTH(t.transactionTime)=:month and YEAR(t.transactionTime)=:year")
	public List<TransactionEntity> findByDate(@Param("month") int month,@Param("year") int year);
}
