/**
 * 
 */
package cmpe275.project.directexchange.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cmpe275.project.directexchange.entity.ExchangeOfferEntity;
import cmpe275.project.directexchange.entity.UserEntity;

/**
 * @author kaila
 *
 */
public interface ExchangeOfferRepository extends CrudRepository<ExchangeOfferEntity, Long> {

	@Query("from ExchangeOfferEntity e where e.status='open' and( (sourceCurrency LIKE %:sc%  and amount>=:miSA and amount<=:maSA) and (destinationCurrency LIKE %:dc% and amount>=:miDA and amount<=:maDA))")
	public Page<ExchangeOfferEntity> findAllOpenOffers(Pageable pageable, @Param("sc") String sc,
			@Param("dc") String dc, @Param("miSA") double miSA, @Param("maSA") double maSA, @Param("miDA") double miDA,
			@Param("maDA") double maDA);

	@Query("from ExchangeOfferEntity e where e.status='open' and e.amount<=:highAmount and e.amount>=:lowAmount and e.offerId!=:offerId and e.offerId!=:accOffer and e.sourceCountry=:sourceCountry "
			+ "and e.sourceCountry=:sourceCountry and e.sourceCurrency=:sourceCurrency and e.destinationCountry=:destinationCountry and e.destinationCurrency=:destinationCurrency")
	public Page<ExchangeOfferEntity> findMatchingOffers(Pageable pageable, @Param("highAmount") double highAmount,
			@Param("lowAmount") double lowAmount, @Param("offerId") long offerId,@Param("accOffer") long accOffer,
			@Param("sourceCurrency") String sourceCurrency, @Param("destinationCurrency") String destinationCurrency,
			@Param("sourceCountry") String sourceCountry, @Param("destinationCountry") String destinationCountry);

	@Query("from ExchangeOfferEntity e where e.user=:user")
	public List<ExchangeOfferEntity> findUserOffers(@Param("user") UserEntity user);
	
	@Query("from ExchangeOfferEntity e where e.user=:user and e.status='open'")
	public List<ExchangeOfferEntity> findUserOpenOffers(@Param("user") UserEntity user);
	
	@Query("from ExchangeOfferEntity e where e.user=:user and e.status!='open'")
	public List<ExchangeOfferEntity> findUserOtherOffers(@Param("user") UserEntity user);
	
	@Query("from ExchangeOfferEntity e where e.status='open' "
			+ "and e.sourceCountry=:sourceCountry and e.sourceCurrency=:sourceCurrency and e.destinationCountry=:destinationCountry and e.destinationCurrency=:destinationCurrency")
	public Page<ExchangeOfferEntity> getOpenOffersBySourceAndDestination(Pageable pageable, 
			@Param("sourceCurrency") String sourceCurrency, @Param("destinationCurrency") String destinationCurrency,
			@Param("sourceCountry") String sourceCountry, @Param("destinationCountry") String destinationCountry);


}
