package com.nttdata.credits.service;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.credits.Constants;
import com.nttdata.credits.entity.Credit;
import com.nttdata.credits.repository.CreditRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {
	
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
									//logger_file.debug("Created a new id= {} for the client with document number= {}", client.getId(), client.getDocumentNumber());
									//logger_consola.info("Created a new id= {} for the client with document number= {}", client.getId(), client.getDocumentNumber());
									return x;}
								);
			 return credits.next();
		 
		 } else { //  ClientType.BUSINESS allows more than a credit  
		 
			 return creditRepository.save(credit);
		 }
		 
		 // I don't know how to return a Mono.empty() when a ClientType.PERSONAL has had a credit yet.
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
	public Mono<Credit> update(Credit account) {
		
		return creditRepository.findById(account.getId())
                .map(data -> {
                    
                	creditRepository.save(data).subscribe();
                    
                    //logger_file.debug("Updated the client with id= {}", client.getId());
            		//logger_consola.info("Updated the client with id= {}", client.getId());
                    
                    return data;
                });
		
	}

	@Override
	public Mono<Credit> delete(String id) {
		return creditRepository.findById(new ObjectId(id))
                .map(credit -> {
                	credit.setActive(false);
                	creditRepository.save(credit).subscribe();
                    return credit;
                });
	}


	@Override
	public Mono<Credit> updateCreditBalance(String id, BigDecimal amount) {
		
		return creditRepository.findById(new ObjectId(id))
				.map( credit -> {
					if(credit.getType() == Constants.CreditType.CARD) {
						
						// in case of CreditType.CARD withdrawal and deposit are 
						// admited so amount can be negative and  positive respectively
						// and the balance don't exceed the total credit.
						
						BigDecimal temp = new BigDecimal(0);
						temp.add(credit.getCredit_balance());
						
						if(amount.compareTo(BigDecimal.ZERO) >0)
							temp.subtract(amount);  // deposit
						else
							temp.add(amount.abs()); // withdrawal
							
						if(temp.compareTo(credit.getCredit_total()) < 0) {
							
							credit.setCredit_balance(new BigDecimal(0));
							credit.getCredit_balance().add(amount);
						
						} else {
							
							// to manage when overflow the card limit 
							
							
							return credit;
						}
						
					} else { 
						
						// in case of CreditType.PERSONAL and CreditType BUSINESS (loan) 
						// only payments are admited so amount can be only positive
						
						credit.getCredit_balance().add(amount);
						
						if(credit.getCredit_balance().compareTo(credit.getCredit_total()) == 0)
						{
							// all credit has been paid 
							// set the active = false
							credit.setActive(false);
						}
						
					}
					
					creditRepository.save(credit).subscribe();
					return credit;
				});
		
	}
}
