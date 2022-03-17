package com.nttdata.credits.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardFeesData {
	
	private int monthlyFeeExpirationDay;
	private BigDecimal percentageInterestRate;
	private Integer numberOfFees;
}
