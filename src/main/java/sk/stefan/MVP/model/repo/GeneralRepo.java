/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.repo;

import com.vaadin.ui.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;

/**
 * zahrna metody, ktore su univerzalne, ale nie su zavisle od Triedy entity.
 *
 * @author stefan
 */
public class GeneralRepo {

    private static final Logger log = Logger.getLogger(GeneralRepo.class);

    private final SecurityService securityService;

    private Connection transConn;

    public GeneralRepo() {

        this.securityService = new SecurityServiceImpl();
        if (transConn == null) {
            transConn = DoDBconn.getConnection();
        }

    }

    //Je to sice metoda, ktora 
    /**
     * @param tn
     * @param id
     *
     * @throws java.sql.SQLException
     */
//    public static synchronized boolean deactivate(String tn, Integer id) {
    public void deactivateOne(String tn, Integer id) throws SQLException {

        Statement st = transConn.createStatement();

        String sql = String.format("UPDATE %s SET visible = false WHERE id = %d", tn, id);
        st.executeUpdate(sql);

        st.close();

    }

    /**
     * Modifikuje iba password. Len pre tabulku a_user;
     *
     * @param id id of row where the value is updated.
     * @return
     *
     * @throws java.sql.SQLException
     */
    public byte[] getPassword(String id) throws SQLException {

        PreparedStatement st = null;
        byte[] hash = null;
        String sql;
        sql = "SELECT password FROM a_user WHERE id = ?";
        try {
            if (DoDBconn.getNonInvasiveConn() == null) {
                DoDBconn.createNoninvasiveConnection();
            }
            st = DoDBconn.getNonInvasiveConn().prepareStatement(sql);
            st.setString(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                hash = result.getBytes("password");
            }
            return hash;
        } catch (SQLException ex) {
            log.error(ex.getLocalizedMessage(), ex);
            Notification.show("nieco sa dojebalo pri ziskavani hesla z DB!", Notification.Type.ERROR_MESSAGE);
            throw new SQLException(ex);
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    /**
     * Modifikuje iba password. Vynimka z univerzalnosti, plati len pre tabulku
     * a_user;
     *
     * @param rawPwd
     * @param id id of row where the value is updated.
     *
     * @throws java.sql.SQLException
     */
    public void updatePassword(String rawPwd, String id) throws SQLException {

        Connection conn = DoDBconn.getConnection();

        PreparedStatement st;
        try {
            st = conn.prepareStatement("UPDATE a_user SET password = ? WHERE id = " + id);
            st.setBytes(1, securityService.encryptPassword(rawPwd));
            st.executeUpdate();

            conn.commit();
            st.close();

            DoDBconn.releaseConnection(conn);

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("nieco sa dojebalo pri update hesla!", Notification.Type.ERROR_MESSAGE);
            throw new SQLException();
        }
    }

    /**
     * For specific filtering purposes.
     *
     * @param sql
     * @return
     */
    public List<Integer> findIds(String sql) {
        try {
            if (DoDBconn.getNonInvasiveConn() == null) {
                DoDBconn.createNoninvasiveConnection();
            }
            Statement st;
            st = DoDBconn.getNonInvasiveConn().createStatement();
            List<Integer> listIds;
            log.info("MEGASQL:*" + sql + "*");
            ResultSet rs;
            rs = st.executeQuery(sql);
//            log.info("KAROLKO RESULT SET is null?: " + (rs==null));
            listIds = fillListIds(rs);
            rs.close();
            st.close();
            return listIds;
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            Notification.show("Chyba, filterRepo::findAllIds(...)", Notification.Type.ERROR_MESSAGE);
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Najde hladane parametre vo vsetkych tabulkach.
     *
     * @param tn
     * @param paramName
     * @param paramVal
     * @return
     */
    public List<Integer> findTnAllByParam(String tn, String paramName, String paramVal) {

        List<Integer> listIds;

//        st = DoDBconn.getConn().prepareStatement("UPDATE a_user SET password = ? WHERE id = " + id);
        String sql = "SELECT id FROM " + tn + " WHERE " + paramName + " = " + paramVal +
                " AND visible = true";
        ResultSet rs;
        Statement st;

        try {
            if (DoDBconn.getNonInvasiveConn() == null) {
                DoDBconn.createNoninvasiveConnection();
            }
            st = DoDBconn.getNonInvasiveConn().createStatement();
            rs = st.executeQuery(sql);

            listIds = fillListIds(rs);

            rs.close();
            st.close();
            return listIds;
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            Notification.show("findTnAllByParam(...)", Notification.Type.ERROR_MESSAGE);
            log.error(e.getMessage(), e);
            return null;
        }

    }

    //************************************************
//    POMOCNE FUNKCIE:
    //************************************************
    /**
     * pomocna metoda pre filtrovanie
     */
    private List<Integer> fillListIds(ResultSet rs) {

        List<Integer> listIds = new ArrayList<>();
        int id;
        int i = 0;
        try {
            while (rs.next()) {                
//                log.info("KAMILKO: " + i);
                i++;
                id = rs.getInt("id");
                listIds.add(id);
            }
            return listIds;
            
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            Notification.show("Chyba, FilterRepo, fillListIds(ResultSet rs)", Notification.Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    /**
     * commituje zmeny do DB.
     */
    public void doCommit() {
        try {
            transConn.commit();
        } catch (SQLException ex) {
            try {
                transConn.rollback();
                DoDBconn.releaseConnection(transConn);

                log.error(ex.getMessage(), ex);
                Notification.show("Ukladanie sa nevydarilo, SPRAVIL SA ROLLBACK!");
            } catch (SQLException ex1) {
                log.error(ex.getMessage(), ex);
                Notification.show("ANI TEN BLBY ROLLBACK SA NEPODARIL!");
            }
        }
    }

    /**
     * commituje zmeny do DB.
     */
    public void doRollback() {
        try {
            transConn.rollback();
            DoDBconn.releaseConnection(transConn);

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("Rollback sa nevydaril!");

        }
    }
    
    public List<Integer> findMeAdminAndAllVolunteers(Integer adminId){
    
        List<Integer> uIds;
        
//        najdi vsetkych uzivatelov s aktualnou rolou dobrovolnik. + sameho seba (pohladu aministratora). 
        String sql = "select u.id from a_user u JOIN a_user_role ur ON(u.id = ur.user_id) "
                + " where ur.role_id = 1 "
                + " AND ur.actual = true "
                + " AND u.active = true";
        
        return null;
        
        
    }

//    /**
//     * Inputs file into the database.
//     *
//     * @param inputStream
//     * @param tn
//     * @param rid
//     * @param fn
//     * @return
//     */
//    public Document insertFileInDB(InputStream inputStream, String fn, String tn, Integer rid) {
//
//        Document doc = new Document();
//
//        Connection conn = DoDBconn.getConnection();
//
//        PreparedStatement st;
//
//        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String inputDate = formatter.format(new Date());
//
//        String sql = "INSERT INTO t_document (document, file_name, table_name, table_row_id, upload_date) "
//                + " values (?, ?, ?, ?, ?) ";
//        log.debug("SQL :*" + sql + "*");
//
//        try {
//            st = conn.prepareStatement(sql);
//
//            st.setBlob(1, inputStream);
////            st.setBytes(1, inputStream);
////            
//            st.setString(2, fn);
//            st.setString(3, tn);
//            st.setInt(4, rid);
//            st.setString(5, inputDate);
//
//            st.executeUpdate();
//
//            ResultSet rs = st.getGeneratedKeys();
//            if (rs.next()) {
//                int iid = rs.getInt(1);
//                log.debug("PRIDELENE ID: " + iid);
//            }
////            int iid = rs.getInt(1);
//
////             
//            conn.commit();
//            st.close();
//            DoDBconn.releaseConnection(conn);
//
//            return doc;
//        } catch (SecurityException | IllegalArgumentException | SQLException e) {
//
//            Notification.show("insertFile(...)", Notification.Type.ERROR_MESSAGE);
//            log.error(e.getMessage(), e);
//            return null;
//        }
//    }

    /**
     * Deactivates all documents related to entity.
     * @param tn
     * @param rid
     * @throws java.sql.SQLException
     */
    public void deactivateEntityDocuments(String tn, Integer rid) throws SQLException {
        
        Statement st = transConn.createStatement();

        String sql = String.format("UPDATE t_document "
                + " SET visible = false WHERE table_name = %s AND table_row_id = %d", tn, rid);
        st.executeUpdate(sql);

        st.close();
    }

}
