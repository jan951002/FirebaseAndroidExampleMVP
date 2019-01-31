package com.jan.firebaseandroidexample.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getFileDate(Date date) {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
    }

}
