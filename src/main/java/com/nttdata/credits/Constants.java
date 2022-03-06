package com.nttdata.credits;


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
    
    
    public static final class CreditErrorMsg
    {
    	public static  String FLUX_NOT_FOUND_MESSAGE = "Data not found";
    	public static  String MONO_NOT_FOUND_MESSAGE = "Credit not found";
    	public static  String MONO_NOT_CREDIT_CARD = "The number does not correspond to a Credit Card";
    }
    
    
    

}
