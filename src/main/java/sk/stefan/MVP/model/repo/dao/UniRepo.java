package sk.stefan.MVP.model.repo.dao;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.interfaces.MyRepo;
import sk.stefan.utils.ToolsDao;

public class UniRepo<E> implements MyRepo<E> {

    private static final Logger log = Logger.getLogger(UniRepo.class);
    // table name:
    private final String TN;
    private final Class<?> clsE;

    //connection musi byt centralne, inak nenudu fungovat transactional operacie
    //bude tam odkaz na DoDBconn.conn
//    private static Connection conn = DoDBconn.getConn();
    /**
     * Konstruktor:
     *
     * @param cls
     */
    public UniRepo(Class<?> cls) {

        this.clsE = cls;
        this.TN = ToolsDao.getTableName(cls);
        if (DoDBconn.getNonInvasiveConn() == null) {
            DoDBconn.createNoninvasiveConnection();
        }

    }

    // 1.
    /**
     *
     * @return
     */
    @Override
    public List<E> findAll() {
        try {
//            Connection conn = DoDBconn.getConnection();
            Statement st;
            //pre pripad, ze by spojenie spadlo.
            if (DoDBconn.getNonInvasiveConn() == null) {
                DoDBconn.createNoninvasiveConnection();
            }
            st = DoDBconn.getNonInvasiveConn().createStatement();

            String sql;
            if (TN.contains("a_")) {
                sql = String.format("SELECT * FROM %s", TN);
            } else {
                sql = String.format("SELECT * FROM %s  WHERE visible = true", TN);
            }

            ResultSet rs;
            rs = st.executeQuery(sql);
            //log.info("PETER 7: " + rs.getClass().getCanonicalName());
            //log.info("PETER 8:  " + (rs != null));

            //JDBC4ResultSet
            //log.info(sql + " DONE!");
            List<E> listEnt = this.fillListEntity(rs);

            rs.close();
            st.close();
//            DoDBconn.releaseConnection(conn);

            return listEnt;
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            Notification.show("Chyba, uniRepo::findAll(...)",
                    Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            return null;
        }

    }

    // 2.
    @Override
    public E findOne(Integer id) {
        try {
            //pre pripad, ze by spojenie spadlo.
            if (DoDBconn.getNonInvasiveConn() == null) {
                DoDBconn.createNoninvasiveConnection();
            }

            Statement st;
            st = DoDBconn.getNonInvasiveConn().createStatement();

            String sql;
            if (TN.contains("a_")) {
                sql = String.format("SELECT * FROM %s WHERE id = %d", TN, id);
            } else {
                sql = String.format("SELECT * FROM %s WHERE id = %d AND visible = true", TN, id);
            }

            ResultSet rs;
            rs = st.executeQuery(sql);
            // Notification.show(sql);
//            log.info(sql);
            E ent = this.fillEntity(rs);

            rs.close();
            st.close();
//            DoDBconn.getPool().releaseConnection(conn);

            return ent;
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            Notification.show("Chyba, uniRepo::findOne(...)",
                    Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            return null;
        }

    }

    // 3.
    /**
     * Najde entitu podla zadaneho parametra.
     *
     * @param paramName
     * @param paramValue
     * @return
     */
    @Override
    public List<E> findByParam(String paramName, String paramValue) {

        try {
            //pre pripad, ze by spojenie spadlo.
            if (DoDBconn.getNonInvasiveConn() == null) {
                DoDBconn.createNoninvasiveConnection();
            }
            Statement st;
            st = DoDBconn.getNonInvasiveConn().createStatement();

            StringBuilder sql = new StringBuilder();

            sql.append(String.format("SELECT * FROM %s WHERE ", TN));
            sql.append(this.getFindByParamQuery(paramName, paramValue));
            if (TN.contains("a_")) {
                //do nothing
            } else {
                sql.append(" AND visible = true");
            }

            log.info("SQL: *" + sql.toString() + "*");
            ResultSet rs;

            rs = st.executeQuery(sql.toString());

            List<E> listEnt = this.fillListEntity(rs);

            rs.close();
            st.close();

            return listEnt;

        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            Notification.show("Chyba, uniRepo::findByParam(...)",
                    Type.ERROR_MESSAGE);
            log.error(e.getMessage());
            return null;
        }

    }

    // 3.
    /**
     * Najde entitu podla zadaneho parametra.
     *
     * @param p1Name
     * @param p1Value
     * @param p2Name
     * @param p2Value
     * @return
     */
    @Override
    public List<E> findByTwoParams(String p1Name, String p1Value,
            String p2Name, String p2Value) {

        try {
            //pre pripad, ze by spojenie spadlo.
            if (DoDBconn.getNonInvasiveConn() == null) {
                DoDBconn.createNoninvasiveConnection();
            }
            Statement st;
            st = DoDBconn.getNonInvasiveConn().createStatement();

            // Notification.show("SOM TU!");
            StringBuilder sql = new StringBuilder();

            sql.append(String.format("SELECT * FROM %s WHERE ", TN));
            sql.append(this.getFindByParamQuery(p1Name, p1Value));
            sql.append(" AND ");
            sql.append(this.getFindByParamQuery(p2Name, p2Value));
            if (TN.contains("a_")) {
                //do nothing
            } else {
                sql.append(" AND visible = true");
            }

            ResultSet rs;
            rs = st.executeQuery(sql.toString());

            log.info("SQL: *" + sql.toString() + "*");
            List<E> listEnt = this.fillListEntity(rs);

            rs.close();
            st.close();
//            DoDBconn.releaseConnection(conn);

            return listEnt;

        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            Notification.show("Chyba, uniRepo::findByParam(...)",
                    Type.ERROR_MESSAGE);
            log.error(e.getMessage());
            return null;
        }

    }

    /**
     * Vrati SQL dotaz, pre zadany parameter a jeho hodnotu.
     *
     * @param pn parameter name
     * @param pv parameter value
     * @return
     */
    private String getFindByParamQuery(String pn, String pv) {

        String sql;

        if (pv == null) {
            sql = String.format(" %s is NULL ", pn);
        } else if ("null".equalsIgnoreCase(pv) || "true".equalsIgnoreCase(pv) || "false".equalsIgnoreCase(pv)) {
            sql = String.format(" %s = %s ", pn, pv);
        } else {
            sql = String.format(" %s = '%s'", pn, pv);
        }

        return sql;

    }


    // 4.
    /**
     * Vracia presne tu istu entitu, len ulozenu. tj. ten isty pointer.
     *
     * @param ent
     * @return
     */
    @Override
    public E save(E ent) {
        try {

            Connection conn = DoDBconn.getConnection();
            PreparedStatement st;
            Map<String, Class<?>> mapPar;
            String sql;

            mapPar = ToolsDao.getTypParametrov(clsE);

            Integer eid = this.isNew(ent);
            boolean novy = (eid == null);

            if (novy) {
                sql = this.createInsertQueryPrepared(mapPar);
            } else {
                sql = this.createUpdateQueryPrepared(mapPar, eid);
            }
            st = this.createStatement(mapPar, conn, sql, ent);

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            if (novy && rs.next()) {
                Integer newId = rs.getInt(1);
                Method entMethod = clsE.getMethod("setId", Integer.class);

                entMethod.invoke(ent, newId);
            }

            conn.commit();
            rs.close();
            st.close();

            DoDBconn.releaseConnection(conn);

            return ent;

        } catch (IllegalAccessException | NoSuchFieldException |
                SecurityException | NoSuchMethodException |
                IllegalArgumentException | InvocationTargetException | SQLException e) {
//            Notification.show("Chyba, uniRepo::save(...)", Type.ERROR_MESSAGE);
            log.error(e.getMessage(), e);
            return null;
        }

    }


// 5.
    /**
     * @param ent
     * @return
     */
    @Override
    public boolean delete(E ent) {
        try {

            Connection conn = DoDBconn.getConnection();

            Statement st = conn.createStatement();

            Integer id = null;

            if (ent != null) {
                Method entMethod = clsE.getMethod("getId");
                id = (Integer) entMethod.invoke(ent);
            }

            if (id != null) {
                String sql = String.format("DELETE FROM %s WHERE id = %d", TN, id);
                st.executeUpdate(sql);
                log.info("DELETE:" + sql);
            }
            conn.commit();
            st.close();

            DoDBconn.releaseConnection(conn);
            return true;

        } catch (IllegalAccessException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            Notification
                    .show("Chyba, uniRepo::delete(non SQL exception)", Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            return false;
        } catch (SQLException e) {
//            Notification.show("Chyba, uniRepo::delete(SQL exception)", Type.ERROR_MESSAGE);
            Notification.show("Danu entitu nieje mozne zatial vymazat, vymaz dalsie podentity");
            log.error(e.getLocalizedMessage(), e);
            return false;
        }

    }

    // 5.B
    /**
     * deaktivuje len 1 entitu.
     * pokial potrebujete deaktivovat cely strom zavislych entit, 
     * pouzite generalRepo...deactivateAll
     * 
     * @param ent
     * @return
     */
    @Override
    public boolean deactivate(E ent) {

        Connection conn = DoDBconn.getConnection();

        try {

            Statement st = conn.createStatement();

            Integer id = null;

            if (ent != null) {
                Method entMethod = clsE.getMethod("getId");
                id = (Integer) entMethod.invoke(ent);
            }

            if (id != null) {
                String sql = String.format("UPDATE %s SET visible = false WHERE id = %d", TN, id);
                st.executeUpdate(sql);
            }

            conn.commit();

            st.close();
            DoDBconn.releaseConnection(conn);
            return true;

        } catch (SQLException | IllegalAccessException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            Notification
                    .show("Chyba, uniRepo::delete(non SQL exception)", Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            return false;
        }

    }

    /**
     * Modifikuje iba specifikovany parameter.
     *
     * @param paramName the name of parameter.
     * @param paramValue the new value of parameter
     * @param id id of row where the value is updated.
     * @throws java.sql.SQLException
     */
    public void updateParam(String paramName, String paramValue, String id) throws SQLException {

        Connection conn = DoDBconn.getConnection();
        Statement st = null;
        try {

            st = conn.createStatement();

            StringBuilder sql = new StringBuilder();

            sql.append(String.format("UPDATE %s SET ", TN));
            sql.append(this.getFindByParamQuery(paramName, paramValue));
            sql.append(String.format(" WHERE id =%s", id));

            log.info("SQL:*" + sql + "*");
            int i = st.executeUpdate(sql.toString());

            conn.commit();
            DoDBconn.releaseConnection(conn);

        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            Notification.show("Chyba, uniRepo::findByParam(...)",
                    Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    
    
    
    
    
    
    
//    POMOCNE METODY:    
    
    
    /**
     *
     * @param mapPar
     * @param conn
     * @param sql
     * @param ent
     * @return
     */
    private PreparedStatement createStatement(Map<String, Class<?>> mapPar,
            Connection conn, String sql, E ent) {

        try {

            PreparedStatement st = conn.prepareStatement(sql);
            Class<?> stCls = st.getClass();

            Method stMethod;
            Method entMethod;

            String stMetName;
            String entMetName;

            Class<?> pomCls;

            int i = 1;

            for (String pn : mapPar.keySet()) {
                if ("id".equals(pn)) {
                    continue;
                }
                stMetName = ToolsDao.getG_SettersForResultSet(mapPar.get(pn), "set");
                pomCls = ToolsDao.transformToPrimitive(mapPar.get(pn));
                log.info("STAT MET NAME: " + stMetName);
                stMethod = stCls.getMethod(stMetName, int.class, pomCls);
//                stMethod = stCls.getMethod(stMetName, int.class, mapPar.get(pn)); //nefunguje

                entMetName = ToolsDao.getG_SetterName(pn, "get");
                entMethod = clsE.getMethod(entMetName);

                Object o = entMethod.invoke(ent);
                
                if (o == null){
                    st.setNull(i, Types.NULL);
                } else {
//                    o = ToolsDao.transformToAppropValue(o, mapPar.get(pn));
                    stMethod.invoke(st, i, o);
                }
                i++;
              
            }
//            st.setString(5, inputDate);
            return st;
        } catch (IllegalAccessException |
                SecurityException | NoSuchMethodException |
                IllegalArgumentException | InvocationTargetException | SQLException e) {
//            Notification.show("Chyba, uniRepo::save(...)", Type.ERROR_MESSAGE);
            log.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * Creates Insert Query for
     *
     * @param mapPar
     * @return
     */
    private String createInsertQueryPrepared(Map<String, Class<?>> mapPar) {

        try {

            StringBuilder insert1 = new StringBuilder();
            StringBuilder insert2 = new StringBuilder();

            insert1.append("(");
            insert2.append("(");

            boolean zac = true;
            for (String pn : mapPar.keySet()) {
                if ("id".equals(pn)) {
                    continue;
                }
                if (!zac) {
                    insert1.append(", ");
                    insert2.append(", ");
                }
                zac = false;
                insert1.append(pn);
                insert2.append("?");
            }
            insert1.append(")");
            insert2.append(")");

            String sql = String.format("INSERT INTO %s %s VALUES %s", TN,
                    insert1.toString(), insert2.toString());

//            Statement st = conn.prepareStatement(sql);
            log.debug("SQL:*" + sql + "*");
            return sql;

        } catch (SecurityException ex) {
//            Notification.show("Chyba, create Insert", Type.ERROR_MESSAGE);
            log.error(ex.getMessage(), ex);
            return null;
        }

    }

    /**
     *
     * @param mapPar
     * @param eid
     * @return
     */
    private String createUpdateQueryPrepared(Map<String, Class<?>> mapPar, Integer eid) {

        try {

            StringBuilder udpate = new StringBuilder();
            StringBuilder update2 = new StringBuilder();

//            udpate.append("(");
            boolean zac = true;

            for (String pn : mapPar.keySet()) {
                if ("id".equals(pn)) {
                    continue;
                }
                if (!zac) {
                    udpate.append(", ");
                }
                zac = false;
                udpate.append(pn);
                udpate.append(" = ?");
            }
//            udpate.append(")");

            update2.append("id = ");
            update2.append(eid);
//            update2.append("");

            String sql = String.format("UPDATE %s SET %s WHERE %s", TN,
                    udpate.toString(), update2.toString());

            log.debug("SQL:*" + sql + "*");
            return sql;

        } catch (SecurityException ex) {
//            Notification.show("Chyba, create Insert", Type.ERROR_MESSAGE);
            log.error(ex.getMessage(), ex);
            return null;
        }

    }

    
    
    
    /**
     * funkce na naplneni entity.
     *
     * @param rs
     * @return
     *
     */
    private E fillEntity(ResultSet rs) {

        List<E> ents = this.fillListEntity(rs);
        if (ents.size() > 0) {
            return ents.get(0);
        } else {
            return null;
        }
    }

    /**
     * naplna zoznam entit
     *
     * @param rs
     * @return
     */
    private List<E> fillListEntity(ResultSet rs) {

//        Class<?> rsCls = rs.getClass();
        Class<?> rsCls = ResultSet.class;

        List<E> listEnts = new ArrayList<>();
        String entMetName, rsMetName;
        Map<String, Class<?>> mapPar;

        try {

            mapPar = ToolsDao.getTypParametrov(clsE);

            while (rs.next()) {
                @SuppressWarnings("unchecked")
                E ent = (E) clsE.newInstance();

                for (String pn : mapPar.keySet()) {

                    entMetName = ToolsDao.getG_SetterName(pn, "set");
                    rsMetName = ToolsDao.getG_SettersForResultSet(mapPar.get(pn), "get");

//                    log.info("KYRYLENKO 1: " + rsMetName);
//                    log.info("KYRYLENKO 2: " + clsT.getCanonicalName());
//                    log.info("KYRYLENKO 3: " + pn);
//                    log.info("KYRYLENKO 4: " + entMetName);
//                    Method entMethod = clsT.getDeclaredMethod(entMetName, new Class<?>[]{mapPar.get(pn)});
                    Method entMethod = clsE.getMethod(entMetName, mapPar.get(pn));

//                  Method rsMethod = rsCls.getDeclaredMethod(rsMetName, new Class<?>[]{String.class});
                    Method rsMethod = rsCls.getMethod(rsMetName, String.class);

                    if ("getShort".equals(rsMetName)) {

                        Short sh = (Short) rsMethod.invoke(rs, pn);

                        Class<?> clsEnum = mapPar.get(pn);
                        Method enumMethod = clsEnum.getDeclaredMethod("values");
                        Object[] enumVals = (Object[]) (enumMethod.invoke(null));
                        Object enumVal = enumVals[sh];

                        entMethod.invoke(ent, enumVal);
//                  }  else if ("getBytes".equals(rsMetName) && "document".equals(pn)){
//                        byte[] bytes;
//                        bytes = (byte[]) rsMethod.invoke(rs, pn);
//                        InputStream myInputStream = new ByteArrayInputStream(bytes);
//                        
//                        entMethod.invoke(ent, myInputStream);

                    } else {
                        entMethod.invoke(ent, rsMethod.invoke(rs, pn));
                    }
                }

                listEnts.add(ent);
            }

            return listEnts;

        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException |
                SecurityException | NoSuchMethodException | IllegalArgumentException |
                InvocationTargetException | SQLException e) {
//            Notification.show("Chyba, uniRepo, fillListEntity(ResultSet rs)", Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    /**
     *
     * @param entFrom
     * @param entTo
     * @return
     */
    @Override
    public boolean copy(E entFrom, E entTo) {

        try {
            if (entFrom.getClass() != entTo.getClass()) {
                throw new NoSuchFieldException();
            }

            String fromMetName, toMetName;
            Map<String, Class<?>> mapPar;

            mapPar = ToolsDao.getTypParametrov(clsE);

            for (String pn : mapPar.keySet()) {

                fromMetName = ToolsDao.getG_SetterName(pn, "get");
                toMetName = ToolsDao.getG_SetterName(pn, "set");

                Method fromMethod = clsE.getMethod(fromMetName);
                Method toMethod = clsE.getMethod(toMetName, mapPar.get(pn));

                toMethod.invoke(entTo, fromMethod.invoke(entFrom));
            }
            return true;

        } catch (IllegalAccessException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            Notification.show("Chyba, uniRepo, copy(...)", Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }


    /**
     * Zisti, ci je entita nova, pokial nie, vrati jej id.
     * @param ent
     * @return 
     */
    private Integer isNew(E ent) {
        try {

            String entMetName = "getId";
            Method entMethod = clsE.getMethod(entMetName);
            boolean novy = entMethod.invoke(ent) == null;
            return (Integer) entMethod.invoke(ent);

        } catch (IllegalAccessException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            Notification.show("Chyba, uniRepo, copy(...)", Type.ERROR_MESSAGE);
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
