/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.service;

import java.util.List;
import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.entity.A_Role;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;

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
    void initAdmin();

    void saveUserRole(A_UserRole urole, boolean b);


    List<A_User> findAllUsers();

    A_User findOneUser(Integer id);

    A_User saveUser(A_User user);

    A_Role getRoleByRoleType(UserType type);

    /**
     * Vrací všechny role uživatele.
     * @param user uživatel
     * @return role uživatele
     */
    List<A_UserRole> findUserRolesForUser(A_User user);

    /**
     * Vrací aktuální roli uživatele.
     * @param userId id uživatele
     * @return aktuální roli uživatele
     */
    A_UserRole getCurrentUserRoleForUser(Integer userId);

    /**
     * Vrací název role uživatele.
     * @param userRole uživatelská role
     * @return název role uživatele
     */
    String getRoleNameFromUserRole(A_UserRole userRole);
}
