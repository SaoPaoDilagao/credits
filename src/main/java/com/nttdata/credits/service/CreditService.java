package com.nttdata.credits.service;

import java.math.BigDecimal;

import com.nttdata.credits.dto.response.CreditCardFeesData;
import com.nttdata.credits.entity.Credit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Credit service interface.
 */
public interface CreditService {
  Mono<Credit> createCredit(Credit credit);

  Mono<Credit> findCreditById(String id);

  Flux<Credit> findCreditByClientFirstNameAndLastName(String firstName, String lastName);

  Flux<Credit> findCreditByClientDocumentNumber(String documentNumber);

  Mono<Credit> findCreditByNumber(String number);

  Mono<Credit> updateCreditBalance(String id, BigDecimal amount);

  Mono<Credit> update(Credit account);

  Mono<Credit> delete(String id);

  Mono<Credit> findIfClientOwnsCreditCard(String documentNumber);

  Mono<BigDecimal> getCreditCardBalance(String number);
  
  Mono<Integer> getMonthlyFeeExpirationDate(String number);
  
  Mono<CreditCardFeesData> getCreditCardFeesData(String number);
  
  Mono<Boolean> checkIfClientHasDebs(String documentNumber);
  
  
}
