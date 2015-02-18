/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.listeners;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import java.lang.reflect.Field;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.view.InputAllView;
import sk.stefan.MVP.view.dialogs.todo.TaskDlg;
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
    private final InputAllView iaw;
    
    public InputButClickListener(Class<?> cls, String title, InputAllView iaw) {
        this.cls = cls;
        this.title = title;
        this.iaw = iaw;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
//                        b = new Button(allButtonsNames[]Map.get(b).getButtonName());
//                    , new Button.ClickListener() {
//            
        //cls = (allButtonsMap.get(b).getClsE());
        try {
            TaskDlg tdlg;
            Field tnField = cls.getDeclaredField("TN");
            String tn;
            try {
                tn = (String) tnField.get(null);
                SQLContainer sqlCont;
                sqlCont = DoDBconn.getContainer(tn);
                tdlg = new TaskDlg("Nový " + title,
                    sqlCont,
                    null,
                    new RenewBackgroundListenerImpl(iaw));               
                UI.getCurrent().addWindow(tdlg);                
            } catch (IllegalAccessException | SQLException ex) {
                log.error("pokazilo sa to");
            }

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException ex) {
            log.error("pokazilo sa to ");
        }
    }

}
