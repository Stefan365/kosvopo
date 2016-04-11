package sk.stefan;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.declarative.DesignContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Toto rozšíření umožňuje Autowirování Spring beanů do deklarativních
 * komponent.
 *
 * @author elopin on 12.11.2015.
 */
@SpringComponent
public class CustomizedFactory extends Design.DefaultComponentFactory {

    private static final long serialVersionUID = 43113294832L;

    @Autowired
    private AutowireCapableBeanFactory factory;

    /**
     * Provede Dependency Injection do vlastních UI komponent použitých v deklarativním designu.
     * @param fullyQualifiedClassName class name
     * @param context kontext deklarativního designu
     * @return komponentu obohacenou o závislosti @Autowired
     */
    @Override
    public Component createComponent(String fullyQualifiedClassName, DesignContext context) {
        Component component = super.createComponent(fullyQualifiedClassName, context);
        factory.autowireBean(component);
        return component;
    }
}
