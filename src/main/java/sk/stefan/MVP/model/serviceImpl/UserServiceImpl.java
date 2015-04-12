/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import com.vaadin.ui.Notification;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.UserService;

/**
 *
 * @author stefan
 */
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private final UniRepo<A_User> uniRepo = new UniRepo<>(A_User.class);

    @Override
    public A_User getUserByLogin(String login) {
        if(uniRepo.findByParam("login", login) == null || uniRepo.findByParam("login", login).isEmpty()){
            return null;
        } else {
            return uniRepo.findByParam("login", login).get(0);
        }
    }

    @Override
    public A_User save(A_User user) {
        return uniRepo.save(user);
    }

    @Override
    public void modifyPassword(String paramName, String paramValue, A_User user) {
        try {
            uniRepo.updateParam(paramName, paramValue, "" + user.getId());
        } catch (SQLException ex) {
            log.error(ex);
            Notification.show("Userservice" + ex);
        }
    }

    @Override
    public List<A_User> getAllUsers() {
        return uniRepo.findAll();
    }

    @Override
    public void delete(A_User user) {
        uniRepo.delete(user);
    }

    @Override
    public byte[] getEncPasswordByLogin(String login) {
        if (uniRepo.findByParam("login", login) == null || uniRepo.findByParam("login", login).isEmpty()){
            return null;
        } else {
            A_User user = uniRepo.findByParam("login", login).get(0);
            return user.getPassword();
        }
        
    }
}
