package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.declarative.Design;
import org.springframework.context.annotation.Scope;

/**
 * Created by elopin on 22.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class HlasovaniPanel extends Panel {

    public HlasovaniPanel() {
        Design.read(this);
    }
}
