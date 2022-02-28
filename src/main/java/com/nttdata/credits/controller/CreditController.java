package com.nttdata.credits.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.credits.entity.Credit;
import com.nttdata.credits.service.CreditService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credits")
public class CreditController {
	
	@Autowired
	private CreditService creditService;
	
	@PostMapping()
    //@ResponseStatus(CREATED)
    public ResponseEntity<Mono<Credit>> create(@RequestBody Credit credit) {
		
		Mono<Credit> created = creditService.createCredit(credit);
		
		return new ResponseEntity<Mono<Credit>>(created,created != null? HttpStatus.CREATED:HttpStatus.BAD_REQUEST);
	 
    }
	
	@GetMapping("/id/{id}")
    public ResponseEntity<Mono<Credit>> findById(@PathVariable String id) {
		
		Mono<Credit> credit = creditService.findCreditById(id);
		
		return new ResponseEntity<Mono<Credit>>(credit,credit != null? HttpStatus.OK:HttpStatus.NOT_FOUND);

    }
	
	@GetMapping("/client/firstName/{firstName}/lastName/{lastName}")
    public ResponseEntity<Flux<Credit>> findByCreditFirstNameAndLastName(@PathVariable String firstName,
                                                                          @PathVariable String lastName) {
		
		Flux <Credit> credits = creditService.findCreditByClientFirstNameAndLastName(firstName, lastName);
		
		return new ResponseEntity<Flux<Credit>>(credits,credits != null? HttpStatus.OK:HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/client/documentNumber/{documentNumber}")
    public ResponseEntity<Flux<Credit>> findByClientDocumentNumber(@PathVariable String documentNumber) {
		
		Flux <Credit> credits = creditService.findCreditByClientDocumentNumber(documentNumber);
		
		return new ResponseEntity<Flux<Credit>>(credits,credits != null? HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
	
	@GetMapping("/number/{number}")
	public ResponseEntity<Mono<Credit>> searchCreditByNumber(@PathVariable("number") String number){
		
		Mono<Credit> credits = creditService.findCreditByNumber(number);
		
		return new ResponseEntity<Mono<Credit>>(credits,credits != null? HttpStatus.OK:HttpStatus.NOT_FOUND);
		
	}
	
	@PatchMapping("/balance/{id}/amount/{amount}")
	public Mono<Credit> payCredit(@PathVariable("id") String id, @PathVariable BigDecimal amount){
		
		Mono<Credit> credits = creditService.updateCreditBalance(id,amount);
		return credits;
		
	}
	
	
	@PutMapping()
    public Mono<Credit> update(@RequestBody Credit credit) {
        return creditService.update(credit);
    }

    @DeleteMapping("/{id}")
    public Mono<Credit> delete(@PathVariable String id) {
        return creditService.delete(id);
    }

}
