package com.nttdata.credits.repository;

import com.nttdata.credits.entity.Credit;

import reactor.core.publisher.Flux;

public interface CustomCreditRepository {
	
    Flux<Credit> findByClientFirstNameAndLastName(String firstName, String lastName);
    Flux<Credit> findByClientDocumentNumber(String documentNumber);

}
