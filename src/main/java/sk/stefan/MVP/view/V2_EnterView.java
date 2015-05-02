package sk.stefan.MVP.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import sk.stefan.MVP.view.components.NavigationComponent;

public class V2_EnterView extends VerticalLayout implements View {

    private static final long serialVersionUID = -2001141270398193257L;
    
//    private final Label vstupniLab;
    
    private final VerticalLayout temporaryLy;
    
    private final NavigationComponent navComp;
    
    private final Navigator nav;
    

    public V2_EnterView() {
   
        this.setMargin(true);
        this.setSpacing(true);
        
        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);
        
        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

//        this.vstupniLab = new Label("KAMIL");
//        this.vstupniLab.setCaption("Marcel Z Maleho Mesta JE Tu!!! Je, je, je jeee....");
//        this.vstupniLab.setValue("HODNOTA");
        

//        temporaryLy.addComponent(vstupniLab);
        
        
    }
    
    private void setNavigator(Navigator nav){
        
        
        
    }

    @Override
    public void enter(ViewChangeEvent event) {
        
    }

}
