package sk.stefan.annotations;

import com.vaadin.server.FontAwesome;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Class annotation for menu button creation.
 * @author  elopin on 03.11.2015.
 */
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface MenuButton {

    /**
     * Button id.
     * @return button id
     */
    String name();

    /**
     * Position in menu.
     * @return position of button in menu
     */
    int position();

    /**
     * Icon for button.
     * @return FontAwesome icon
     */
    FontAwesome icon();

}
