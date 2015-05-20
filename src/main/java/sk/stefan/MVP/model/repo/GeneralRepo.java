/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.repo;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.entity.A_Change;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.A_UserRole;
import sk.stefan.MVP.model.entity.Document;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.utils.ToolsFiltering;

/**
 * Vrstva komunikujuca s na jednej strane s databazovymi nastrojmi (jdbc)
 * a na druhej strane so systemom (servisom).
 * 
 * Zahrna metody, ktore nie su zavisle od Triedy entity.
 *
 * @author stefan
 */
public class GeneralRepo {

    private static final Logger log = Logger.getLogger(GeneralRepo.class);

    private final SecurityService securityService;

    private Connection transactionalConn;

    private final UniRepo<A_User> userRepo;

    private final UniRepo<A_UserRole> userRoleRepo;

    private final UniRepo<Document> docRepo;

    private UniRepo<A_Change> changeRepo;
    
    //0. konstruktor.
    /**
     *
     */
    public GeneralRepo() {

        this.securityService = new SecurityServiceImpl();
        this.docRepo = new UniRepo<>(Document.class);
        this.userRepo = new UniRepo<>(A_User.class);
        this.userRoleRepo = new UniRepo<>(A_UserRole.class);

    }

// ****************** NON INVASIVE **************************************    
    /**
     * Modifikuje iba password. Len pre tabulku a_user;
     *
     * @param id id of row where the value is updated.
     * @return
     *
     * @throws java.sql.SQLException
     */
    public byte[] getPassword(String id) throws SQLException {

        Connection conn;
        PreparedStatement st;
        ResultSet rs;
        byte[] hash = null;
        String sql;
        
        sql = "SELECT password FROM a_user WHERE id = ?";
        
        try {
            conn = DoDBconn.createNoninvasiveConnection();
            st = conn.prepareStatement(sql);

            st.setString(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                hash = rs.getBytes("password");
            }

            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);

            return hash;

        } catch (SQLException ex) {
            log.error(ex.getLocalizedMessage(), ex);
            throw new SQLException(ex);
        } 
        
    }

    /**
     * Specificka metoda pre filtrovacie ucely. 
     * Vrati id pre dany sql dotaz.
     *
     * @param sql
     * @return 
     */
    public List<Integer> findIds(String sql) {

        Connection conn;
        Statement st;
        ResultSet rs;

        try {
            conn = DoDBconn.createNoninvasiveConnection();
            st = conn.createStatement();

            List<Integer> listIds;
            log.info("MEGASQL:*" + sql + "*");
            rs = st.executeQuery(sql);
            
            listIds = fillListIds(rs);
            
            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);

            return listIds;

        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            log.error(e.getMessage(), e);
            return null;
        } 
    }

    /**
     * Najde hladane parametre vo vsetkych tabulkach. 
     *
     * Kryje sa sice s metodou findbyparam triedy UniRepo, 
     * ale viac vyhovuje poziadavkam na automaticku deaktivaciu 
     * entit pri deaktivacii korenovej entity. 
     * Pretoze tato deaktivacia bude dennym chlebom, ponechavam 
     * aj tuto specifickejsiu metodu.
     *
     * @param tn
     * @param paramName
     * @param paramVal
     * @return
     */
    public List<Integer> findTnAllByParam(String tn, String paramName, String paramVal) {

        Connection conn;
        Statement st;
        ResultSet rs;

        List<Integer> listIds;

        String sql = "SELECT id FROM " + tn + " WHERE " + paramName + " = '" + paramVal
                + "' AND visible = true";
        
        log.info("**" + sql + "*");

        try {
            conn = DoDBconn.createNoninvasiveConnection();
            st = conn.createStatement();

            rs = st.executeQuery(sql);

            listIds = fillListIds(rs);

            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);

            
            return listIds;
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            
            log.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * Metoda na najdenie dobrovolnikov s seba. Metoda sa vyuzije pri administracii uzivatelov.
     * 
     * @param userId
     * @return
     */
    public List<Integer> findMeAndAllVolunteers(Integer userId) {

        //list of user ids:
        List<Integer> listIds;

//        najdi vsetkych uzivatelov s aktualnou rolou dobrovolnik. + sameho seba. 
        String sql = "select u.id from a_user u JOIN a_user_role ur ON(u.id = ur.user_id) "
                + " where ur.role_id = 1 OR u.id = " + userId
                + " AND ur.actual = true"
                + " AND ur.visible = true"
                + " AND u.visible = true";

        Connection conn;
        Statement st;
        ResultSet rs;

        try {
            conn = DoDBconn.createNoninvasiveConnection();
            st = conn.createStatement();
            
            rs = st.executeQuery(sql);

            listIds = fillListIds(rs);

            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);
            
            return listIds;

        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

// ****************** INVASIVE **************************************    
    /**
     * Toto netreba ukladat do a_change!!!
     *
     * Modifikuje iba password. Vynimka z univerzalnosti, plati len pre tabulku
     * a_user;
     *
     * @param rawPwd
     * @param userId id of user where the password is updated.
     *
     * @throws java.sql.SQLException
     */
    public void updatePassword(String rawPwd, String userId) throws SQLException {

        log.info("TERAZ POJDEM VYTVORIT INVAZIVNE CONN" + DoDBconn.count);
        
        Connection conn = DoDBconn.createInvasiveConnection();
        PreparedStatement st;
        
        try {
            st = conn.prepareStatement("UPDATE a_user SET password = ? WHERE id = " + userId);
            st.setBytes(1, securityService.encryptPassword(rawPwd));
            st.executeUpdate();

            st.close();
            conn.commit();
            DoDBconn.releaseConnection(conn);

        } catch (SQLException ex) {
            
            log.error(ex.getMessage(), ex);
            throw new SQLException();
        }
    }

    /**
     * Deaktivuje cely strom entit, pricom vrcholom stromu je entita na vstupe.
     *
     * @param tn
     * @param id
     * @throws java.sql.SQLException
     */
    public void deactivateWithSlavesTree(String tn, Integer id) throws SQLException {

        if (transactionalConn == null) {
            log.info("TERAZ POJDEM VYTVORIT INVAZIVNE CONN" + DoDBconn.count);
            transactionalConn = DoDBconn.createInvasiveConnection();
        }

        changeRepo = new UniRepo<>(A_Change.class, transactionalConn);

        this.deactivateOne(tn, id);
        //deactivate documents;
        this.deactivateEntityDocuments(tn, id);

        List<String> slaveTns = ToolsFiltering.getSlaves(tn);

        if (!slaveTns.isEmpty()) {

            Map<String, List<Integer>> slavesIdsMap = new HashMap<>();
            List<Integer> slvIds;

            String paramN = ToolsFiltering.getParamName(tn);

            for (String slv : slaveTns) {
                slvIds = this.findTnAllByParam(slv, paramN, "" + id);
                slavesIdsMap.put(slv, slvIds);

            }
            
            //      najdime dalsich pod-otrokov:
            for (String key : slavesIdsMap.keySet()) {
                slvIds = slavesIdsMap.get(key);
                for (Integer aid : slvIds) {
                    deactivateWithSlavesTree(key, aid);
                }
            }
        }
    }

    /**
     * POZOR!!! NEpouzivat na inom mieste, inak sa musi preimplementovat aj
     * zapis do a_change!!!
     * 
     * Deaktivuje len parameter visible. Kryje sa sice s DeactivateOblyOne UniRepa,
     * ale lepsie vyhovuje potrebam deaktivacie stromu entit (pretoze tam sa
     * dopredu nevie akeho typu etita bude - to by robilo zbytocne probemy pri 
     * tvorbe generickeho UniRepa.)
     *
     * @param tn
     * @param entId id entity, ktoru chceme deaktivovat.
     *
     * @throws java.sql.SQLException
     */
    private void deactivateOne(String tn, Integer entId) throws SQLException {

        Statement st = transactionalConn.createStatement();

        String sql = String.format("UPDATE %s SET visible = false WHERE id = %d", tn, entId);
        st.executeUpdate(sql);

        //for creating change:
        A_Change change = createDeactivateChangeToPersist(tn, entId);
        changeRepo.save(change, false);

        st.close();

    }

    /**
     * POZOR!!! NEpouzivat na inom mieste, inak sa musi preimplementovat aj
     * zapis do a_change!!!
     *
     * Deactivates all documents related to entity.
     *
     * @param tn
     * @param rid
     * @throws java.sql.SQLException
     */
    private void deactivateEntityDocuments(String tn, Integer rid) throws SQLException {

        Statement st = transactionalConn.createStatement();

        String sql = String.format("UPDATE t_document "
                + " SET visible = false WHERE table_name = '%s' AND table_row_id = %d", tn, rid);
        log.info("*" + sql + "**");
        st.executeUpdate(sql);

        //for creating change:
        A_Change change;
        List<Document> touchedDocumetsIds = docRepo.findByTwoParams("table_name", tn, "table_row_id", rid + "");
        for (Document doc : touchedDocumetsIds) {
            change = createDeactivateChangeToPersist("t_document", doc.getId());
            changeRepo.save(change, false);
        }

        st.close();
    }

    /**
     * commituje zmeny do DB.
     */
    public void doCommit() {
        try {
            transactionalConn.commit();
        } catch (SQLException ex) {
            try {
                transactionalConn.rollback();
                DoDBconn.releaseConnection(transactionalConn);
                transactionalConn = null;

                log.error(ex.getMessage(), ex);
//                Notification.show("Ukladanie sa nevydarilo, SPRAVIL SA ROLLBACK!");
            } catch (SQLException ex1) {
                log.error(ex.getMessage(), ex);
//                Notification.show("ANI TEN BLBY ROLLBACK SA NEPODARIL!");
            }
        }
    }

    /**
     * commituje zmeny do DB.
     */
    public void doRollback() {
        try {
            transactionalConn.rollback();
            DoDBconn.releaseConnection(transactionalConn);
            transactionalConn = null;

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
//            Notification.show("Rollback sa nevydaril!");

        }
    }

    //************************************************
//    POMOCNE FUNKCIE:
    //************************************************
    /**
     * pomocna metoda pre filtrovanie. Najde zoznam id pre dany resultset.
     * 
     * @param rs
     * @return 
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
     * Vytvori instanciu zmeny, pre deaktivaciu daneho riadku v tabulke tn.
     * 
     * @param tn
     * @param rowId
     * @return 
     */
    public A_Change createDeactivateChangeToPersist(String tn, Integer rowId) {

        A_User user = UI.getCurrent().getSession().getAttribute(A_User.class);

        Integer userId;// = 2;
        A_Change zmena;

        if (user == null) {
            log.warn("This shouldnt be possible!!");
            return null;
        } else {
            userId = user.getId();
        }

        zmena = new A_Change();
        zmena.setUser_id(userId);
        zmena.setTable_name(tn);
        zmena.setColumn_name("visible");
        zmena.setRow_id(rowId);
        zmena.setOld_value("true");
        zmena.setNew_value("false");
        zmena.setVisible(Boolean.TRUE);

        return zmena;

    }

    
    /**
     * Tato metoda inicializuje tj. vytvori uzivatel admin. a zmeni hesla uzivatelov
     * pridanych cez skript (.._initB.sql).
     * Je to z toho dvovodu, ze mysql md5() vytvori(koduje) nieco uplne ine ako 
     * Java md5(). Nemam cas riesit preco, takze riesim to takto.
     * 
     */
    public void initAdmin() {

        byte[] encPassword;

        A_User admin = new A_User();

        admin.setFirst_name("admin");
        admin.setLast_name("adminovic");
        admin.setLogin("admin");
        admin.setE_mail("admin@admin.sk");
        admin.setVisible(Boolean.TRUE);

        encPassword = securityService.encryptPassword("admin");
        admin.setPassword(encPassword);

        admin = userRepo.save(admin, false);

        //user Role:
        A_UserRole urole = new A_UserRole();

        urole.setUser_id(admin.getId());
        urole.setRole_id(2);
        urole.setSince(new Date());
        urole.setTill(null);
        urole.setVisible(Boolean.TRUE);
        urole.setActual(Boolean.TRUE);

        userRoleRepo.save(urole, false);

        try {
            this.updatePassword("petak", "1");
            this.updatePassword("stefan", "2");
            this.updatePassword("miro", "3");
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }

    }
}
