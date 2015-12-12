package sk.stefan.mvps.view.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.declarative.Design;
import org.springframework.context.annotation.Scope;

/**
 * Created by elopin on 09.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class DokumentyPanel extends Panel {

    private static final long serialVersionUID = 1L;

    public DokumentyPanel() {
        Design.read(this);
    }
}
