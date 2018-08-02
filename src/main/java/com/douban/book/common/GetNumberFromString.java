package com.douban.book.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetNumberFromString {
    public static int getNumber(String str)
    {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);
        String number = matcher.replaceAll("");
        if(!number.isEmpty()){
            return Integer.parseInt(number);
        }else{
            return 0;
        }

    }
}
