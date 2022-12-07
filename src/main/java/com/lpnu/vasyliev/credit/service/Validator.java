package com.lpnu.vasyliev.credit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static Logger logger = LoggerFactory.getLogger(Validator.class);
    private static final String regexForName="[a-zA-Z ]+";
    private static final String regexForLoginAnPassword="^[a-zA-Z0-9._-]{3,}$";


    public static boolean validateName(String name){
        Pattern pattern = Pattern.compile(regexForName);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
    public static boolean validateLoginOrPassword(String str){
        Pattern pattern = Pattern.compile(regexForLoginAnPassword);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
    public static boolean validateInt(String intLine){
        int value;
        try {
            value=Integer.parseInt(intLine);
            if(value<0)    throw new NumberFormatException();
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public static boolean validatePercent(String intLine){
       if(validateInt(intLine))
           return Integer.valueOf(intLine)<100;
       return false;
    }

}
