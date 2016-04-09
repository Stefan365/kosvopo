package sk.stefan.utils;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

/**
 * Utilita pro práci s obrázky.
 * @author elopin on 29.11.2015.
 */
public class ImageTools {

    /**
     * Nastaví výchozí obrázek do Image objektu.
     * @param image image objekt
     */
    public static synchronized void fillDefaultImage(Image image) {
        image.setSource(new ThemeResource("images/default.jpg"));
    }
}
