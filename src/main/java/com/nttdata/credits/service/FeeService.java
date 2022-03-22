package com.nttdata.credits.service;



import com.nttdata.credits.dto.request.FeeRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account service interface.
 */
public interface FeeService {
	Flux<Void> createFees(FeeRequest feeRequest);
	
	Mono<Boolean> checkFeesExpired(String productNumber);
  
}
