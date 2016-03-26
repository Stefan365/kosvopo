package sk.stefan.utils;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Created by Lukas on 22.03.2016.
 */
@Component
public class Localizator implements ApplicationContextAware {

    private static Logger LOGGER = LoggerFactory.getLogger(Localizator.class);

    private static MessageSource messageSource;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.messageSource = applicationContext.getBean(MessageSource.class);
    }

    public static void localizeDesign(com.vaadin.ui.Component component) {
        String baseKey = component.getClass().getCanonicalName();
        for (Field field : component.getClass().getDeclaredFields()) {
           if (AbstractComponent.class.isAssignableFrom(field.getType())) {
               field.setAccessible(true);
               localizeComponent(baseKey + "." + field.getName(), (AbstractComponent) ReflectionUtils.getField(field, component));
           }
        }
    }

    public static void localizeComponent(String componentKey, AbstractComponent component) {
        Locale locale = VaadinUtils.getLocale();
            String caption = getLocalizedMessage(componentKey + ".cap", null, locale);
            if (caption != null) {
                component.setCaption(caption);
            }

            if (Label.class.isAssignableFrom(component.getClass())) {
                String value = getLocalizedMessage(componentKey, null, locale);
                if (value != null) {
                    ((Label) component).setValue(value);
                }
            }

            if (AbstractTextField.class.isAssignableFrom(component.getClass())) {
                String inputPrompt = getLocalizedMessage(componentKey + ".ip", null, locale);
                if (inputPrompt != null) {
                    ((AbstractTextField) component).setInputPrompt(inputPrompt);
                }
            }

    }

    public static String getLocalizedMessage(String key, Object[] args, Locale locale) {
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException ex) {
            LOGGER.debug(ex.getLocalizedMessage());
        }
        return null;
    }
}
