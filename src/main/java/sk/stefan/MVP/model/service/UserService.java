/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.enums.UserType;

/**
 *
 * @author stefan
 */
public interface UserService {
    
    /**
     * Vrátí uživatele podle loginu.
     * @param login login uživatele
     * @return instanci uživatele, pokud v databázi existuje, jinak vrací null
     */
    A_User getUserByLogin(String login);
    
    /**
     * Vrací hash hesla uživatele podle e-mailu.
     * @param login email uživatele
     * @return hash hesla
     */
    public byte[] getEncPasswordByLogin(String login);

    public UserType getUserType(A_User user);
     
    
}
