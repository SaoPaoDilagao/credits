package com.nttdata.credits.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.credits.dto.request.FeeRequest;
import com.nttdata.credits.dto.response.FeeResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FeeServiceImpl implements FeeService{
	
	@Value("${backend.service.fees}")
	  private String urlFees;

	@Autowired
	@Qualifier("wcLoadBalanced")
	private WebClient.Builder webClient;

	@Override
	public Flux<Void> createFees(FeeRequest feeRequest) {
		
		webClient
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        	.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
	        .build()
	        .post()
	        .uri(urlFees + "/create")
			.body(Mono.just(feeRequest), FeeRequest.class)
	        .retrieve()
	        .bodyToFlux(FeeResponse.class)
	        .subscribe();
		
		
		return null;
	}

}
