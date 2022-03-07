package com.nttdata.credits.entity;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String documentNumber;
	private int profile; 
    private int type;
    private boolean active;
}
