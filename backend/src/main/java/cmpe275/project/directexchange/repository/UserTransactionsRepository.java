/**
 * 
 */
package cmpe275.project.directexchange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cmpe275.project.directexchange.entity.UserTransactions;

/**
 * @author kaila
 *
 */
public interface UserTransactionsRepository extends CrudRepository<UserTransactions, Long>{

	@Query("from UserTransactions t where t.userName=:userName and MONTH(t.transactionDate)=:month and YEAR(t.transactionDate)=:year")
	public List<UserTransactions> getByUserName(@Param("userName") String UserName,@Param("month") int month,@Param("year") int year);
}
