package sk.stefan.mvps.view.tabs;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sk.stefan.annotations.ViewTab;

/**
 * Created by elopin on 03.11.2015.
 */
@Component
public class TabFactory {

    @Autowired
    private ApplicationContext context;

    private Map<String, Class<? extends TabComponent>> tabMap = new HashMap<>();

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
