package sk.stefan.utils;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

import java.net.URL;

/**
 * Created by elopin on 29.11.2015.
 */
public class ImageTools {

    public static void fillDefaultImage(Image image) {
        image.setSource(new ThemeResource("images/default.jpg"));
    }
}
