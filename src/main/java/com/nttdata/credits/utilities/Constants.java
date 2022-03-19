package com.nttdata.credits.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
  /**
   * Client types.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class ClientType {
    public static final int PERSONAL = 1;
    public static final int BUSINESS = 2;
  }

  /**
   * Credit types.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class CreditType {
    public static final int PERSONAL = 1;
    public static final int BUSINESS = 2;
    public static final int CARD = 3;
  }

  /**
   * Credit error message.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class CreditErrorMsg {
    public static final String FLUX_NOT_FOUND_MESSAGE = "Data not found";
    public static final String MONO_NOT_FOUND_MESSAGE = "Credit not found";
    public static final String MONO_NOT_CREDIT_CARD =
        "The number does not correspond to a Credit Card";
  }
  
  /**
   * Client profiles.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class ClientProfile {
    public static final int REGULAR = 1;
    public static final int VIP = 2;
    public static final int PYME = 3;
  }
}
