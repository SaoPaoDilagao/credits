package com.nttdata.credits.dto.request;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Credit object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequest {
  @NotBlank(message = "Field creditCard must be required")
  private String creditCard;
  @NotBlank(message = "Field number must be required")
  private String number;
  @Valid
  private ClientRequest client;
  @NotNull(message = "Field type must be required")
  private Integer type;
  @NotNull(message = "Field creditTotal must be required")
  private BigDecimal creditTotal;
  @NotNull(message = "Field monthlyFeeExpirationDay must be required")
  private int monthlyFeeExpirationDay;
  @NotNull(message = "Field percentageInterestRate must be required")
  private BigDecimal percentageInterestRate;
  // It only applies to credits such as loans, not credit cards.
  private Integer numberOfFees;
}
