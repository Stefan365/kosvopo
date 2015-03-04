/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import com.vaadin.ui.Notification;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.User;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.InputFormLayout;

/**
 *
 * @author stefan
 */
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private final UniRepo<User> uniRepo = new UniRepo<>(User.class);

    @Override
    public User getUserByLogin(String login) {
        if(uniRepo.findByParam("login", login) == null || uniRepo.findByParam("login", login).isEmpty()){
            return null;
        } else {
            return uniRepo.findByParam("login", login).get(0);
        }
    }

    @Override
    public User save(User user) {
        return uniRepo.save(user);
    }

    @Override
    public void modifyPassword(String paramName, String paramValue, User user) {
        try {
            uniRepo.updateParam(paramName, paramValue, "" + user.getId());
        } catch (SQLException ex) {
            log.error(ex);
            Notification.show("Userservice" + ex);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return uniRepo.findAll();
    }

    @Override
    public void delete(User user) {
        uniRepo.delete(user);
    }

    @Override
    public byte[] getEncPasswordByLogin(String login) {
        if (uniRepo.findByParam("login", login) == null || uniRepo.findByParam("login", login).isEmpty()){
            return null;
        } else {
            User user = uniRepo.findByParam("login", login).get(0);
            return user.getPassword();
        }
        
    }
}
