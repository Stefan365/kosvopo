/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.sql.SQLException;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.enums.UserType;

/**
 * Obsluhuje veci okolo prihlasovania a hesiel. 
 * 
 * @author stefan
 */
public interface SecurityService {

    public void login(A_User user);

    public void logout();

    public A_User getCurrentUser();

    public Boolean checkPassword(String rawPassword, byte[] hashPassword);

    public byte[] getPassword(Integer id) throws SQLException;

    public void updatePassword(String newPwd, String uid) throws SQLException;
 
    public byte[] encryptPassword(String password);

    
}
