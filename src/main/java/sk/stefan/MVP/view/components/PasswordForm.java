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
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.SecurityServiceImpl;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.listeners.RefreshViewListener;

/**
 *
 * @author stefan
 */
public class PasswordForm extends VerticalLayout implements OkCancelListener {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(PasswordForm.class);

    private PasswordField oldPwdTf;
    private PasswordField newPwd1Tf;
    private PasswordField newPwd2Tf;

    private final Item item;
    private final UniRepo<A_User> uniRepo;
    private final OkCancelListener okCancelListener;
    private final ObnovFilterListener obnovFilterListener;
    private final RefreshViewListener refreshViewListener;
    
    private final SecurityService securityService;

    public PasswordForm(Item it, Component cp) {
        this.okCancelListener = (OkCancelListener)cp;
        this.obnovFilterListener = (ObnovFilterListener)cp;
        this.refreshViewListener = (RefreshViewListener)cp;
                
        this.item = it;
        this.securityService = new SecurityServiceImpl();
        this.uniRepo = new UniRepo<>(A_User.class);
        this.initLayout();
    }

    private void initLayout() {

        oldPwdTf = new PasswordField("staré heslo");
        newPwd1Tf = new PasswordField("nové heslo");
        newPwd2Tf = new PasswordField("potvrdenie nového hesla");

        this.addComponent(oldPwdTf);
        this.addComponent(newPwd1Tf);
        this.addComponent(newPwd2Tf);

    }

    private void verifyPassword() {
        //1. podmienka: stare heslo je spravne.
//        item.getItemProperty("id").getValue()
        Integer id = (Integer) item.getItemProperty("id").getValue();
        String rawPwd = oldPwdTf.getValue();

        try {
            byte[] pwd = uniRepo.getPassword("" + id);
            boolean isGood = securityService.checkPassword(rawPwd, pwd);
            isGood = true;
            log.info("ISGOOD: " + (isGood));
            if (isGood && newPwd1Tf.getValue().equals(newPwd2Tf.getValue())) {
                addToDB(newPwd1Tf.getValue(), "" + id);
                Notification.show("heslo úspešne zmenené!");
            } else {
                Notification.show("chod do p...!");
            }
            
            //return Boolean.TRUE;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("Zmena hesla sa nepodarila");
            //return Boolean.FALSE;
        }
    }

    private void addToDB(String newPwd, String id) throws SQLException {
        uniRepo.updatePassword(newPwd, id);
    }

    /**
     * @return the oldPwdTf
     */
    public PasswordField getOldPwdTf() {
        return oldPwdTf;
    }

    /**
     * @param oldPwdTf the oldPwdTf to set
     */
    public void setOldPwdTf(PasswordField oldPwdTf) {
        this.oldPwdTf = oldPwdTf;
    }

    /**
     * @return the newPwd1Tf
     */
    public PasswordField getNewPwd1Tf() {
        return newPwd1Tf;
    }

    /**
     * @param newPwd1Tf the newPwd1Tf to set
     */
    public void setNewPwd1Tf(PasswordField newPwd1Tf) {
        this.newPwd1Tf = newPwd1Tf;
    }

    /**
     * @return the newPwd2Tf
     */
    public PasswordField getNewPwd2Tf() {
        return newPwd2Tf;
    }

    /**
     * @param newPwd2Tf the newPwd2Tf to set
     */
    public void setNewPwd2Tf(PasswordField newPwd2Tf) {
        this.newPwd2Tf = newPwd2Tf;
    }

    @Override
    public void doOkAction() {
        this.verifyPassword();
        this.okCancelListener.doOkAction();
        this.obnovFilterListener.obnovFilter();
        this.refreshViewListener.refreshView();
        
    }

    @Override
    public void doCancelAction() {
        this.okCancelListener.doCancelAction();
        //do nothing
    }

}
