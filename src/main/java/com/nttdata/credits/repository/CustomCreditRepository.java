package com.nttdata.credits.repository;

import com.nttdata.credits.entity.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Custom credit repository.
 */
public interface CustomCreditRepository {

  Flux<Credit> findByClientFirstNameAndLastName(String firstName, String lastName);

  Flux<Credit> findByClientDocumentNumber(String documentNumber);

  Mono<Credit> findByClientDocumentNumberAndCreditType(String documentNumber, Integer creditType);
}
