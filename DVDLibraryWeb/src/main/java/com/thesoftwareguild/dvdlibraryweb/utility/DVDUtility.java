package com.thesoftwareguild.dvdlibraryweb.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DVDUtility {
    
    public static String formatDateForEncode(Date dateToFormat) {

        DateFormat date = new SimpleDateFormat("MM/dd/YYYY");
        return date.format(dateToFormat);

    }
}
