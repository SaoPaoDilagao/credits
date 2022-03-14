package com.nttdata.credits.controller;

import com.nttdata.credits.dto.request.CreditRequest;
import com.nttdata.credits.entity.Credit;
import com.nttdata.credits.service.CreditService;
import java.math.BigDecimal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * RestController for credit service.
 */
@RestController
@RequestMapping("/credits")
public class CreditController {
  @Autowired
  private CreditService creditService;

  @GetMapping("/client/documentNumber/{documentNumber}")
  public Flux<Credit> findByClientDocumentNumber(@PathVariable String documentNumber) {
    return creditService.findCreditByClientDocumentNumber(documentNumber);
  }

  @GetMapping("/number/{number}")
  public Mono<Credit> searchCreditByNumber(@PathVariable("number") String number) {
    return creditService.findCreditByNumber(number);
  }

  @PostMapping()
  public Mono<Credit> create(@Valid @RequestBody CreditRequest request) {
    Credit credit = new Credit(request);
    return creditService.createCredit(credit);
  }

  @PutMapping("/balance/{id}/amount/{amount}")
  public Mono<Credit> payCredit(@PathVariable("id") String id, @PathVariable BigDecimal amount) {
    return creditService.updateCreditBalance(id, amount);
  }


  @GetMapping("/id/{id}")
  public Mono<Credit> findById(@PathVariable String id) {
    return creditService.findCreditById(id);
  }

  @GetMapping("/client/firstName/{firstName}/lastName/{lastName}")
  public Flux<Credit> findByCreditFirstNameAndLastName(@PathVariable String firstName,
                                                       @PathVariable String lastName) {
    return creditService.findCreditByClientFirstNameAndLastName(firstName, lastName);
  }

  @GetMapping("/clientOwnsCard/{documentNumber}")
  public Mono<Credit> checkIfClientOwnsCreditCard(
      @PathVariable("documentNumber") String documentNumber) {
    return creditService.findIfClientOwnsCreditCard(documentNumber);
  }

  @GetMapping("/getBalance/{number}")
  public Mono<BigDecimal> getCreditCardBalance(@PathVariable("number") String number) {
    return creditService.getCreditCardBalance(number);
  }

  @PutMapping()
  public Mono<Credit> update(@Valid @RequestBody CreditRequest request) {
    Credit credit = new Credit(request);
    return creditService.update(credit);
  }

  @DeleteMapping("/{id}")
  public Mono<Credit> delete(@PathVariable String id) {
    return creditService.delete(id);
  }

  @GetMapping("/check/debts/{documentNumber}")
  public Mono<Boolean> checkIfClientHasDebs(@PathVariable String documentNumber) {
    return Mono.just(false);
  }
}
