package com.nttdata.credits.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.nttdata.credits.entity.Credit;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Custom credit repository implementation.
 */
@RequiredArgsConstructor
public class CustomCreditRepositoryImpl implements CustomCreditRepository {
  
  private final ReactiveMongoTemplate mongoTemplate;

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

  @Override
  public Mono<Credit> findByClientDocumentNumberAndCreditType(String documentNumber,
                                                              Integer creditType) {
    Query query = new Query(where("client.documentNumber").is(documentNumber)
        .and("type").is(creditType));
    return mongoTemplate.findOne(query, Credit.class);
  }
}
