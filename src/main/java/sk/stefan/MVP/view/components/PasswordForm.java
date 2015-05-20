/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.listeners.RefreshViewListener;

/**
 * Formular na vkladanie noveho hesla / zmenu hesla.
 *
 * @author stefan
 */
public class PasswordForm extends VerticalLayout implements OkCancelListener {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(PasswordForm.class);

    private PasswordField oldPwdTf;
    private PasswordField newPwd1Tf;
    private PasswordField newPwd2Tf;

    private final Integer userId;

    private final SecurityService securityService;

    private final OkCancelListener okCancelListener;
    private OkCancelListener forWindowListener;
    private final ObnovFilterListener obnovFilterListener;
    private final RefreshViewListener refreshViewListener;

    //toto je len pre ucely docasneho ulozenia hesla, pri zadani noveho usera.
    private String newPassword = null;

    //0. konstruktor
    /**
     *
     * @param it
     * @param cp
     * @param uid
     */
    public PasswordForm(Item it, Component cp, Integer uid) {

        this.okCancelListener = (OkCancelListener) cp;
        this.obnovFilterListener = (ObnovFilterListener) cp;
        this.refreshViewListener = (RefreshViewListener) cp;

        this.securityService = new SecurityServiceImpl();
        
        this.userId = uid;
        this.initLayout();
    }

    private void initLayout() {

        if (userId != null) {
            oldPwdTf = new PasswordField("staré heslo");
            this.addComponent(oldPwdTf);
        }
        newPwd1Tf = new PasswordField("nové heslo");
        newPwd2Tf = new PasswordField("potvrdenie nového hesla");
        this.addComponent(newPwd1Tf);
        this.addComponent(newPwd2Tf);

    }

    /**
     * Overi stare heslo, ci bolo zadane spravne.
     */
    private boolean verifyPassword(String rawOldPwd, Integer uid) {
        //1. podmienka: stare heslo je spravne.
        boolean isOldGood;
        //pokial je novy,  je to ok.
        try {
            if (uid == null) {
                isOldGood = true;
            } else {
                byte[] pwd = securityService.getPassword(uid);
                isOldGood = securityService.checkPassword(rawOldPwd, pwd);
                log.info("IS OLD GOOD: " + (isOldGood));
            }
            return isOldGood && newPwd1Tf.getValue().equals(newPwd2Tf.getValue());
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("Overovanie hesla sa nepodarilo");
            return false;
        }
    }

    @Override
    public void doOkAction() {

        String rawNewPwd;
        String rawOldPwd = "";
        boolean isOldPassValid;
        rawNewPwd = newPwd1Tf.getValue();

        if (userId != null) {
            rawOldPwd = oldPwdTf.getValue();
            isOldPassValid = this.verifyPassword(rawOldPwd, userId);

        } else {
            isOldPassValid = this.verifyPassword(rawOldPwd, userId);
        }
        if (isOldPassValid) {
            this.newPassword = rawNewPwd;
            Notification.show("Heslo pripravené!");

        } else {
            
            Notification.show("Heslo sa nepodarilo pripraviť!");

        }

        this.okCancelListener.doOkAction();
//        this.forWindowListener.doOkAction();
        this.obnovFilterListener.obnovFilter();
        this.refreshViewListener.refreshView();

    }

    @Override
    public void doCancelAction() {
        this.okCancelListener.doCancelAction();
//        this.forWindowListener.doOkAction();
    }

    
    
    
    
    
    
    
    
    //***************    GETTERS AND SETTERS *********************
    
    
    void setWindowOkCancelListener(OkCancelListener aThis) {
        this.forWindowListener = aThis;
    }

    
    public PasswordField getOldPwdTf() {
        return oldPwdTf;
    }

    public void setOldPwdTf(PasswordField oldPwdTf) {
        this.oldPwdTf = oldPwdTf;
    }

    public PasswordField getNewPwd1Tf() {
        return newPwd1Tf;
    }

    public void setNewPwd1Tf(PasswordField newPwd1Tf) {
        this.newPwd1Tf = newPwd1Tf;
    }

    public PasswordField getNewPwd2Tf() {
        return newPwd2Tf;
    }

    public void setNewPwd2Tf(PasswordField newPwd2Tf) {
        this.newPwd2Tf = newPwd2Tf;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
