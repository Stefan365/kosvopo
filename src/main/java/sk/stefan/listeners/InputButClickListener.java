/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.listeners;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.repo.dao.UniRepo;
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
    private SQLContainer sqlCont;
    private TaskDlg tdlg;
    private String tn;

    public InputButClickListener(Class<?> cls, String title, InputAllView iaw) {
        this.cls = cls;
        this.title = title;
        this.iaw = iaw;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        //cls = (allButtonsMap.get(b).getClsE());
        try {
            Field tnFld = cls.getDeclaredField("TN");

            tn = (String) tnFld.get(null);
            sqlCont = DoDBconn.getContainer(tn);

            //vytvorenie noveho dialogoveho okna s mapami
            //toto dat do input form layoutu.
//                Class<?> repoCls = Class.forName("sk.stefan.MVP.model.repo.dao.UniRepo");
//                Constructor<UniRepo> repoCtor = (Constructor<UniRepo>) repoCls.getConstructor(Class.class);
//                return repoCtor.newInstance(cls).findAll();
            tdlg = new TaskDlg("Nový " + title,
                    sqlCont,
                    null,
                    new RenewBackgroundListenerImpl(iaw),
                    cls
            );
            UI.getCurrent().addWindow(tdlg);
            //POZOR na toto, malo by to dobehnut az potom, co sa ukonci vlakno 
            //UI okna:
            sqlCont = null;
        } catch (IllegalAccessException | SQLException | NoSuchFieldException | SecurityException ex) {
            log.error(ex.getMessage());
        }

    }

}
