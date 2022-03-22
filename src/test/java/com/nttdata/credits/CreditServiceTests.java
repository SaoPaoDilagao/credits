package com.nttdata.credits;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nttdata.credits.dto.response.CreditCardFeesData;
import com.nttdata.credits.entity.Client;
import com.nttdata.credits.entity.Credit;
import com.nttdata.credits.repository.CreditRepository;
import com.nttdata.credits.service.CreditServiceImpl;
import com.nttdata.credits.service.FeeServiceImpl;
import com.nttdata.credits.utilities.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class CreditServiceTests {
	
	@Mock
	private CreditRepository creditRepository;
	
	@Mock
	private FeeServiceImpl feeService;
	
	@InjectMocks
	private CreditServiceImpl creditServiceImpl;
	
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
		
		credit_1.setId(new ObjectId());
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setMonthlyFeeExpirationDay(27);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		credit_2.setId(new ObjectId());
		credit_2.setClient(client);
		credit_2.setType(Constants.CreditType.CARD);
		credit_2.setNumber("00002");
		credit_2.setCreditTotal(new BigDecimal(5000));
		credit_2.setCreditBalance(BigDecimal.ZERO);
		credit_2.setNumberOfFees(6);
		credit_2.setMonthlyFeeExpirationDay(27);
		credit_2.setPercentageInterestRate(new BigDecimal(5.0));
		credit_2.setActive(true);
		credit_2.setCreditCard("00002");
		
		Flux<Credit> dummyFlux = Flux.just(credit_1, credit_2);
		
		String documentNumber = "0123456";
		
		when(creditRepository.findByClientDocumentNumber(documentNumber)).thenReturn(dummyFlux);
		
		var result = creditServiceImpl.findCreditByClientDocumentNumber(documentNumber);
		
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_1)
			.expectNext(credit_2)
			.verifyComplete();
	}
	
	@Test
	public void testFindCreditByNumber() {
		
		Credit credit_1 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_1.setId(new ObjectId());
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setMonthlyFeeExpirationDay(27);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		String number = "00001";
		
		when(creditRepository.findByNumber(number)).thenReturn(Mono.just(credit_1));
		
		var result = creditServiceImpl.findCreditByNumber(number);
		
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_1)
			.verifyComplete();
	}
	
	@Test
	public void testFindCreditById() {
		
		Credit credit_1 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_1.setId(new ObjectId());
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setMonthlyFeeExpirationDay(27);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		String id = "62267fbb0831ec49ca9dfb41";
		
		given(creditRepository.findById(new ObjectId(id))).willReturn(Mono.just(credit_1));
		
		var result = creditServiceImpl.findCreditById(id);
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_1)
			.verifyComplete();
	}
	
	@Test
	public void testFindCreditByClientFirstNameAndLastName() {
		Credit credit_1 = new Credit();
		Credit credit_2 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_1.setId(new ObjectId());
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setMonthlyFeeExpirationDay(27);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		credit_2.setId(new ObjectId());
		credit_2.setClient(client);
		credit_2.setType(Constants.CreditType.CARD);
		credit_2.setNumber("00002");
		credit_2.setCreditTotal(new BigDecimal(5000));
		credit_2.setCreditBalance(BigDecimal.ZERO);
		credit_2.setNumberOfFees(6);
		credit_2.setMonthlyFeeExpirationDay(27);
		credit_2.setPercentageInterestRate(new BigDecimal(5.0));
		credit_2.setActive(true);
		credit_2.setCreditCard("00002");
		
		Flux<Credit> dummyFlux = Flux.just(credit_1, credit_2);
		
		String firstName = "Pedro";
		String lastName = "Sanchez";
		
		when(creditRepository.findByClientFirstNameAndLastName(firstName,lastName)).thenReturn(dummyFlux);
		
		var result = creditServiceImpl.findCreditByClientFirstNameAndLastName(firstName,lastName);
		
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_1)
			.expectNext(credit_2)
			.verifyComplete();
	}
	
	@Test
	public void testFindIfClientOwnsCreditCard() {
		
		Credit credit_2 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_2.setId(new ObjectId());
		credit_2.setClient(client);
		credit_2.setType(Constants.CreditType.CARD);
		credit_2.setNumber("00002");
		credit_2.setCreditTotal(new BigDecimal(5000));
		credit_2.setCreditBalance(BigDecimal.ZERO);
		credit_2.setNumberOfFees(6);
		credit_2.setMonthlyFeeExpirationDay(27);
		credit_2.setPercentageInterestRate(new BigDecimal(5.0));
		credit_2.setActive(true);
		credit_2.setCreditCard("00002");
		
		String documentNumber = "0123456";
		
		given(creditRepository.findByClientDocumentNumberAndCreditType(documentNumber,Constants.CreditType.CARD))
			.willReturn(Mono.just(credit_2));
			
		var result = creditServiceImpl.findIfClientOwnsCreditCard(documentNumber);
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_2)
			.verifyComplete();
		
	}
	
	@Test
	public void testGetCreditCardBalance()
	{
		Credit credit_2 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_2.setId(new ObjectId());
		credit_2.setClient(client);
		credit_2.setType(Constants.CreditType.CARD);
		credit_2.setNumber("00002");
		credit_2.setCreditTotal(new BigDecimal(5000));
		credit_2.setCreditBalance(BigDecimal.ZERO);
		credit_2.setNumberOfFees(6);
		credit_2.setMonthlyFeeExpirationDay(27);
		credit_2.setPercentageInterestRate(new BigDecimal(5.0));
		credit_2.setActive(true);
		credit_2.setCreditCard("00002");
		
		String number = "00002";
		
		given(creditRepository.findByNumber(number)).willReturn(Mono.just(credit_2));
		
		var result = creditServiceImpl.getCreditCardBalance(number);
		
		StepVerifier
		.create(result)
		.expectSubscription()
		.consumeNextWith( fee ->{
			Assertions.assertNotNull(fee);
			Assertions.assertEquals(fee, credit_2.getCreditBalance());
		})
		.verifyComplete();
	}
	
	@Test
	public void testGetCreditCardFeesData() {
		
		Credit credit_2 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_2.setId(new ObjectId());
		credit_2.setClient(client);
		credit_2.setType(Constants.CreditType.CARD);
		credit_2.setNumber("00002");
		credit_2.setCreditTotal(new BigDecimal(5000));
		credit_2.setCreditBalance(BigDecimal.ZERO);
		credit_2.setNumberOfFees(6);
		credit_2.setMonthlyFeeExpirationDay(27);
		credit_2.setPercentageInterestRate(new BigDecimal(5.0));
		credit_2.setActive(true);
		credit_2.setCreditCard("00002");
		
		CreditCardFeesData data = new CreditCardFeesData();
		data.setMonthlyFeeExpirationDay(27);
		data.setNumberOfFees(6);
		data.setPercentageInterestRate(new BigDecimal(5.0));
		
		String number = "00002";
		
		given(creditRepository.findByNumber(number)).willReturn(Mono.just(credit_2));
		
		var result = creditServiceImpl.getCreditCardFeesData(number);
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(data)
			.verifyComplete();	
	}
	
	@Test
	public void testCreateCredit() {
		
		Credit credit_1 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_1.setId(new ObjectId());
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setMonthlyFeeExpirationDay(27);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		given(creditRepository.findByNumber(any())).willReturn(Mono.empty());
		
		given(creditRepository.countByClientDocumentNumber(any())).willReturn(Mono.just(0L));
		
		given(creditRepository.save(any())).willReturn(Mono.just(credit_1));
		
		given(feeService.createFees(any())).willReturn(Flux.empty());
		
		var result = creditServiceImpl.createCredit(credit_1);
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_1)
			.verifyComplete();	
	}
	
	@Test
	public void testUpdateCreditBalance() {
		
		String id = "621dfddf0f776c0f58c3eb5b";
		BigDecimal amount = new BigDecimal(250);
		
		Credit credit_1 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_1.setId(new ObjectId("621dfddf0f776c0f58c3eb5b"));
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setMonthlyFeeExpirationDay(27);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		
		given(creditRepository.findById(new ObjectId(id))).willReturn(Mono.just(credit_1));
		
		credit_1.setCreditBalance(credit_1.getCreditBalance().add(amount));
		
		given(creditRepository.save(credit_1)).willReturn(Mono.just(credit_1));
		
		var result = creditServiceImpl.updateCreditBalance(id,amount);
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_1)
			.verifyComplete();	
	}
	
	@Test
	public void testUpdateCreditBalance_Card() {
		
		String id = "621dfddf0f776c0f58c3eb5b";
		BigDecimal amount = new BigDecimal(250);
		
		Credit credit_2 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_2.setId(new ObjectId());
		credit_2.setClient(client);
		credit_2.setType(Constants.CreditType.CARD);
		credit_2.setNumber("00002");
		credit_2.setCreditTotal(new BigDecimal(5000));
		credit_2.setCreditBalance(BigDecimal.ZERO);
		credit_2.setNumberOfFees(6);
		credit_2.setMonthlyFeeExpirationDay(27);
		credit_2.setPercentageInterestRate(new BigDecimal(5.0));
		credit_2.setActive(true);
		credit_2.setCreditCard("00002");
		
		
		given(creditRepository.findById(new ObjectId(id))).willReturn(Mono.just(credit_2));
		
		credit_2.setCreditBalance(credit_2.getCreditBalance().add(amount));
		
		given(creditRepository.save(credit_2)).willReturn(Mono.just(credit_2));
		
		var result = creditServiceImpl.updateCreditBalance(id,amount);
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_2)
			.verifyComplete();	
	}
	
	
	
	
	
	@Test
	public void testUpdate() {
		
		Credit credit_1 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_1.setId(new ObjectId("621dfddf0f776c0f58c3eb5b"));
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setMonthlyFeeExpirationDay(27);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		given(creditRepository.findById(credit_1.getId())).willReturn(Mono.just(credit_1));
		
		credit_1.setMonthlyFeeExpirationDay(28);
		
		given(creditRepository.save(credit_1)).willReturn(Mono.just(credit_1));
		
		var result = creditServiceImpl.update(credit_1);
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_1)
			.verifyComplete();	
	}
	
	@Test
	public void testDelete() {
		Credit credit_1 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_1.setId(new ObjectId("621dfddf0f776c0f58c3eb5b"));
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setMonthlyFeeExpirationDay(27);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		given(creditRepository.findById(credit_1.getId())).willReturn(Mono.just(credit_1));
		
		credit_1.setActive(false);
		
		given(creditRepository.save(credit_1)).willReturn(Mono.just(credit_1));
		
		var result = creditServiceImpl.delete(credit_1.getId().toString());
		
		StepVerifier
			.create(result)
			.expectSubscription()
			.expectNext(credit_1)
			.verifyComplete();	
		
	}
	
	@Test
	public void testGetMonthlyFeeExpirationDate() {
		
		Credit credit_1 = new Credit();
		Client client = new Client();
		
		client.setId(new ObjectId("62267fbb0831ec49ca9dfb41"));
		client.setFirstName("Pedro");
		client.setLastName("Sanchez");
		client.setDocumentNumber("0123456");
		client.setType(Constants.CreditType.PERSONAL);
		client.setProfile(Constants.ClientProfile.REGULAR);
		
		credit_1.setId(new ObjectId("621dfddf0f776c0f58c3eb5b"));
		credit_1.setClient(client);
		credit_1.setType(Constants.CreditType.PERSONAL);
		credit_1.setNumber("00001");
		credit_1.setCreditTotal(new BigDecimal(5000));
		credit_1.setCreditBalance(BigDecimal.ZERO);
		credit_1.setNumberOfFees(12);
		credit_1.setMonthlyFeeExpirationDay(27);
		credit_1.setPercentageInterestRate(new BigDecimal(15.0));
		credit_1.setActive(true);
		credit_1.setCreditCard("-");
		
		int testMonthlyFeeExpirationDate = 27;
		
		given(creditRepository.findByNumber(credit_1.getNumber())).willReturn(Mono.just(credit_1));
		
		var result = creditServiceImpl.getMonthlyFeeExpirationDate(credit_1.getNumber());
		
		StepVerifier
			.create(result)
			.consumeNextWith( day ->{
				Assertions.assertNotNull(day);
				Assertions.assertEquals(day, testMonthlyFeeExpirationDate);
			})
			.verifyComplete();
		
	}
	
	/*@Test
	public void testcCheckIfClientHasDebs() {
		
	}*/
	

}
