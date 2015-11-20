package sk.stefan.mvps.view.tabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sk.stefan.annotations.ViewTab;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by elopin on 03.11.2015.
 */
@Component
public class TabFactory {

    @Autowired
    private ApplicationContext context;

    private Map<String, Class<?>> tabMap = new HashMap<>();

    @PostConstruct
    public void init() {
        context.getBeansWithAnnotation(ViewTab.class).values().forEach(clazz -> {
            ViewTab annotation = clazz.getClass().getAnnotation(ViewTab.class);
            tabMap.put(annotation.value(), clazz.getClass());
        });
    }

    public Class<?> getTabTypeByName(String name) {
        Class<?> clazz = tabMap.get(name);
        if (clazz == null) {
            throw new RuntimeException("Undefined tab class with name " + name + "!");
        }
        return clazz;
    }
}
