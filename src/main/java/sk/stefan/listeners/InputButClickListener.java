/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.listeners;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import java.lang.reflect.Field;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.view.InputAllView;
import sk.stefan.MVP.view.components.InputFormLayout;
import sk.stefan.MVP.view.components.UniDlg;
import sk.stefan.listenersImpl.RenewBackgroundListenerImpl;

/**
 *
 * @author stefan
 */
@SuppressWarnings("serial")
public class InputButClickListener implements Button.ClickListener {

    private static final Logger log = Logger.getLogger(InputButClickListener.class);

    private final Class<?> cls;
    private final String title;
//    private final InputAllView iaw;
    private SQLContainer sqlCont;
    private UniDlg tdlg;
    private String tn;
    private Object itemId;
    private Item item;
    

//    public InputButClickListener(Class<?> cls, String title, InputAllView iaw) {
    public InputButClickListener(Class<?> cls, String title) {
    
        this.cls = cls;
        this.title = title;
        //inputAllView nepotrebuje vediet o akcii pochadzajucej z komponenty InputForm layout,
        //tj. nebude vyuzity ako listener.
//        this.iaw = iaw;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        
        try {
            Field tnFld = cls.getDeclaredField("TN");

            tn = (String) tnFld.get(null);
            sqlCont = DoDBconn.getContainer(tn);
            InputFormLayout<? extends Object> inputFl;
            itemId = sqlCont.addItem();
            item = sqlCont.getItem(itemId);
            inputFl = new InputFormLayout<>(cls, item, sqlCont, null, null);
            tdlg = new UniDlg("Nový " + title, inputFl);
            
            UI.getCurrent().addWindow(tdlg);
            //POZOR na toto, malo by to dobehnut az potom, co sa ukonci vlakno 
            //UI okna:
            //sqlCont = null;
        } catch (IllegalAccessException | SQLException | NoSuchFieldException | SecurityException ex) {
            log.error(ex.getMessage());
        }

    }

}
