/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.repo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;

/**
 *
 * @author stefan
 */
public class GeneralRepo {
    
    private static final Logger log = Logger.getLogger(GeneralRepo.class);
    
    /**
     * @param tn
     * @param id
     * @return
     */
//    public static synchronized boolean deactivate(String tn, Integer id) {
    public boolean deactivate(String tn, Integer id) {
        try {
            Connection conn = DoDBconn.getConnection();
            Statement st = conn.createStatement();

            String sql;
            
            sql = String.format("UPDATE %s SET visible = false WHERE id = %d", tn,id);
            st.executeUpdate(sql);
            
            st.close();
//            conn.commit(); //uz je nastaveny autocommit!
            DoDBconn.releaseConnection(conn);
            return true;

            
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return false;
        }

    }

}
