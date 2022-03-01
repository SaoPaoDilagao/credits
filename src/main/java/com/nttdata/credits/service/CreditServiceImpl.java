package com.nttdata.credits.service;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.credits.Constants;
import com.nttdata.credits.entity.Credit;
import com.nttdata.credits.repository.CreditRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {
	
	private static final Logger logger_consola = LoggerFactory.getLogger("consola");
	private static final Logger logger_file = LoggerFactory.getLogger("clients_log");
	
	@Autowired 
	private CreditRepository creditRepository;


	@Override
	// public void createCredit(Credit credit) {
	public Mono<Credit> createCredit(Credit credit) {
		 credit.setActive(true);
		 
		 //  ClientType.PERSONAL allows only a credit  
		 if(credit.getClient().getType() == Constants.ClientType.PERSONAL) {
		 
			 Flux<Credit> credits = creditRepository.findByClientDocumentNumber(credit.getClient().getDocumentNumber())
						.switchIfEmpty(creditRepository.save(credit))
								.map(x-> {
									logger_file.debug("Created a new credit with id= {} for the client with document number= {}", credit.getId(), credit.getClient().getDocumentNumber());
									logger_consola.info("Created a new credit with id= {} for the client with document number= {}", credit.getId(), credit.getClient().getDocumentNumber());
									return x;}
								);
			 return credits.next();
		 
		 } else { //  ClientType.BUSINESS allows more than a credit  
		 
			 return creditRepository.save(credit);
		 }
		 
		 // TODO how to return a Mono.empty() when a ClientType.PERSONAL has had a credit yet.
	}

	@Override
	public Mono<Credit> findCreditById(String id) {
		return creditRepository.findById(new ObjectId(id));
	}

	@Override
	public Flux<Credit> findCreditByClientFirstNameAndLastName(String firstName, String lastName) {
		return creditRepository.findByClientFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public Flux<Credit> findCreditByClientDocumentNumber(String documentNumber) {
		return creditRepository.findByClientDocumentNumber(documentNumber);
	}

	@Override
	public Mono<Credit> findCreditByNumber(String number) {
		return creditRepository.findByNumber(number);
	}

	@Override
	public Mono<Credit> update(Credit credit) {
		
		return creditRepository.findById(credit.getId())
                .map(data -> {
                    
                	creditRepository.save(credit).subscribe();
                    
                    logger_file.debug("Updated the credit with id= {}", credit.getId());
            		logger_consola.info("Updated the client with id= {}", credit.getId());
                    
                    return data;
                });
		
	}

	@Override
	public Mono<Credit> delete(String id) {
		return creditRepository.findById(new ObjectId(id))
                .map(credit -> {
                	credit.setActive(false);
                	creditRepository.save(credit).subscribe();
                	
                	logger_file.debug("Deleted the credit with id= {}", credit.getId());
            		logger_consola.info("Deleted the client with id= {}", credit.getId());
                    
            		return credit;
                });
	}


	@Override
	public Mono<Credit> updateCreditBalance(String id, BigDecimal amount) {
		
		return creditRepository.findById(new ObjectId(id))
				.map( credit -> {
					
					int type = credit.getType();
					
					if(credit.getType() == Constants.CreditType.CARD) {
						
						// in case of CreditType.CARD withdrawal and deposit are 
						// admitted so amount can be negative and  positive respectively
						// and the balance don't exceed the total credit.
						
						BigDecimal temp = new BigDecimal(0);
						temp = temp.add(credit.getCredit_balance());
						
						if(amount.compareTo(BigDecimal.ZERO) >0)
							temp = temp.subtract(amount);  // deposit
						else
							temp = temp.add(amount.abs()); // withdrawal
							
						if(temp.compareTo(credit.getCredit_total()) <= 0) {
							
							credit.setCredit_balance(new BigDecimal(0));
							credit.setCredit_balance(credit.getCredit_balance().add(temp));
						
						} else {
							
							// if the limit is exceeded the card is deactivated
							credit.setActive(false);
						}
						
					} else { 
						
						// in case of CreditType.PERSONAL and CreditType BUSINESS (loan) 
						// only deposits are permitted so amount can be only positive
						
						credit.setCredit_balance(credit.getCredit_balance().add(amount));
						
						if(credit.getCredit_balance().compareTo(credit.getCredit_total()) == 0)
						{
							// when the loan has been paid totally, the loan is deactivated
							credit.setActive(false);
						}
						
					}
					
					creditRepository.save(credit).subscribe();
					
					logger_file.debug("The balance of the credit with id= {} was updated to {}", credit.getId(), credit.getCredit_balance());
            		logger_consola.info("The balance of the credit with id= {} was updated to {}", credit.getId(), credit.getCredit_balance());
					
					return credit;
				});
	}
}
