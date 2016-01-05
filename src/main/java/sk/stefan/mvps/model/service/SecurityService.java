/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.service;

import java.sql.SQLException;
import java.util.List;

import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.entity.A_Role;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;

/**
 * Obsluhuje veci okolo prihlasovania a hesiel.
 *
 * @author stefan
 */
public interface SecurityService {

    void login(A_User user);

    void logout();

    A_User getCurrentUser();

    Boolean checkPassword(String rawPassword, byte[] hashPassword);

    byte[] getPassword(Integer id) throws SQLException;

    void updatePassword(String newPwd, String uid) throws SQLException;

    byte[] encryptPassword(String password);

    A_UserRole getActualUserRoleForUser(A_User user);

    List<A_Role> getAvailableRoles();

    boolean currentUserHasRole(UserType userType);
}
