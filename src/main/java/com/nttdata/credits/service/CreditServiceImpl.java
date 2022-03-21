package com.nttdata.credits.service;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.credits.dto.request.FeeRequest;
import com.nttdata.credits.dto.response.CreditCardFeesData;
import com.nttdata.credits.entity.Credit;
import com.nttdata.credits.exceptions.CustomInformationException;
import com.nttdata.credits.exceptions.CustomNotFoundException;
import com.nttdata.credits.repository.CreditRepository;
import com.nttdata.credits.utilities.Constants;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Credit service implementation.
 */
@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
  private static final Logger logger_consola = LoggerFactory.getLogger(CreditServiceImpl.class);

  private final FeeService feeService;
  
  private final CreditRepository creditRepository;
  
  @Override
  public Mono<Credit> createCredit(Credit credit) {
	  
    return creditRepository.findByNumber(credit.getNumber())
        .doOnNext(a -> {
          Mono.error(new CustomInformationException("Credit number has already been created")) ;
        })
        .switchIfEmpty(creditRepository
            .countByClientDocumentNumber(credit.getClient().getDocumentNumber())
            .map(a -> {
              if (credit.getClient().getType() == Constants.ClientType.PERSONAL && a > 0) {
                return Mono.error( new CustomInformationException("The client type allows "
                    + "to have only 1 credits"));
              } else {
                return a;
              }
            })
            .then(Mono.just(credit))
            .flatMap(a -> creditRepository.save(a)
                .map(b -> {
                	
                  // if the credit is a loan (no credit card) is necessary 
                  // to create the quotas to be paid
                  if(credit.getType() != Constants.CreditType.CARD) {
                	  FeeRequest request = new FeeRequest();
                	  request.setIdTransaction(b.getId().toString());
                	  request.setClientDocumentNumber(b.getClient().getDocumentNumber());
                	  request.setProductNumber(b.getNumber());
                	  request.setAmount(b.getCreditTotal());
                	  request.setMonthlyFeeExpirationDay(b.getMonthlyFeeExpirationDay());
                	  request.setPercentageInterestRate(b.getPercentageInterestRate());
                	  request.setNumberOfFees(b.getNumberOfFees());
                	  
                	  feeService.createFees(request);
                  }
                  
                  logger_consola
                      .info("Created a new credit with id= {} for the client "
                              + "with document number= {}",
                              credit.getId(), credit.getClient().getDocumentNumber());
                  return b;
                })));
  }

  @Override
  public Mono<Credit> findCreditById(String id) {
    return creditRepository.findById(new ObjectId(id))
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Flux<Credit> findCreditByClientFirstNameAndLastName(String firstName, String lastName) {
    return creditRepository.findByClientFirstNameAndLastName(firstName, lastName)
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.FLUX_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Flux<Credit> findCreditByClientDocumentNumber(String documentNumber) {
    return creditRepository.findByClientDocumentNumber(documentNumber)
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.FLUX_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Mono<Credit> findCreditByNumber(String number) {
    return creditRepository.findByNumber(number)
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Mono<Credit> update(Credit credit) {
    return creditRepository.findById(credit.getId())
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)))
        .map(data -> {
          creditRepository.save(credit).subscribe();
          logger_consola.info("Updated the client with id= {}", credit.getId());

          return data;
        });
  }

  @Override
  public Mono<Credit> delete(String id) {
    return creditRepository.findById(new ObjectId(id))
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)))
        .map(credit -> {
          credit.setActive(false);
          creditRepository.save(credit).subscribe();
          logger_consola.info("Deleted the client with id= {}", credit.getId());

          return credit;
        });
  }

  @Override
  public Mono<Credit> updateCreditBalance(String id, BigDecimal amount) {
    return creditRepository.findById(new ObjectId(id))
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)))
        .map(credit -> {
          if (credit.getType() == Constants.CreditType.CARD) {
            // in case of CreditType.CARD withdrawal and deposit are
            // admitted so amount can be negative and positive respectively
            // and the balance don't exceed the total credit.

            BigDecimal temp = new BigDecimal(0);
            temp = temp.add(credit.getCreditBalance());

            if (amount.compareTo(BigDecimal.ZERO) > 0) {
              temp = temp.subtract(amount);  // deposit
            } else {
              temp = temp.add(amount.abs()); // withdrawal
            }

            if (temp.compareTo(credit.getCreditTotal()) <= 0) {
              credit.setCreditBalance(new BigDecimal(0));
              credit.setCreditBalance(credit.getCreditBalance().add(temp));
            } else {
              // if the limit is exceeded the card is deactivated
              credit.setActive(false);
            }
          } else {
            // in case of CreditType.PERSONAL and CreditType BUSINESS (loan)
            // only deposits are permitted so amount can be only positive
            credit.setCreditBalance(credit.getCreditBalance().add(amount));

            if (credit.getCreditBalance().compareTo(credit.getCreditTotal()) == 0) {
              // when the loan has been paid totally, the loan is deactivated
              credit.setActive(false);
            }
          }

          creditRepository.save(credit).subscribe();
          logger_consola.info("The balance of the credit with id= {} was updated to {}",
              credit.getId(), credit.getCreditBalance());

          return credit;
        });
  }

  @Override
  public Mono<Credit> findIfClientOwnsCreditCard(String documentNumber) {
    return creditRepository
        .findByClientDocumentNumberAndCreditType(documentNumber, Constants.CreditType.CARD)
        .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<BigDecimal> getCreditCardBalance(String number) {
    Mono<Credit> result = creditRepository.findByNumber(number)
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)));

    return result.flatMap(credit -> {
      if (credit.getType() == Constants.CreditType.CARD) {
        return Mono.just(credit.getCreditBalance());
      } else {
        return Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_CREDIT_CARD));
      }
    });
  }
	
	@Override
	public Mono<Integer> getMonthlyFeeExpirationDate(String number) {
		Mono<Credit> result = creditRepository.findByNumber(number)
		        .switchIfEmpty(Mono
		            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)));

	    return result.flatMap(credit -> {
	     
	        return Mono.just(credit.getMonthlyFeeExpirationDay());
	      
	    });
	}

	@Override
	public Mono<CreditCardFeesData> getCreditCardFeesData(String number) {
		Mono<Credit> result = creditRepository.findByNumber(number)
				.switchIfEmpty(Mono
			            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)));
		
		return result.flatMap( item ->{
			if(item.getType() != Constants.CreditType.CARD) {
				return Mono.error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE));
			} else {
				CreditCardFeesData data = new CreditCardFeesData();
				
				data.setMonthlyFeeExpirationDay(item.getMonthlyFeeExpirationDay());
				data.setPercentageInterestRate(item.getPercentageInterestRate());
				data.setNumberOfFees(item.getNumberOfFees());
				
				return Mono.just(data);
			}
		});
	}

	@Override
	public Mono<Boolean> checkIfClientHasDebs(String documentNumber) {
			/*return findCreditByClientDocumentNumber(documentNumber)
			.flatMap( item ->{
				 item.getNumber()
				
				
				
				
				
			})*/
			
			return Mono.just(false);
		
	}
}
