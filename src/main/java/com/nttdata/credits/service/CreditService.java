package com.nttdata.credits.service;

import com.nttdata.credits.entity.Credit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
	
	Mono<Credit> createCredit(Credit credit);
	Mono<Credit> findCreditById(String id);
	Flux<Credit> findCreditByClientFirstNameAndLastName(String firstName, String lastName);
	Flux<Credit> findCreditByClientDocumentNumber(String documentNumber);
	Mono<Credit> findCreditByNumber(String number);
	Mono<Credit> update(Credit account);
	Mono<Credit> delete(String id);

}
