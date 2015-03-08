package sk.stefan.MVP.view.components;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import sk.stefan.listeners.GeneralComponentListener;

import com.vaadin.ui.*;

/**
 * trieda pre potvrzovací dialog. 
 *
 * @author stefan
 */
public class TBD_YesNoWindow extends Window {

	
	Button yesBT, noBT;
	GeneralComponentListener listener;
	HorizontalLayout buttonLayout;
	VerticalLayout content;
	Label msgLB;
	
    /**
     * Konstruktor.
     *
     * @param caption nadpis okna
     * @param message zpráva, která má být zobrazena
     */
    public TBD_YesNoWindow(String caption, String message, GeneralComponentListener listener) {
    	
        super(caption);
        
    	this.listener = listener;
    	
        content = new VerticalLayout();
        this.setContent(content);
        content.setWidth(450, Unit.PIXELS);
        content.setMargin(true);
        content.setSpacing(true);
        setModal(false);

        buttonLayout = new HorizontalLayout();
        msgLB = new Label(message);
        content.addComponent(msgLB);

        buttonLayout = new HorizontalLayout();
        content.addComponent(buttonLayout);
        buttonLayout.setSpacing(true);
        
        yesBT = new Button("Ano");
        buttonLayout.addComponent(yesBT);
        
        yesBT.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
					onYes();
				} catch (NoSuchFieldException | SecurityException
						| NoSuchMethodException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        
        noBT = new Button("Ne");
        buttonLayout.addComponent(noBT);
        noBT.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                onNo();
            }
        });
       
        //poskladanie:
        center();
    }

    public void doYes() throws NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    	listener.doYesAction();
    } 

    public void onYes() throws NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        doYes();
        close();
    }

    public void onNo() {
    	listener.doNoAction();
        close();
    }

}
