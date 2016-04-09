package sk.stefan.mvps.view.tabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sk.stefan.annotations.ViewTab;

import java.util.HashMap;
import java.util.Map;

/**
 * Továrna na záložky.
 * @author elopin on 03.11.2015.
 */
@Component
public class TabFactory {

    @Autowired
    private ApplicationContext context;

    private Map<String, Class<? extends TabComponent>> tabMap = new HashMap<>();

    /**
     * Vrací typ záložky podle názvu.
     * @param name název, id záložky
     * @return typ záložky
     */
    public Class<? extends TabComponent> getTabTypeByName(String name) {
        if (tabMap.isEmpty()) {
            context.getBeansWithAnnotation(ViewTab.class).values().forEach(clazz -> {
                ViewTab annotation = clazz.getClass().getAnnotation(ViewTab.class);
                tabMap.put(annotation.value(), (Class<? extends TabComponent>) clazz.getClass());
            });
        }
        return tabMap.get(name);
    }
}
