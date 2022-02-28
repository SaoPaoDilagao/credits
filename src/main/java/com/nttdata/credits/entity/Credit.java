package com.nttdata.credits.entity;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document()
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credit {
	@Id
    private ObjectId id;
	private Client client;
	private String number;
	private boolean active;
	@Field(targetType = FieldType.DECIMAL128)
    private BigDecimal credit_total;
	@Field(targetType = FieldType.DECIMAL128)
    private BigDecimal credit_remaining;
	@Field(targetType = FieldType.DECIMAL128)
    private BigDecimal debt_paid;
	
}
