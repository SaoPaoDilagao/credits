package com.nttdata.credits.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.credits.dto.response.Client;
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
  private ObjectId id;
  private Client client;
  private String number;
  private boolean active;
  private int type;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal creditTotal;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal creditBalance;
}
