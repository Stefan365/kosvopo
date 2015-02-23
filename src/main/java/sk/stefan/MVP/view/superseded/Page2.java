package sk.stefan.MVP.view.superseded;

import sk.stefan.MVP.view.components.NavigationComponent;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class Page2 extends VerticalLayout implements View {

    private Navigator navigator;

    Button goToPageW;
    Button goToPage1;

    public Page2(Navigator nav) {

        this.navigator = nav;

        ClickListener listener = new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                navigator.navigateTo(event.getButton().getCaption());
            }
        };

        goToPageW = new Button("welcome");
        goToPage1 = new Button("page1");

        goToPageW.addClickListener(listener);
        goToPage1.addClickListener(listener);

        addComponent(new Label("Page DVA"));
        addComponent(goToPageW);
        addComponent(goToPage1);

        this.addComponent(NavigationComponent.getNavComp());

    }

    @Override
    public void enter(ViewChangeEvent event) {
        Notification.show("Showing page 2");
        navigator = event.getNavigator();
    }

}
