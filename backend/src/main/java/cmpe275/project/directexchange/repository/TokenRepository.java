package cmpe275.project.directexchange.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cmpe275.project.directexchange.entity.VerificationToken;

public interface TokenRepository extends CrudRepository<VerificationToken, Long>{
	@Query("from VerificationToken u where u.token =:token")
	public VerificationToken findByToken(@Param("token") String token);
}
