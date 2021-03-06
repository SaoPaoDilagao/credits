package com.nttdata.credits.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nttdata.credits.dto.request.CreditRequest;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * Credit object.
 */
@Document("credit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credit {
  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  private String creditCard;
  private String number;
  private Client client;
  private int type;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal creditTotal;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal creditBalance;
  private int monthlyFeeExpirationDay;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal percentageInterestRate;
  private int numberOfFees;
  private boolean active;

  /**
   * Return credit from an CreditRequest.
   *
   * @param request CreditRequest object
   */
  public Credit(CreditRequest request) {
    creditCard = request.getCreditCard();
    number = request.getNumber();
    client = new Client(request.getClient());
    type = request.getType();
    creditTotal = request.getCreditTotal();
    creditBalance = BigDecimal.ZERO;
    monthlyFeeExpirationDay = request.getMonthlyFeeExpirationDay();
    percentageInterestRate = request.getPercentageInterestRate();
    numberOfFees = request.getNumberOfFees();
    active = true;
  }
}
