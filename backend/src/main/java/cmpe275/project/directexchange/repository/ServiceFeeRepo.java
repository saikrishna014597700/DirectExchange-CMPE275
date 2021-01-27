/**
 * 
 */
package cmpe275.project.directexchange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cmpe275.project.directexchange.entity.ServiceFeeEntity;

/**
 * @author kaila
 *
 */
public interface ServiceFeeRepo extends CrudRepository<ServiceFeeEntity, Long> {
	@Query("from ServiceFeeEntity t where MONTH(t.serviceFeeDate)=:month and YEAR(t.serviceFeeDate)=:year")
	public List<ServiceFeeEntity> findByDate(@Param("month") int month,@Param("year") int year);
}
