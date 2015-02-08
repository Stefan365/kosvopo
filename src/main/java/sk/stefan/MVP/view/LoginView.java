package sk.stefan.MVP.view;



import sk.stefan.MVP.view.components.NavigationComponent;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;




/**
 * Třída komponenty pro přihlášení do systému
 */
@SuppressWarnings("serial")
public class LoginView extends VerticalLayout implements View {

	/** Textové pole pro zadani uzivatelskeho emailu */
	private TextField tfEmail;
	
	/** Textové pole pro zadání hesla */
	private PasswordField tfPasswrord;
	
	Navigator navigator;


	public LoginView(Navigator nav) {
		
		this.navigator = nav;
		this.addComponent(new NavigationComponent(nav));
		
	}
	
		/*
		// Vytvareni komponent

		tfEmail = Tools.createFormTextField("Email", true);
		tfPasswrord = Tools.createFormPasswordField("Heslo", true);

		final Button btLogin = new Button("Přihlásit", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Zde autentizace uzivatele
				navigation.navigateTo(ViewId.TODOS, null);
			}
		});
		btLogin.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		btLogin.setSizeFull();

		final Button btRegister = new Button("Registrovat", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigation.navigateTo(ViewId.REGISTER, null);
			}
		});
		btRegister.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

		// Zarovnani komponent na stranku
		
		HorizontalLayout hlButtons = new HorizontalLayout(btRegister, btLogin);
		hlButtons.setSizeFull();
		hlButtons.setSpacing(true);
		hlButtons.setExpandRatio(btLogin, 1.0f);

		VerticalLayout vlForm = new VerticalLayout(tfEmail, tfPasswrord, hlButtons);
		vlForm.setMargin(true);
		vlForm.setSpacing(true);

		this.addComponent(Tools.createPanelCaption("Přihlášení do Úkolníčku"));
		this.addComponent(new Panel(vlForm));
		this.setWidth(300, Unit.PIXELS);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// prazdna metoda, mozno pridat inicializaci z cookies 
	}
/*/

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	

}
