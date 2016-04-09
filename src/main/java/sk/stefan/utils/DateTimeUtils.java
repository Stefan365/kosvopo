package sk.stefan.utils;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Utilita pro práci s formátem data a času.
 * @author elopin on 29.11.2015.
 */
public class DateTimeUtils {

    /**
     * Výchozí reprezentace pro datum.
     */
    private static final String DATE_FORMAT = "d. MMMMM yyyy";

    private static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    /**
     * Vrací výchozí formát data.
     * @return výchozí formát data
     */
    public static String getDatePattern() { return DATE_FORMAT; }

    /**
     * Vrací formátovací objekt pro datum.
     * @return formátovací objekt
     */
    public static Format getDateFormat() { return dateFormat; }
}
