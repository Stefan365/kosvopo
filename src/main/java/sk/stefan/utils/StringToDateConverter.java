package sk.stefan.utils;

import java.text.DateFormat;
import java.util.Locale;

/**
 * String to date convertor with custom DateDormat.
 * @author elopin on 06.12.2015.
 */
public class StringToDateConverter extends com.vaadin.data.util.converter.StringToDateConverter {

    @Override
    protected DateFormat getFormat(Locale locale) {
        return (DateFormat) DateTimeUtils.getDateFormat();
    }
}
