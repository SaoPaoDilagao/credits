package com.nttdata.credits;

import static org.mockito.Mockito.when;

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
	public void testFindByClientDocumentNumber() {
		Credit credit_1 = new Credit();
		Credit credit_2 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		//credit_1.setId(new ObjectId("621dfddf0f776c0f58c3eb5b"));
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		//credit_2.setId(new ObjectId("621e01850f776c0f58c3eb5c"));
		credit_2.setClient(client);
		credit_2.setType(Constants.CreditType.CARD);
		credit_2.setNumber("00002");
		credit_2.setCreditTotal(new BigDecimal(5000));
		credit_2.setCreditBalance(BigDecimal.ZERO);
		credit_2.setNumberOfFees(6);
		credit_2.setPercentageInterestRate(new BigDecimal(5.0));
		credit_2.setActive(true);
		credit_2.setCreditCard("00002");
		
		Flux<Credit> dummyFlux = Flux.just(credit_1, credit_2);
		
		String documentNumber = "0123456";
		
		when(creditService.findCreditByClientDocumentNumber(documentNumber)).thenReturn(dummyFlux);
		
		var responseBody = webTestClient.get().uri("/credits/client/documentNumber/{documentNumber}",documentNumber)
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
	public void testSearchCreditByNumber() {
		
		Credit credit_1 = new Credit();	
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		//credit_1.setId(new ObjectId("621dfddf0f776c0f58c3eb5b"));
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		String number = "00001";
		
		when(creditService.findCreditByNumber(number)).thenReturn(Mono.just(credit_1));
		
		var responseBody = webTestClient.get().uri("/credits/number/{number}",number)
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
	public void testFindById() {
		
		Credit credit_1 = new Credit();	
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		//credit_1.setId(new ObjectId("621dfddf0f776c0f58c3eb5b"));
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		String id = "62267fbb0831ec49ca9dfb41";
		
		when(creditService.findCreditById(id)).thenReturn(Mono.just(credit_1));
		
		var responseBody = webTestClient.get().uri("/credits/id/{id}",id)
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
	public void testFindByCreditFirstNameAndLastName() {
		
		Credit credit_1 = new Credit();
		Credit credit_2 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		//credit_1.setId(new ObjectId("621dfddf0f776c0f58c3eb5b"));
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		//credit_2.setId(new ObjectId("621e01850f776c0f58c3eb5c"));
		credit_2.setClient(client);
		credit_2.setType(Constants.CreditType.CARD);
		credit_2.setNumber("00002");
		credit_2.setCreditTotal(new BigDecimal(5000));
		credit_2.setCreditBalance(BigDecimal.ZERO);
		credit_2.setNumberOfFees(6);
		credit_2.setPercentageInterestRate(new BigDecimal(5.0));
		credit_2.setActive(true);
		credit_2.setCreditCard("00002");
		
		Flux<Credit> dummyFlux = Flux.just(credit_1, credit_2);
		
		String firstName = "Pedro";
		String lastName = "Sanchez";
		
		when(creditService.findCreditByClientFirstNameAndLastName(firstName,lastName)).thenReturn(dummyFlux);
		
		var responseBody = webTestClient.get()
				.uri("/credits//client/firstName/{firstName}/lastName/{lastName}",firstName,lastName)
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
	public void testCheckIfClientOwnsCreditCard() {
		
		Credit credit_2 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		//credit_2.setId(new ObjectId("621e01850f776c0f58c3eb5c"));
		credit_2.setClient(client);
		credit_2.setType(Constants.CreditType.CARD);
		credit_2.setNumber("00002");
		credit_2.setCreditTotal(new BigDecimal(5000));
		credit_2.setCreditBalance(BigDecimal.ZERO);
		credit_2.setNumberOfFees(6);
		credit_2.setPercentageInterestRate(new BigDecimal(5.0));
		credit_2.setActive(true);
		credit_2.setCreditCard("00002");
		
		String documentNumber = "0123456";
		
		when(creditService.findIfClientOwnsCreditCard(documentNumber)).thenReturn(Mono.just(credit_2));
		
		var responseBody = webTestClient.get().uri("/credits/clientOwnsCard/{documentNumber}",documentNumber)
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
	public void testGetCreditCardBalance() {
		
		String number = "00002";
		
		BigDecimal balance = new BigDecimal(5000);
		
		when(creditService.getCreditCardBalance(number)).thenReturn(Mono.just(balance));
		
		var responseBody = webTestClient.get().uri("/credits/getBalance/{number}",number)
				.exchange()
				.expectStatus().isOk()
				.returnResult(BigDecimal.class)
				.getResponseBody();
		
		StepVerifier.create(responseBody)
				.expectSubscription()
				.consumeNextWith( fee ->{
					Assertions.assertNotNull(fee);
					Assertions.assertEquals(fee, balance);
				})
				.verifyComplete();
		
	}
	
	
	

	

}
