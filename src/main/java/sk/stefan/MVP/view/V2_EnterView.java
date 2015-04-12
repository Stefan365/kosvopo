package sk.stefan.MVP.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import sk.stefan.MVP.view.components.NavigationComponent;

public class V2_EnterView extends VerticalLayout implements View {

    private static final long serialVersionUID = -2001141270398193257L;
    
    private final Label vstupniLab;

    public V2_EnterView() {
   
        this.addComponent(NavigationComponent.getNavComp());

        this.vstupniLab = new Label("Karolko");
        this.vstupniLab.setCaption("Marcel Z Maleho Mesta JE Tu!!! Je, je, je jeee....");
    }

    @Override
    public void enter(ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }

}