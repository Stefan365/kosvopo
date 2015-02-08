package sk.stefan.MVP.view;

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
public class Page1 extends VerticalLayout implements View {

	private Navigator navigator;
	
	Button goToPageW;
	Button goToPage2;
	
	public Page1(Navigator nav) {
		
		this.navigator = nav;
		
		ClickListener listener = new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(event.getButton().getCaption());
			}
		};
		
	
	
		goToPageW = new Button("welcome");
		goToPage2 = new Button("page2");
	
		goToPageW.addClickListener(listener);
		goToPage2.addClickListener(listener);
		
	
		addComponent(new Label("Page ONE"));
		addComponent(goToPageW);
		addComponent(goToPage2);
		
		this.addComponent(new NavigationComponent(nav));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Showing page 1");
		navigator = event.getNavigator();
	}

}
