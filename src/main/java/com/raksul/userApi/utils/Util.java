package com.raksul.userApi.utils;

import java.sql.Timestamp;

public class Util {
    public static Timestamp getCurrentTimeStamp(){
        return new Timestamp(System.currentTimeMillis());
    }
}
