package com.nttdata.credits.repository;

import com.nttdata.credits.entity.Credit;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Credit repository.
 */
@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, ObjectId>,
    CustomCreditRepository {
  Mono<Credit> findByNumber(String number);

  Mono<Long> countByClientDocumentNumber(String documentNumber);
}
