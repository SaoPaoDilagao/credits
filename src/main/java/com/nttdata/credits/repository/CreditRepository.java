package com.nttdata.credits.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.credits.entity.Credit;

import reactor.core.publisher.Mono;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, ObjectId>, CustomCreditRepository {
    Mono<Credit> findByNumber(String number);
    
}
