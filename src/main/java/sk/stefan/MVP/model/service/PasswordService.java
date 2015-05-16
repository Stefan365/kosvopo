/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.sql.SQLException;

/**
 *
 * @author stefan
 */
public interface PasswordService {

    public byte[] getPassword(Integer id) throws SQLException;
    
}
