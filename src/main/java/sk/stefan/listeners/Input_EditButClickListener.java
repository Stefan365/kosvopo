/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.listeners;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.log4j.Logger;
import sk.stefan.dbConnection.DoDBconn;
import sk.stefan.mvps.view.components.layouts.InputFormLayout;
import sk.stefan.mvps.view.components.UniDlg;
import sk.stefan.enums.CrutialNonEditable;
import sk.stefan.enums.NonEditableFields;

/**
 * Univerzalny listener pre input new / edit buttons.
 *
 * @author stefan
 */
@SuppressWarnings("serial")
public class Input_EditButClickListener<E> implements Button.ClickListener {

    private static final Logger log = Logger.getLogger(Input_EditButClickListener.class);

    private final E ent;
    private final Class<E> clsE;
    private final String title;
    private SQLContainer sqlCont;
    private UniDlg tdlg;
    private String tn;
    private Object itemId;
    private Item item;

//    public InputButClickListener(Class<?> cls, String title, InputAllView iaw) {
    public Input_EditButClickListener(Class<E> cls, String title, E entit) {

        this.ent = entit;
        this.clsE = cls;
        this.title = title;
        //inputAllView nepotrebuje vediet o akcii pochadzajucej z komponenty InputForm layout,
        //tj. nebude vyuzity ako listener.
//        this.iaw = iaw;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

        try {
            Field tnFld = clsE.getDeclaredField("TN");

            tn = (String) tnFld.get(null);
            sqlCont = DoDBconn.createSqlContainera(tn);
            InputFormLayout<E> inputFl;
            if (ent == null) {
                itemId = sqlCont.addItem();
                item = sqlCont.getItem(itemId);
            } else {
                Method getIdMethod = clsE.getDeclaredMethod("getId");
                Integer entId = (Integer) getIdMethod.invoke(ent);
                item = sqlCont.getItem(new RowId(new Object[]{entId}));
            }

            String[] nonEdCols = NonEditableFields.valueOf(tn.toUpperCase()).getNonEditableParams();
            String[] crutialCols = CrutialNonEditable.valueOf(tn.toUpperCase()).getCrutialParams();

            if (null == crutialCols) {
                Notification.show("crutialCols is null");

            } else {
                Notification.show("crutialCols : " + crutialCols.length);
            }

            
            log.info("NON EDITABL COLS IS NULL:" + (nonEdCols == null));
            if ((nonEdCols != null)) {
                log.info("NON ED SIZE:" + nonEdCols.length);
            }

            inputFl = new InputFormLayout<>(clsE, item, sqlCont, null, nonEdCols, crutialCols);
            tdlg = new UniDlg("Nový " + title, inputFl);

            UI.getCurrent().addWindow(tdlg);
            //POZOR na toto, malo by to dobehnut az potom, co sa ukonci vlakno 
            //UI okna:
            //sqlCont = null;
        } catch (IllegalAccessException | SQLException | NoSuchFieldException | SecurityException |
                NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("Nastala chyba!");
        }
    }

}
