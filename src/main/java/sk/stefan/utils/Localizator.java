package sk.stefan.utils;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Upload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import sk.stefan.mvps.view.tabs.TabComponent;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Lokalizační framework pro UI komponenty. Využívá MessageSource bean ze Spring.
 * TODO refaktorovat, učesat
 * @author Lukas on 22.03.2016.
 */
@Component
public class Localizator implements ApplicationContextAware {

    private static Logger LOGGER = LoggerFactory.getLogger(Localizator.class);

    private static MessageSource messageSource;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.messageSource = applicationContext.getBean(MessageSource.class);
    }

    /**
     * Provede lokalizaci komponent UI komponent. Pro použití v deklarativním designu Vaadinu.
     * @param component komponenta obsahující deklarace UI komponent, @DesignRoot
     */
    public static void localizeDesign(com.vaadin.ui.Component component) {
        if (AbstractComponent.class.isAssignableFrom(component.getClass())) {
            localizeComponent(component.getClass().getCanonicalName(), null, (AbstractComponent) component);
        }

        String baseKey = component.getClass().getCanonicalName();
        for (Field field : component.getClass().getDeclaredFields()) {
            if (AbstractComponent.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                localizeComponent(baseKey, field.getName(), (AbstractComponent) ReflectionUtils.getField(field, component));
            }
        }
    }

    /**
     * Lokalizuje UI komponentu rozšiřující AbstractComponent.
     * @param baseKey základ lokalizačního klíče
     * @param componentName název komponenty, její _id v HTML
     * @param component lokalizovaná komponenta
     */
    public static void localizeComponent(String baseKey, String componentName, AbstractComponent component) {
        Locale locale = VaadinUtils.getLocale();
        String componentKey = null;
        if (componentName != null) {
            componentKey = baseKey + "." + componentName;
        } else {
            componentKey = baseKey;
        }
        String caption = getLocalizedMessage(componentKey, ".cap", null, locale);
        if (caption == null) {
            caption = getDefaultMessage(componentName, ".cap", locale);
        }

        if (caption != null) {
            if (component instanceof Upload) {
                ((Upload) component).setButtonCaption(caption);
            } else {
                component.setCaption(caption);
            }
        }

        if (Label.class.isAssignableFrom(component.getClass())) {
            String value = getLocalizedMessage(componentKey, "", null, locale);
            if (value != null) {
                ((Label) component).setValue(value);
            }
        }

        if (AbstractTextField.class.isAssignableFrom(component.getClass()) || ComboBox.class.isAssignableFrom(component.getClass())) {
            String inputPrompt = getLocalizedMessage(componentKey, ".ip", null, locale);
            if (inputPrompt == null) {
                inputPrompt = getDefaultMessage(componentName, ".ip", locale);
            }
            if (inputPrompt != null) {
                if (AbstractTextField.class.isAssignableFrom(component.getClass())) {
                    ((AbstractTextField) component).setInputPrompt(inputPrompt);
                } else {
                    ((ComboBox) component).setInputPrompt(inputPrompt);
                }
            }
        }

        if (Grid.class.isAssignableFrom(component.getClass())) {
            Grid grid = (Grid) component;
            grid.getColumns().forEach(column -> {
                String headerCaption = getLocalizedMessage(baseKey + ".grid." + column.getPropertyId(), ".cap", null, locale);
                if (headerCaption != null) {
                    column.setHeaderCaption(headerCaption);
                }
            });
        }
    }

    /**
     * Vrací lokalizovaný název záložky.
     * @param tab záložka
     * @return lokalizovaný název záložky
     */
    public static String localizeTab(TabComponent tab) {
        return getLocalizedMessage(tab.getClass().getCanonicalName(), ".cap", null, VaadinUtils.getLocale());
    }

    /**
     * Vrací výchozí lokalizaci komponenty. Určeno pro komponenty, které se nacházejí na více místech v aplikaci. Např. tlačítka
     * Uložit, Odstranit, Zrušit
     * @param componentName název komponenty, její _id v HTML
     * @param type typ lokalizace (popisek, input prompt)
     * @param locale locale
     * @return výchozí hodnotu pro lokalizaci komponentu
     */
    private static String getDefaultMessage(String componentName, String type, Locale locale) {
        return getLocalizedMessage("default." + componentName, type, null, locale);
    }

    /**
     * Vrací lokalizovaný string z messages.properties.
     * @param key lokalizační klíč
     * @param type typ lokalizace (popisek, input prompt)
     * @param args volitelné argumenty pro dosazení placeholderů v messages.properties
     * @param locale locale
     * @return lokalizovanou hodnotu pro daný klíč a typ lokalizační zprávy
     */
    public static String getLocalizedMessage(String key, String type, Object[] args, Locale locale) {
        try {
            return messageSource.getMessage(key + type, args, locale);
        } catch (NoSuchMessageException ex) {

            // MessageSource při chybějícím klíči v properties souboru vyhodí výjimku
            LOGGER.debug(ex.getLocalizedMessage());
        }
        return null;
    }
}
