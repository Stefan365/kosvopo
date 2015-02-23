/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import sk.stefan.MVP.model.entity.dao.User;

/**
 *
 * @author stefan
 */
public interface SecurityService {

    public void login(User user);

    public void logout();

    public User getCurrentUser();

    public boolean checkPassword(String rawPassword, byte[] hashPassword);

    public byte[] encryptPassword(String password);
    
}
