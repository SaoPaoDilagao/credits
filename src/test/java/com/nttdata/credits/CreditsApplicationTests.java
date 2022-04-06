package com.nttdata.credits;

import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.credits.controller.CreditController;
import com.nttdata.credits.dto.request.ClientRequest;
import com.nttdata.credits.dto.request.CreditRequest;
import com.nttdata.credits.dto.response.CreditCardFeesData;
import com.nttdata.credits.entity.Client;
import com.nttdata.credits.entity.Credit;
import com.nttdata.credits.service.CreditService;
import com.nttdata.credits.utilities.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CreditController.class)
class CreditsApplicationTests {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private CreditService creditService;

  @Test
  void testFindByClientDocumentNumber() {
    Credit credit_1 = new Credit();
    Credit credit_2 = new Credit();
    Client client = new Client();

    client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
    client.setFirstName("Pedro");
    client.setLastName("Sanchez");
    client.setDocumentNumber("0123456");
    client.setType(Constants.CreditType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    //credit_1.setId(new ObjectId());
    credit_1.setClient(client);
    credit_1.setType(Constants.CreditType.PERSONAL);
    credit_1.setNumber("00001");
    credit_1.setCreditTotal(new BigDecimal(5000));
    credit_1.setCreditBalance(BigDecimal.ZERO);
    credit_1.setNumberOfFees(12);
    credit_1.setMonthlyFeeExpirationDay(27);
    credit_1.setPercentageInterestRate(new BigDecimal("15.0"));
    credit_1.setActive(true);
    credit_1.setCreditCard("-");

    //credit_2.setId(new ObjectId());
    credit_2.setClient(client);
    credit_2.setType(Constants.CreditType.CARD);
    credit_2.setNumber("00002");
    credit_2.setCreditTotal(new BigDecimal(5000));
    credit_2.setCreditBalance(BigDecimal.ZERO);
    credit_2.setNumberOfFees(6);
    credit_2.setMonthlyFeeExpirationDay(27);
    credit_2.setPercentageInterestRate(new BigDecimal("5.0"));
    credit_2.setActive(true);
    credit_2.setCreditCard("00002");

    Flux<Credit> dummyFlux = Flux.just(credit_1, credit_2);

    String documentNumber = "0123456";

    when(creditService.findCreditByClientDocumentNumber(documentNumber)).thenReturn(dummyFlux);

    var responseBody = webTestClient.get().uri("/client/documentNumber/{documentNumber}", documentNumber)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Credit.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(credit_1)
        .expectNext(credit_2)
        .verifyComplete();
  }

  @Test
  void testSearchCreditByNumber() {

    Credit credit_1 = new Credit();
    Client client = new Client();

    client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
    client.setFirstName("Pedro");
    client.setLastName("Sanchez");
    client.setDocumentNumber("0123456");
    client.setType(Constants.CreditType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    //credit_1.setId(new ObjectId());
    credit_1.setClient(client);
    credit_1.setType(Constants.CreditType.PERSONAL);
    credit_1.setNumber("00001");
    credit_1.setCreditTotal(new BigDecimal(5000));
    credit_1.setCreditBalance(BigDecimal.ZERO);
    credit_1.setNumberOfFees(12);
    credit_1.setMonthlyFeeExpirationDay(27);
    credit_1.setPercentageInterestRate(new BigDecimal("15.0"));
    credit_1.setActive(true);
    credit_1.setCreditCard("-");

    String number = "00001";

    when(creditService.findCreditByNumber(number)).thenReturn(Mono.just(credit_1));

    var responseBody = webTestClient.get().uri("/number/{number}", number)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Credit.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(credit_1)
        .verifyComplete();

  }

  @Test
  void testFindById() {

    Credit credit_1 = new Credit();
    Client client = new Client();

    client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
    client.setFirstName("Pedro");
    client.setLastName("Sanchez");
    client.setDocumentNumber("0123456");
    client.setType(Constants.CreditType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    //credit_1.setId(new ObjectId());
    credit_1.setClient(client);
    credit_1.setType(Constants.CreditType.PERSONAL);
    credit_1.setNumber("00001");
    credit_1.setCreditTotal(new BigDecimal(5000));
    credit_1.setCreditBalance(BigDecimal.ZERO);
    credit_1.setNumberOfFees(12);
    credit_1.setMonthlyFeeExpirationDay(27);
    credit_1.setPercentageInterestRate(new BigDecimal("15.0"));
    credit_1.setActive(true);
    credit_1.setCreditCard("-");

    String id = "62267fbb0831ec49ca9dfb41";

    when(creditService.findCreditById(id)).thenReturn(Mono.just(credit_1));

    var responseBody = webTestClient.get().uri("/id/{id}", id)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Credit.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(credit_1)
        .verifyComplete();

  }

  @Test
  void testFindByCreditFirstNameAndLastName() {

    Credit credit_1 = new Credit();
    Credit credit_2 = new Credit();
    Client client = new Client();

    client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
    client.setFirstName("Pedro");
    client.setLastName("Sanchez");
    client.setDocumentNumber("0123456");
    client.setType(Constants.CreditType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    //credit_1.setId(new ObjectId());
    credit_1.setClient(client);
    credit_1.setType(Constants.CreditType.PERSONAL);
    credit_1.setNumber("00001");
    credit_1.setCreditTotal(new BigDecimal(5000));
    credit_1.setCreditBalance(BigDecimal.ZERO);
    credit_1.setNumberOfFees(12);
    credit_1.setMonthlyFeeExpirationDay(27);
    credit_1.setPercentageInterestRate(new BigDecimal("15.0"));
    credit_1.setActive(true);
    credit_1.setCreditCard("-");

    //credit_2.setId(new ObjectId());
    credit_2.setClient(client);
    credit_2.setType(Constants.CreditType.CARD);
    credit_2.setNumber("00002");
    credit_2.setCreditTotal(new BigDecimal(5000));
    credit_2.setCreditBalance(BigDecimal.ZERO);
    credit_2.setNumberOfFees(6);
    credit_2.setMonthlyFeeExpirationDay(27);
    credit_2.setPercentageInterestRate(new BigDecimal("5.0"));
    credit_2.setActive(true);
    credit_2.setCreditCard("00002");

    Flux<Credit> dummyFlux = Flux.just(credit_1, credit_2);

    String firstName = "Pedro";
    String lastName = "Sanchez";

    when(creditService.findCreditByClientFirstNameAndLastName(firstName, lastName)).thenReturn(dummyFlux);

    var responseBody = webTestClient.get()
        .uri("/client/firstName/{firstName}/lastName/{lastName}", firstName, lastName)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Credit.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(credit_1)
        .expectNext(credit_2)
        .verifyComplete();
  }

  @Test
  void testCheckIfClientOwnsCreditCard() {

    Credit credit_2 = new Credit();
    Client client = new Client();

    client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
    client.setFirstName("Pedro");
    client.setLastName("Sanchez");
    client.setDocumentNumber("0123456");
    client.setType(Constants.CreditType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    //credit_2.setId(new ObjectId());
    credit_2.setClient(client);
    credit_2.setType(Constants.CreditType.CARD);
    credit_2.setNumber("00002");
    credit_2.setCreditTotal(new BigDecimal(5000));
    credit_2.setCreditBalance(BigDecimal.ZERO);
    credit_2.setNumberOfFees(6);
    credit_2.setMonthlyFeeExpirationDay(27);
    credit_2.setPercentageInterestRate(new BigDecimal("5.0"));
    credit_2.setActive(true);
    credit_2.setCreditCard("00002");

    String documentNumber = "0123456";

    when(creditService.findIfClientOwnsCreditCard(documentNumber)).thenReturn(Mono.just(credit_2));

    var responseBody = webTestClient.get().uri("/clientOwnsCard/{documentNumber}", documentNumber)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Credit.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(credit_2)
        .verifyComplete();
  }

  @Test
  void testGetCreditCardBalance() {

    String number = "00002";

    BigDecimal balance = new BigDecimal(5000);

    when(creditService.getCreditCardBalance(number)).thenReturn(Mono.just(balance));

    var responseBody = webTestClient.get().uri("/getBalance/{number}", number)
        .exchange()
        .expectStatus().isOk()
        .returnResult(BigDecimal.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .consumeNextWith(fee -> {
          Assertions.assertNotNull(fee);
          Assertions.assertEquals(fee, balance);
        })
        .verifyComplete();

  }

  @Test
  void testGetCreditCardFeesData() {

    String number = "00002";

    CreditCardFeesData data = new CreditCardFeesData();
    data.setMonthlyFeeExpirationDay(27);
    data.setNumberOfFees(6);
    data.setPercentageInterestRate(new BigDecimal("5.0"));

    when(creditService.getCreditCardFeesData(number)).thenReturn(Mono.just(data));

    var responseBody = webTestClient.get().uri("/getCreditCardFeesData/{number}", number)
        .exchange()
        .expectStatus().isOk()
        .returnResult(CreditCardFeesData.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(data)
        .verifyComplete();

  }


  @Test
  void testCreate() {

    CreditRequest credReq = new CreditRequest();
    ClientRequest cliReq = new ClientRequest();

    cliReq.setId("62267fbb0831ec49ca9dfb41");
    cliReq.setFirstName("Pedro");
    cliReq.setLastName("Sanchez");
    cliReq.setDocumentNumber("0123456");
    cliReq.setType(Constants.CreditType.PERSONAL);
    cliReq.setProfile(Constants.ClientProfile.REGULAR);

    credReq.setClient(cliReq);
    credReq.setType(Constants.CreditType.PERSONAL);
    credReq.setNumber("00001");
    credReq.setCreditTotal(new BigDecimal(5000));
    credReq.setNumberOfFees(12);
    credReq.setMonthlyFeeExpirationDay(27);
    credReq.setPercentageInterestRate(new BigDecimal("15.0"));
    credReq.setCreditCard("-");

    Credit credit = new Credit(credReq);

    when(creditService.createCredit(credit)).thenReturn(Mono.just(credit));

    var responseBody = webTestClient.post().uri("")
        .contentType(APPLICATION_JSON)
        .body(Mono.just(credReq), CreditRequest.class)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Credit.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(credit)
        .verifyComplete();
  }

  @Test
  void testPayCredit() {

    Credit credit_1 = new Credit();
    Client client = new Client();

    client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
    client.setFirstName("Pedro");
    client.setLastName("Sanchez");
    client.setDocumentNumber("0123456");
    client.setType(Constants.CreditType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    //credit_1.setId(new ObjectId());
    credit_1.setClient(client);
    credit_1.setType(Constants.CreditType.PERSONAL);
    credit_1.setNumber("00001");
    credit_1.setCreditTotal(new BigDecimal(5000));
    credit_1.setCreditBalance(new BigDecimal(250));
    credit_1.setNumberOfFees(12);
    credit_1.setMonthlyFeeExpirationDay(27);
    credit_1.setPercentageInterestRate(new BigDecimal("15.0"));
    credit_1.setActive(true);
    credit_1.setCreditCard("-");

    String id = "621dfddf0f776c0f58c3eb5b";
    BigDecimal amount = new BigDecimal(250);

    when(creditService.updateCreditBalance(id, amount)).thenReturn(Mono.just(credit_1));

    var responseBody = webTestClient.put().uri("/balance/{id}/amount/{amount}", id, amount)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Credit.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(credit_1)
        .verifyComplete();

  }

  @Test
  void testUpdate() {

    CreditRequest credReq = new CreditRequest();
    ClientRequest cliReq = new ClientRequest();

    cliReq.setId("62267fbb0831ec49ca9dfb41");
    cliReq.setFirstName("Pedro");
    cliReq.setLastName("Sanchez");
    cliReq.setDocumentNumber("0123456");
    cliReq.setType(Constants.CreditType.PERSONAL);
    cliReq.setProfile(Constants.ClientProfile.REGULAR);

    credReq.setClient(cliReq);
    credReq.setType(Constants.CreditType.PERSONAL);
    credReq.setNumber("00001");
    credReq.setCreditTotal(new BigDecimal(5000));
    credReq.setNumberOfFees(12);
    credReq.setMonthlyFeeExpirationDay(27);
    credReq.setPercentageInterestRate(new BigDecimal("15.0"));
    credReq.setCreditCard("-");

    Credit credit = new Credit(credReq);

    when(creditService.update(credit)).thenReturn(Mono.just(credit));

    var responseBody = webTestClient.put().uri("")
        .contentType(APPLICATION_JSON)
        .body(Mono.just(credReq), CreditRequest.class)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Credit.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(credit)
        .verifyComplete();

  }


  @Test
  void testDelete() {

    Credit credit_1 = new Credit();
    Client client = new Client();

    client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
    client.setFirstName("Pedro");
    client.setLastName("Sanchez");
    client.setDocumentNumber("0123456");
    client.setType(Constants.CreditType.PERSONAL);
    client.setProfile(Constants.ClientProfile.REGULAR);

    //credit_1.setId(new ObjectId());
    credit_1.setClient(client);
    credit_1.setType(Constants.CreditType.PERSONAL);
    credit_1.setNumber("00001");
    credit_1.setCreditTotal(new BigDecimal(5000));
    credit_1.setCreditBalance(new BigDecimal(250));
    credit_1.setNumberOfFees(12);
    credit_1.setMonthlyFeeExpirationDay(27);
    credit_1.setPercentageInterestRate(new BigDecimal("15.0"));
    credit_1.setActive(false);
    credit_1.setCreditCard("-");

    String id = "621dfddf0f776c0f58c3eb5b";

    when(creditService.delete(id)).thenReturn(Mono.just(credit_1));

    var responseBody = webTestClient.delete().uri("/{id}", id)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Credit.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(credit_1)
        .verifyComplete();
  }

  @Test
  void testCheckIfExistFeesExpired() {

    String documentNumber = "0123456";

    Boolean test = true;

    given(creditService.checkIfClientHasDebs(any())).willReturn(Mono.just(test));

    var responseBody = webTestClient.get().uri("/check/debts/{documentNumber}", documentNumber)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Boolean.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(test)
        .verifyComplete();

  }


}
