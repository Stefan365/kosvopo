package sk.stefan.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by elopin on 03.11.2015.
 */
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ViewTab {

    /**
     * Tab name.
     * @return name of tab for navigation
     */
    String value();
}
