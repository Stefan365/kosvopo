/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.listeners.RenewBackgroundListener;

/**
 * Dialog pro editaci vlastnosti jedne ulohy
 *
 * @author Marek Svarc
 * @author Stefan
 */
public class UniDlg extends Window implements OkCancelListener, ObnovFilterListener {

    private static final long serialVersionUID = 1L;

    /**
     * Formulář na zadávání/úpravu úkolů.
     */
    //final private InputFormLayout<? extends Object> flInputForm;

    /**
     * Listener na obnovu základního view, ze kterého se do fromulářového okna
     * vstupuje, po vyplnění formuláře.
     */
    private final RenewBackgroundListener listener;

    //0.
    /**
     * Konstruktor.
     *
     * @param caption nadpis okna
     * @param inputFl
     * @param lis Listener na obnovu view.
     */
    public UniDlg(String caption, InputFormLayout<? extends Object> inputFl, RenewBackgroundListener lis) {
        this.listener = lis;
        this.setCaption(caption);
        setModal(true);
        inputFl.setOkCancelListener(this);
//        InputFormLayout<> InputFormFl = new InputFormLayout<>(cls, item, sqlCont, this, null);

        // obsah dialogu
        VerticalLayout content = new VerticalLayout(inputFl);
        content.setSpacing(true);
        content.setMargin(true);

        setContent(content);
    }

    public UniDlg(PasswordForm passFm, String caption, RenewBackgroundListener lis) {
        this.listener = lis;
        this.setCaption(caption);
        setModal(true);
//        flInputForm = new InputFormLayout<>(cls, item, sqlCont, this, null);

        // obsah dialogu
        VerticalLayout content = new VerticalLayout(passFm);
        content.setSpacing(true);
        content.setMargin(true);

        setContent(content);
    }

    // pozri na popis OkCancelListener
    @Override
    public void doOkAction() {
        listener.refreshTodo();
        close();
    }

    @Override
    public void doCancelAction() {
        //listener.refreshTodo();
        close();
    }

    @Override
    public void obnovFilter() {
        listener.refreshFilters();
    }

}
