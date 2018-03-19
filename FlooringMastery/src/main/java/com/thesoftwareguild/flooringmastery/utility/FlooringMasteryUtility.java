package com.thesoftwareguild.flooringmastery.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by paulharding on 9/1/16.
 */
public class FlooringMasteryUtility {

    public static String formatDateForFiles(Date dateToFormat) {

        DateFormat date = new SimpleDateFormat("MMddYYYY");

        String result = date.format(dateToFormat);

        return result;
    }

    public static String formatDateForDisplay(Date dateToFormat) {

        DateFormat date = new SimpleDateFormat("MM/dd/YYYY");

        String result = date.format(dateToFormat);

        return result;

    }

    public static String formatDateForAudits(Date dateToFormat) {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY hh:mm:ss");

        String result = dateFormat.format(dateToFormat);

        return result;

    }

    public static Date getCurrentDate() {

        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        return currentDate;

    }

    public static String checkForToken(String stringToCheck, String token) {

        String result;

        if (stringToCheck.contains(token)) {
            result = "\"" + stringToCheck + "\"";
        } else {
            result = stringToCheck;
        }

        return result;

    }

    public static List<String> generateArrayToDecode(String currentLine, String token) {

        List<String> result = new ArrayList();

        boolean sWasQuoted = false;

        String[] splitOnQuotes = currentLine.split("\"");
        for (String s : splitOnQuotes) {

            if (sWasQuoted) {
                result.add(s.trim());
            } else {

                String[] splitOnCommas = s.split(token);
                for (String c : splitOnCommas) {

                    if (!c.trim().isEmpty()) {
                        result.add(c);
                    }

                }

            }

            sWasQuoted = !sWasQuoted;
        }

        return result;

    }

}
