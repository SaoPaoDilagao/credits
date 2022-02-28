package com.nttdata.credits.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.credits.entity.Credit;
import com.nttdata.credits.repository.CreditRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {
	
	@Autowired 
	private CreditRepository creditRepository;


	@Override
	public void createCredit(Credit credit) {
		 credit.setActive(true);
		 creditRepository.save(credit).subscribe();
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
		return creditRepository.save(account);
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
}
