/**
 * 
 */
package cmpe275.project.directexchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cmpe275.project.directexchange.DTO.PrevailingRatesDTO;
import cmpe275.project.directexchange.service.PrevailingRatesService;

/**
 * @author kaila
 *
 */
@RestController
@RequestMapping("/rates")
public class PrevailingRatesController {
	
	@Autowired
	PrevailingRatesService service;
	
	@GetMapping()
	public ResponseEntity<List<PrevailingRatesDTO>> getAllRates(){
		return new ResponseEntity<List<PrevailingRatesDTO>>(service.getAllRates(),HttpStatus.OK);
	}

}
