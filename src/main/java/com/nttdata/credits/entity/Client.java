package com.nttdata.credits.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nttdata.credits.dto.request.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 * Client object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  private String documentNumber;
  private String firstName;
  private String lastName;
  private int type;
  private int profile;

  /**
   * Return client from an ClientRequest.
   *
   * @param request ClientRequest object
   */
  public Client(ClientRequest request) {
    id = new ObjectId(request.getId());
    documentNumber = request.getDocumentNumber();
    firstName = request.getFirstName();
    lastName = request.getLastName();
    type = request.getType();
    profile = request.getProfile();
  }
}
