package com.nttdata.credits.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Client object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
  private String id;
  private String firstName;
  private String lastName;
  private String documentNumber;
  private int profile;
  private int type;
  private boolean active;
}
