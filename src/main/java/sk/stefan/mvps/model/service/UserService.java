/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.service;

import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;
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
     
    public String getUserRolePresentationName(A_UserRole usrRole);
    
    /**
     * Zistuje, ci je v DB user admin.
     * @return 
     */
    public Boolean isThereAdmin();

    /**
     * Pokial nieje tak ho touto metodou vytvori.
     */
    public void initAdmin();

    public void saveUserRole(A_UserRole urole, boolean b);

    
}
