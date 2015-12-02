package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.context.annotation.Scope;
import sk.stefan.mvps.model.entity.A_User;

/**
 * Created by elopin on 01.12.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class RoleUzivatelePanel extends CssLayout {

    public RoleUzivatelePanel()  {
        Design.read(this);
    }

    public void setUzivatel(A_User user) {

    }
}
