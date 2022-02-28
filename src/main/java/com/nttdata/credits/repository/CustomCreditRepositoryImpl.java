package com.nttdata.credits.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.nttdata.credits.entity.Credit;

import reactor.core.publisher.Flux;


public class CustomCreditRepositoryImpl implements CustomCreditRepository {
	
	@Autowired
	private  ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Credit> findByClientFirstNameAndLastName(String firstName,
                                                          String lastName) {
        Query query = new Query(where("client.firstName").is(firstName)
                .and("client.lastName").is(lastName));
        return mongoTemplate.find(query, Credit.class);
    }

    @Override
    public Flux<Credit> findByClientDocumentNumber(String documentNumber) {
        Query query = new Query(where("client.documentNumber").is(documentNumber));
        return mongoTemplate.find(query, Credit.class);
    }

}
