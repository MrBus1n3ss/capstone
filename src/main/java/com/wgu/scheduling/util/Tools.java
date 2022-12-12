package com.wgu.scheduling.util;

import java.sql.Timestamp;

public class Tools {
    public static Timestamp convertStringToTimestamp(String time) {
        time = time.replace("T", " ");
        time = time + ":00";
        return Timestamp.valueOf(time);
    }

}
