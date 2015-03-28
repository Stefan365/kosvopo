package sk.stefan.MVP.model.repo.dao;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.interfaces.MyRepo;
import sk.stefan.utils.ToolsDao;

public class UniRepo<T> implements MyRepo<T> {

    private static final Logger log = Logger.getLogger(UniRepo.class);
    // table name:
    private final String TN;
    private final Class<?> clsT;

    //connection musi byt centralne, inak nenudu fungovat transactional operacie
    //bude tam odkaz na DoDBconn.conn
//    private static Connection conn = DoDBconn.getConn();
    /**
     * Konstruktor:
     *
     * @param cls
     */
    public UniRepo(Class<?> cls) {

        this.clsT = cls;
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
    public List<T> findAll() {
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
            List<T> listEnt = this.fillListEntity(rs);

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
    public T findOne(Integer id) {
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
            T ent = this.fillEntity(rs);

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
    public List<T> findByParam(String paramName, String paramValue) {

        try {
            //pre pripad, ze by spojenie spadlo.
            if (DoDBconn.getNonInvasiveConn() == null) {
                DoDBconn.createNoninvasiveConnection();
            }
            Statement st;
            st = DoDBconn.getNonInvasiveConn().createStatement();

            // Notification.show("SOM TU!");
            String sql;
            if (paramValue == null) {
                sql = String.format("SELECT * FROM %s WHERE %s is NULL", TN,
                        paramName);
            } else if ("null".equalsIgnoreCase(paramValue) || "true".equalsIgnoreCase(paramValue) || "false".equalsIgnoreCase(paramValue)) {
                sql = String.format("SELECT * FROM %s WHERE %s = %s", TN,
                        paramName, paramValue);
            } else {
                sql = String.format("SELECT * FROM %s WHERE %s = '%s'", TN,
                        paramName, paramValue);
            }

            if (TN.contains("a_")) {
                //do nothing
            } else {
                sql += " AND visible = true";
            }

            // Notification.show(sql);
//            System.out.println(sql);
            ResultSet rs;
            rs = st.executeQuery(sql);

//            System.out.println(sql);
            List<T> listEnt = this.fillListEntity(rs);

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

    // 4.
    /**
     * Vracia presne tu istu entitu, len ulozenu. tj. ten isty pointer.
     *
     * @param ent
     * @return
     */
    @Override
    public T save(T ent) {
        try {

            Connection conn = DoDBconn.getConnection();

            Statement st = conn.createStatement();

            String entMetName;
            // Map<String, String> mapPar;
            Map<String, Class<?>> mapPar;

            mapPar = ToolsDao.getTypParametrov(clsT);

            String sql, str;

            Boolean novy, zac = true;
            StringBuilder insert1 = new StringBuilder();
            StringBuilder insert2 = new StringBuilder();

            StringBuilder update1 = new StringBuilder();
            StringBuilder update2 = new StringBuilder();

            insert1.append("(");
            insert2.append("(");

            // kontrola o jakou entitu se jedna (nova/uz pouzita):
            entMetName = "getId";
            Method entMethod = clsT.getMethod(entMetName);
            if (entMethod.invoke(ent) == null) {
                novy = true;
            } else {
                novy = false;
                str = String.format(" id = %d",
                        (Integer) (entMethod.invoke(ent)));
                update2.append(str);
            }

            // cyklus zistovania hodnot parametrov POJO-a:
            for (String pn : mapPar.keySet()) {
                if ("password".equals(pn)) {
                    //password sa bude ukladat inym sposobom
                    continue;
                }
                if ("id".equals(pn)) {
                    continue;
                }

                if (zac == false && !novy) {
                    update1.append(", ");
                } else if (zac == false && novy) {
                    insert1.append(", ");
                    insert2.append(", ");
                }
                zac = false;

                entMetName = ToolsDao.getG_SetterName(pn, "get");
                entMethod = clsT.getMethod(entMetName);

                // pokial je entita nova, tj. este nebola ulozena v DB.:
                String s;
                Object obj = entMethod.invoke(ent);

//                log.debug("KAROLKO entMetName:" + entMetName);
//                log.debug("KAROLKO s:" + s);
//                log.debug("KAROLKO obj" + obj);
                if (null == obj) {
                    s = " " + obj + " ";
                } else if ("java.util.Date".equals(obj.getClass().getCanonicalName())) {
                    s = "'" + ToolsDao.utilDateToString((Date) obj) + "'";
                } else if ("java.sql.Date".equals(obj.getClass().getCanonicalName())) {
                    s = "'" + ToolsDao.sqlDateToString((java.sql.Date) obj) + "'";
                } else if ("java.lang.Boolean".equals(obj.getClass().getCanonicalName())) {
                    s = " " + obj + " ";
                } else {
                    if (obj.getClass().getCanonicalName().contains(".enums.")) {
                        s = "" + ToolsDao.getShortFromEnum(mapPar.get(pn), obj);
                    } else {
                        s = "'" + obj + "'";
                    }
                }

                if (novy) {
                    insert1.append(pn);
                    insert2.append(s);
                } else {
                    update1.append(pn).append(" = ").append(s);
                }
            }

            if (novy) {
                insert1.append(")");
                insert2.append(")");
                sql = String.format("INSERT INTO %s %s VALUES %s ", TN,
                        insert1.toString(), insert2.toString());
            } else {
                sql = String.format("UPDATE %s SET %s WHERE %s", TN,
                        update1.toString(), update2.toString());
//                System.out.println(sql);
            }

            // Notification.show(sql);
            log.info("SAVE:" + sql);

            st.executeUpdate(sql);

            ResultSet rs = st.getGeneratedKeys();
            if (novy && rs.next()) {
                Integer newId = rs.getInt(1);
                entMethod = clsT.getMethod("setId", Integer.class);
                entMethod.invoke(ent, newId);
                // ent.setId(newId);
            }

            conn.commit();
            rs.close();
            st.close();

            DoDBconn.releaseConnection(conn);

            return ent;

        } catch (IllegalAccessException | NoSuchFieldException |
                SecurityException | NoSuchMethodException |
                IllegalArgumentException | InvocationTargetException | SQLException e) {
            Notification.show("Chyba, uniRepo::save(...)", Type.ERROR_MESSAGE);
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
    public boolean delete(T ent) {
        try {

            Connection conn = DoDBconn.getConnection();

            Statement st = conn.createStatement();

            Integer id = null;

            if (ent != null) {
                Method entMethod = clsT.getMethod("getId");
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
     * @param ent
     * @return
     */
    @Override
    public boolean deactivate(T ent) {

        Connection conn = DoDBconn.getConnection();

        try {

            Statement st = conn.createStatement();

            Integer id = null;

            if (ent != null) {
                Method entMethod = clsT.getMethod("getId");
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

            // Notification.show("SOM TU!");
            String sql;
            if (paramValue == null) {
                sql = String.format("UPDATE %s SET %s = NULL WHERE id =%s", TN, paramName, id);
            } else if ("null".equalsIgnoreCase(paramValue) || "true".equalsIgnoreCase(paramValue) || "false".equalsIgnoreCase(paramValue)) {
                sql = String.format("UPDATE %s SET %s = %s WHERE id =%s", TN, paramName, paramValue, id);
            } else {
                sql = String.format("UPDATE %s SET %s = '%s' WHERE  id =%s", TN, paramName, paramValue, id);
            }

            int i = st.executeUpdate(sql);

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

    /**
     * funkce na naplneni entity.
     *
     * @param rs
     * @return
     *
     */
    private T fillEntity(ResultSet rs) {

        List<T> ents = this.fillListEntity(rs);
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
    private List<T> fillListEntity(ResultSet rs) {

//        Class<?> rsCls = rs.getClass();
        Class<?> rsCls = ResultSet.class;

        List<T> listEnts = new ArrayList<>();
        String entMetName, rsMetName;
        Map<String, Class<?>> mapPar;

        try {

            mapPar = ToolsDao.getTypParametrov(clsT);

            while (rs.next()) {
                @SuppressWarnings("unchecked")
                T ent = (T) clsT.newInstance();

                for (String pn : mapPar.keySet()) {

                    entMetName = ToolsDao.getG_SetterName(pn, "set");
                    rsMetName = ToolsDao.getGettersForResultSet(mapPar.get(pn)
                            .getCanonicalName());

//                    log.info("KYRYLENKO 1: " + rsMetName);
//                    log.info("KYRYLENKO 2: " + clsT.getCanonicalName());
//                    log.info("KYRYLENKO 3: " + pn);
//                    log.info("KYRYLENKO 4: " + entMetName);
//                    Method entMethod = clsT.getDeclaredMethod(entMetName, new Class<?>[]{mapPar.get(pn)});
                    Method entMethod = clsT.getMethod(entMetName, mapPar.get(pn));

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
    public boolean copy(T entFrom, T entTo) {

        try {
            if (entFrom.getClass() != entTo.getClass()) {
                throw new NoSuchFieldException();
            }

            String fromMetName, toMetName;
            Map<String, Class<?>> mapPar;

            mapPar = ToolsDao.getTypParametrov(clsT);

            for (String pn : mapPar.keySet()) {

                fromMetName = ToolsDao.getG_SetterName(pn, "get");
                toMetName = ToolsDao.getG_SetterName(pn, "set");

                Method fromMethod = clsT.getMethod(fromMetName);
                Method toMethod = clsT.getMethod(toMetName, mapPar.get(pn));

                toMethod.invoke(entTo, fromMethod.invoke(entFrom));
            }
            return true;

        } catch (IllegalAccessException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            Notification.show("Chyba, uniRepo, copy(...)", Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

//    /**
//     * commituje zmeny do DB.
//     */
//    public void doCommit() {
////        DoDBconn.doCommit();
//    }
}
