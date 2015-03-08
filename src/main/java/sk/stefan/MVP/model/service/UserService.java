/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.dao.A_User;

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
     * Uloží nového nebo aktualizuje stávajícího uživatele. Vrací jeho aktuální
     * verzi v databázi.
     * @param user uživatel k uložení do databáze
     * @return aktuální verzi uživatele
     */
    A_User save(A_User user);
    
    
    /**
     * Změní heslo uživatele.
     * @param paramName
     * @param paramValue
     * @param user uživatel, ktermy ma jiz zmeneny vnitrni parametr hesla.
     */
    public void modifyPassword(String paramName, String paramValue, A_User user);
    
    /**
     * Vrátí seznam všech uživatelů.
     * @return seznam vrácených uživatelů.
     */
    List<A_User> getAllUsers();
    
    /**
     * Smaže uživatele a jeho úkoly.
     * @param user mazaný uživatel.     * 
     */
    public void delete(A_User user);    

    /**
     * Vrací hash hesla uživatele podle e-mailu.
     * @param login email uživatele
     * @return hash hesla
     */
    public byte[] getEncPasswordByLogin(String login);

    
}
