/**
 * 
 */
package cmpe275.project.directexchange.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cmpe275.project.directexchange.entity.PrevailingRatesEntity;

/**
 * @author kailash
 *
 */
public interface PrevailingRatesRepository extends CrudRepository<PrevailingRatesEntity, Long> {

	@Query("from PrevailingRatesEntity p where p.fromCurr =:fromCurr and p.toCurr =:toCurr")
	public PrevailingRatesEntity findByFromAndToCurr(@Param("fromCurr") String fromCurr,
			@Param("toCurr") String toCurr);
	
}
