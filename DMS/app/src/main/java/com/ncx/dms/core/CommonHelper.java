package com.ncx.dms.core;

import java.text.DecimalFormat;
import java.text.ParseException;

public class CommonHelper {

    public static final String dateFormat = "dd/MM/yyyy";

    public CommonHelper(){

    }

    public CommonHelper getInstance(){
        return new CommonHelper();
    }

    public static boolean isNullOrEmpty(String value){
        if (value != null && !value.isEmpty() && !value.equals("null")) {
            return value.trim().length() == 0;
        }

        return true;
    }

    public static String getNumberFormatString(Object value){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return decimalFormat.format(value);
    }

    public static String getCurrencyFormatString(Object value){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(value);
    }

    public static Double getDobuleFromString(String value) {
        try{
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.parse(value).doubleValue();
        }catch (Exception e){
            return null;
        }
    }

    public static Integer getIntegerFromString(String value) {
        try{
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            return decimalFormat.parse(value).intValue();
        }catch (Exception e){
            return null;
        }
    }
}
