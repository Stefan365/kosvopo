package sk.stefan.MVP.view.superseded;

import sk.stefan.MVP.view.components.NavigationComponent;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ZBD_Welcome extends VerticalLayout implements View {

    private Navigator navigator;

    private final Button goToPage1;
    private final Button goToPage2;

    public ZBD_Welcome(Navigator nav) {

        this.addComponent(NavigationComponent.createNavigationComponent());

        this.navigator = nav;

        ClickListener listener = new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                navigator.navigateTo(event.getButton().getCaption());
            }
        };

        goToPage1 = new Button("page1");
        goToPage2 = new Button("page2");

        goToPage1.addClickListener(listener);
        goToPage2.addClickListener(listener);

        addComponent(new Label("WELCOME"));
        addComponent(goToPage1);
        addComponent(goToPage2);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        Notification.show("Showing Welcome page");
        navigator = event.getNavigator();
    }

}
