package utils;

import model.CurrencyType;

public class AccountUtil {

    public static boolean isValidId(String accountId){
       if (accountId.length() != ApplicationConst.ACCOUNT_NAME_LENGHT){
           return false;
       }
       if(!accountId.substring(0,2).equals(ApplicationConst.ACCOUNT_NAME_PREFIX)){
           return false;
       }
        return true;
    }

    public static boolean isValidCurrencyType (String currencyType){
        if (currencyType.equalsIgnoreCase("RON") ||
            currencyType.equalsIgnoreCase("EUR")){
            return true;
        }
        return false;
    }

    public static CurrencyType getCurrencyType(String currencyStr){
        if (currencyStr.equalsIgnoreCase("RON")){
           return CurrencyType.RON;
        }
        if (currencyStr.equalsIgnoreCase("EUR")){
            return CurrencyType.EUR;
        }
        return CurrencyType.NO_CURRENCY;
    }
}
