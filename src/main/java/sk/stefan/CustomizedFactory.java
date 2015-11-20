package sk.stefan;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.declarative.DesignContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Toto rozšíření umožňuje Autowirování Spring beanů do deklarativních komponent.
 * @author elopin on 12.11.2015.
 */
@SpringComponent
public class CustomizedFactory extends Design.DefaultComponentFactory {

    @Autowired
    private AutowireCapableBeanFactory factory;

    @Override
    public Component createComponent(String fullyQualifiedClassName, DesignContext context) {
        Component component = super.createComponent(fullyQualifiedClassName, context);
        factory.autowireBean(component);
        return component;
    }
}
