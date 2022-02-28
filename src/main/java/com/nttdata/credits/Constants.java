package com.nttdata.credits;

import java.math.BigDecimal;

public class Constants {
	public static final class ClientType
    {
        public static int PERSONAL = 1;
        public static int BUSINESS = 2;
    }

    public static final class CreditType
    {
        public static int PERSONAL = 1;
        public static int BUSINESS = 2;
        public static int CARD = 3;
    }
    
    public static final class CreditLimit
    {
    	public static int CREDIT_PERSONAL_INSTALLMENTS = 12;
    	public static int CREDIT_BUSINESS_INSTALLMENTS = 24;
    	public static int CREDIT_CARD_INSTALLMENTS = 12;
    	public static BigDecimal CREDIT_CARD_MAX_LIMIT = new BigDecimal(2500);
    }
    
    
    

}
