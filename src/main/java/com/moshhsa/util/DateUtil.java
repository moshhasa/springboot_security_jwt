package com.moshhsa.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtil {

    private DateUtil(){

    }

    public static Date now(){
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }

    public static Date hoursFromNow(long hours){
        return Date.from(LocalDateTime.now().plusHours(hours).toInstant(ZoneOffset.UTC));
    }
}
