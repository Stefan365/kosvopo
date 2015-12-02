package sk.stefan.utils;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by elopin on 29.11.2015.
 */
public class DateTimeUtils {

    private static final String DATE_FORMAT = "d. MMMMM yyyy";

    private static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    public static String getDatePattern() { return DATE_FORMAT; }

    public static Format getDateFormat() { return dateFormat; }
}
