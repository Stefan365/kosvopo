package sk.stefan.utils;

import com.vaadin.server.VaadinSession;

import java.util.Locale;

/**
 * Created by Lukas on 22.03.2016.
 */
public class VaadinUtils {

    public static Locale DEFAULT_LOCALE = new Locale("sk", "SK");

    public static Locale getLocale() {
        Locale locale = VaadinSession.getCurrent().getLocale();
        return locale != null ? locale : DEFAULT_LOCALE;
    }
}
