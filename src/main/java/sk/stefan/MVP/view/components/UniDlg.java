/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import sk.stefan.MVP.view.components.hlasovanie.VotingLayout;
import sk.stefan.listeners.OkCancelListener;

/**
 * Dialog pro editaci vlastnosti jedne ulohy
 *
 * @author Stefan
 */
public class UniDlg extends Window implements OkCancelListener {

    private static final long serialVersionUID = 1L;

    /**
     * Listener na obnovu základního view, ze kterého se do fromulářového okna
     * vstupuje, po vyplnění formuláře.
     */
    private OkCancelListener parentListenerOc;

    //0.
    /**
     * Konstruktor. Okno pre vstupny formular. 
     *
     * @param caption nadpis okna
     * @param inputFl
     */
//    public UniDlg(String caption, InputFormLayout<? extends Object> inputFl, OkCancelListener cp) {
    public UniDlg(String caption, InputFormLayout<?> inputFl) {

        this.setCaption(caption);
        setModal(true);
        inputFl.setOkCancelListener(this);

        // obsah dialogu
        addContent(inputFl);
    }

    /**
     * Okno pre zmenu hesla.
     * @param passFm
     * @param caption
     * @param lis
     */
    public UniDlg(PasswordForm passFm, String caption, OkCancelListener lis) {
        
        this.parentListenerOc = lis;
        
        this.setCaption(caption);
        passFm.setWindowOkCancelListener(this);
        setModal(true);
        
        // obsah dialogu
        addContent(passFm);
        
    }
    
    /**
     * Okno pre vlozenie hlasovania.
     * nepotrebuje ziadny listener.
     * 
     * @param votingLy
     * @param caption
     */
    public UniDlg(String caption, VotingLayout votingLy) {
        
        this.setCaption(caption);
        votingLy.setWindowOkCancelListener(this);
        setModal(true);
        
        addContent(votingLy);
    
    }

    
    //1.
    /**Pomocna funkcia.
     * Vlozi prislusnu komponentu do seba, tj. do okna. 
     */
    private void addContent(Component com){
        
        VerticalLayout content = new VerticalLayout(com);
        content.setSpacing(true);
        content.setMargin(true);

        setContent(content);
    }

    
    
    // pozri na popis OkCancelListener
    @Override
    public void doOkAction() {
        if (parentListenerOc != null) {
            parentListenerOc.doOkAction();
        }
        close();
    }

    @Override
    public void doCancelAction() {
        if (parentListenerOc != null) {

            parentListenerOc.doCancelAction();
        }
        close();
    }

//    @Override
//    public void obnovFilter() {
//        if (listenerOc != null) {
//
//            listenerF.obnovFilter();
//        }
//        //do nothing this will be done via OK Cancel listener;
//    }

}
