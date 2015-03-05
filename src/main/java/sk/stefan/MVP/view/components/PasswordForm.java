/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.data.Item;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.User;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.SecurityServiceImpl;

/**
 *
 * @author stefan
 */
public class PasswordForm extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(PasswordForm.class);

    private TextField oldPwdTf;
    private TextField newPwd1Tf;
    private TextField newPwd2Tf;

    private final Item item;
    private final UniRepo<User> uniRepo;

    private final SecurityService securityService;

    public PasswordForm(Item it) {
        this.item = it;
        this.securityService = new SecurityServiceImpl();
        this.uniRepo = new UniRepo<>(User.class);
    }

    private void initLayout() {

        oldPwdTf = new TextField("staré heslo");
        newPwd1Tf = new TextField("nové heslo");
        newPwd2Tf = new TextField("potvrdenie nového hesla");

        this.addComponent(oldPwdTf);
        this.addComponent(newPwd1Tf);
        this.addComponent(newPwd2Tf);

    }

    public Boolean verifyPassword() {
        //1. podmienka: stare heslo je spravne.
//        item.getItemProperty("id").getValue()
        Integer id = (Integer) item.getItemProperty("id").getValue();
        String rawPwd = oldPwdTf.getValue();

        try {
            byte[] pwd = uniRepo.getPassword("" + id);
            boolean isGood = securityService.checkPassword(rawPwd, pwd);
            if (isGood && newPwd1Tf.getValue().equals(newPwd2Tf.getValue())) {
                addToDB(newPwd1Tf.getValue(), "" + id);
            }
            Notification.show("heslo úspešne zmenené!");
            return Boolean.TRUE;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("Zmena hesla sa nepodarila");
            return Boolean.FALSE;
        }
    }

    private void addToDB(String newPwd, String id) throws SQLException {
        uniRepo.updatePassword(newPwd, id);
    }

    /**
     * @return the oldPwdTf
     */
    public TextField getOldPwdTf() {
        return oldPwdTf;
    }

    /**
     * @param oldPwdTf the oldPwdTf to set
     */
    public void setOldPwdTf(TextField oldPwdTf) {
        this.oldPwdTf = oldPwdTf;
    }

    /**
     * @return the newPwd1Tf
     */
    public TextField getNewPwd1Tf() {
        return newPwd1Tf;
    }

    /**
     * @param newPwd1Tf the newPwd1Tf to set
     */
    public void setNewPwd1Tf(TextField newPwd1Tf) {
        this.newPwd1Tf = newPwd1Tf;
    }

    /**
     * @return the newPwd2Tf
     */
    public TextField getNewPwd2Tf() {
        return newPwd2Tf;
    }

    /**
     * @param newPwd2Tf the newPwd2Tf to set
     */
    public void setNewPwd2Tf(TextField newPwd2Tf) {
        this.newPwd2Tf = newPwd2Tf;
    }

}
