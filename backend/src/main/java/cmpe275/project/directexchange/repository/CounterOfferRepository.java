/**
 * 
 */
package cmpe275.project.directexchange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cmpe275.project.directexchange.entity.CounterOffersEntity;

/**
 * @author kaila
 *
 */
public interface CounterOfferRepository extends CrudRepository<CounterOffersEntity, Long>{
	
	@Query("from CounterOffersEntity c where c.counterPartyUserName=:counterParty")
	public List<CounterOffersEntity> findByCounterParty(@Param("counterParty") String counterParty);
	

}
