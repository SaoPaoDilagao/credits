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
  @NotBlank(message = "Field type must be required")
  private Integer type;
  @NotNull(message = "Field creditBalance must be required")
  private BigDecimal creditBalance;
}
