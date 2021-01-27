/**
 * 
 */
package cmpe275.project.directexchange.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmpe275.project.directexchange.DTO.PrevailingRatesDTO;
import cmpe275.project.directexchange.entity.PrevailingRatesEntity;
import cmpe275.project.directexchange.repository.PrevailingRatesRepository;

/**
 * @author kaila
 *
 */
@Service
public class PrevailingRatesService {

	@Autowired
	private PrevailingRatesRepository repo;

	public boolean saveRates(PrevailingRatesDTO ratesDto) {
		PrevailingRatesEntity rates = new PrevailingRatesEntity();
		BeanUtils.copyProperties(ratesDto, rates);
		repo.save(rates);
		return true;
	}

	public double getRate(String sourceCurrency, String targetCurrency) {
		PrevailingRatesEntity rates = repo.findByFromAndToCurr(sourceCurrency, targetCurrency);
		return rates.getExchangeRate();
	}
	
	public List<PrevailingRatesDTO> getAllRates(){
		List<PrevailingRatesEntity> rates = (List<PrevailingRatesEntity>) repo.findAll();
		List<PrevailingRatesDTO> ratesList = new ArrayList<PrevailingRatesDTO>();
		for(PrevailingRatesEntity rate:rates) {
			PrevailingRatesDTO dto = new PrevailingRatesDTO();
			BeanUtils.copyProperties(rate,dto);
			ratesList.add(dto);
		}
		return ratesList;
	}

}
