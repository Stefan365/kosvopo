package sk.stefan.utils;

import com.sun.corba.se.impl.legacy.connection.USLPort;
import com.vaadin.server.VaadinSession;

import java.util.Locale;

/**
 * Utilita pro Vaadin komponenty.
 * @author Lukas on 22.03.2016.
 */
public class VaadinUtils {

    public static final Locale US_LOCALE = new Locale("en", "US");
    /**
     * Výchozí locale aplikace.
     */
    public static final Locale DEFAULT_LOCALE = new Locale("sk", "SK");

    /**
     * Vrací locale z VaadinSession nebo výchozí.
     * @return aktuální locale uživatele
     */
    public static Locale getLocale() {
        Locale locale = VaadinSession.getCurrent().getLocale();
        return locale != null ? locale : DEFAULT_LOCALE;
    }
}
