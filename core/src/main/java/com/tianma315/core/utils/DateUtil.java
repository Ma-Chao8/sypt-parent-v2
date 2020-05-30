package com.tianma315.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DateUtil {
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static List<String> initYear() {
        List<String> dates = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12");
        return dates;
    }

    public static List<String> initMonth(String year,String month) {
        Integer y = Integer.parseInt(year);
        Integer m = Integer.parseInt(month);
        Integer count = getDaysByYearMonth(y,m);
        List<String> result = new ArrayList<>(count);
        for (int i = 1; i<=count; i++){
            String obj = "";
            if (i<10){
                obj = "0"+i;
            }else{
                obj = i+"";
            }
            result.add(obj);
        }
        return result;
    }
}
