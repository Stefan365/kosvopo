package sk.stefan.MVP.model.repo.dao;

import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.view.helpers.PomDao;
import sk.stefan.interfaces.MyRepo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

public class UniRepo<T> implements MyRepo<T> {

    private static final Logger log = Logger.getLogger(UniRepo.class);
    // table name:
    private String TN;
    private final Class<?> cls;

    /**
     * Konstruktor:
     *
     * @param cls
     */
    public UniRepo(Class<?> cls) {
        this.cls = cls;
        this.setTN(cls);
    }

    /**
     *
     * Nastavi meno DB tabulky.
     */
    private void setTN(Class<?> cls) {

        try {
            Method getTnMethod = cls.getDeclaredMethod("getTN");
            TN = (String) getTnMethod.invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            log.error("chyba, !");
        }
    }

    // 1.
    @Override
    public List<T> findAll() {
        try {
            Connection conn = DoDBconn.getConnection();
            Statement st = conn.createStatement();

            String sql = String.format("SELECT * FROM %s", TN);
            ResultSet rs = st.executeQuery(sql);
            //log.info("PETER 7: " + rs.getClass().getCanonicalName());
            //log.info("PETER 8:  " + (rs != null));

            //JDBC4ResultSet
            log.info(sql + " DONE!");

            List<T> listEnt = this.fillListEntity(rs);

            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);

            return listEnt;
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            //Notification.show("Chyba, uniRepo::findAll(...)",
            //        Type.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }

    }

    // 2.
    @Override
    public T findOne(Integer id) {
        try {
            Connection conn = DoDBconn.getConnection();
            Statement st = conn.createStatement();

            String sql = String
                    .format("SELECT * FROM %s WHERE id = %d", TN, id);
            ResultSet rs = st.executeQuery(sql);
            // Notification.show(sql);
            System.out.println(sql);
            T ent = this.fillEntity(rs);

            rs.close();
            st.close();
            DoDBconn.getPool().releaseConnection(conn);

            return ent;
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
//            Notification.show("Chyba, uniRepo::findOne(...)",
//                    Type.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }

    }

    // 3.
    @Override
    public List<T> findByParam(String paramName, String paramValue) {

        try {
            Connection conn = DoDBconn.getConnection();
            Statement st = conn.createStatement();

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

            // Notification.show(sql);
            System.out.println(sql);

            ResultSet rs = st.executeQuery(sql);

            System.out.println(sql);
            List<T> listEnt = this.fillListEntity(rs);

            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);

            return listEnt;

        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            //Notification.show("Chyba, uniRepo::findByParam(...)",
            //		Type.ERROR_MESSAGE);

            e.printStackTrace();
            return null;
        }

    }

    // 4.
    @Override
    public T save(T ent) {
        try {
            Connection conn = DoDBconn.getConnection();
            Statement st = conn.createStatement();

            String entMetName;
            // Map<String, String> mapPar;
            Map<String, Class<?>> mapPar;

            mapPar = PomDao.getTypParametrov(cls);

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
            Method entMethod = cls.getDeclaredMethod(entMetName);
            if (entMethod.invoke(ent) == null) {
                novy = true;
            } else {
                novy = false;
                str = String.format(" id = %d",
                        (Integer) (entMethod.invoke(ent)));
                update2.append(str);
            }
            // cyklus:
            for (String pn : mapPar.keySet()) {

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

                entMetName = PomDao.getG_SetterName(pn, "get");
                entMethod = cls.getDeclaredMethod(entMetName);

                // pokial je entita nova, tj. este nebola ulozena v DB.:
                String s = "" + entMethod.invoke(ent);
//				if (entMethod.invoke(ent) != null){
//					System.out.println("MENO: "+ (entMethod.invoke(ent)).getClass().getCanonicalName());
//				}

                if (entMethod.invoke(ent) != null && "java.util.Date".equals((entMethod.invoke(ent)).getClass().getCanonicalName())) {
                    s = "'" + PomDao.utilDateToString((Date) entMethod.invoke(ent)) + "'";
                } else if (entMethod.invoke(ent) != null && "java.lang.Boolean".equals((entMethod.invoke(ent)).getClass().getCanonicalName())) {
                    s = " " + entMethod.invoke(ent) + " ";
                } else {
                    s = "'" + s + "'";
                }
                if (novy) {
                    insert1.append(pn);
                    insert2.append(s);
                } else {
                    update1.append(pn + " = " + s);
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
                System.out.println(sql);
            }

            // Notification.show(sql);
            System.out.println(sql);

            st.executeUpdate(sql);
            ResultSet rs = st.getGeneratedKeys();
            if (novy && rs.next()) {
                Integer newId = rs.getInt(1);
                entMethod = cls.getDeclaredMethod("setId",
                        new Class[]{Integer.class});
                entMethod.invoke(ent, newId);
                // ent.setId(newId);
            }

            rs.close();
            st.close();
            conn.commit();
            DoDBconn.releaseConnection(conn);

            return ent;
        } catch (IllegalAccessException | NoSuchFieldException | 
                SecurityException | NoSuchMethodException | 
                IllegalArgumentException | InvocationTargetException | SQLException e) {
//            Notification.show("Chyba, uniRepo::save(...)", Type.ERROR_MESSAGE);
            log.error(e.getMessage());
            return null;
        }

    }

    // 5.
    @Override
    public boolean delete(T ent) {
        try {
            Connection conn = DoDBconn.getConnection();
            Statement st = conn.createStatement();

            Integer id = null;

            if (ent != null) {
                Method entMethod = cls.getDeclaredMethod("getId");
                id = (Integer) entMethod.invoke(ent);
            }

            if (id != null) {
                String sql = String.format("DELETE FROM %s WHERE id = %d", TN,
                        id);
                st.executeUpdate(sql);
                // Notification.show(sql);
                System.out.println(sql);
            }

            st.close();
            conn.commit();
            DoDBconn.releaseConnection(conn);
            return true;

        } catch (IllegalAccessException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            Notification
                    .show("Chyba, uniRepo::delete(non SQL exception)", Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            Notification.show("Chyba, uniRepo::delete(SQL exception)", Type.ERROR_MESSAGE);
            //Notification.show("Danu entitu nieje mozne zatial vymazat, vymaz dalsie podentity");
            e.printStackTrace();
            return false;
        }

    }

    // funkce na naplneni entity:
    public T fillEntity(ResultSet rs) {

        //Class<?> rsCls = rs.getClass();
        Class<?> rsCls = ResultSet.class;
        //log.info("PAVOLKO 0: " + rsCls.getCanonicalName());

        
        String entMetName, rsMetName;
        // Map<String, String> mapPar;
        Map<String, Class<?>> mapPar;
        try {
            @SuppressWarnings("unchecked")
            T ent = (T) cls.newInstance();
            mapPar = PomDao.getTypParametrov(cls);

            while (rs.next()) {

                for (String pn : mapPar.keySet()) {

                    entMetName = PomDao.getG_SetterName(pn, "set");
                    rsMetName = PomDao.getGettersForResultSet(mapPar.get(pn)
                            .getCanonicalName());

                    Method entMethod = cls.getDeclaredMethod(entMetName,
                            new Class[]{mapPar.get(pn)});
                    Method rsMethod = rsCls.getDeclaredMethod(rsMetName,
                            new Class[]{String.class});

                    entMethod.invoke(ent, rsMethod.invoke(rs, pn));
                }
            }

            return ent;
        } catch (InstantiationException | IllegalAccessException |
                NoSuchFieldException | SecurityException |
                NoSuchMethodException | IllegalArgumentException |
                InvocationTargetException | SQLException e) {
//            Notification.show("Chyba, uniRepo::fillEntity(...)",
//                    Type.ERROR_MESSAGE);
            log.error(e.getMessage());
            return null;
        }

    }

    // funkce na seznamu entit:
    private List<T> fillListEntity(ResultSet rs) {

//        Class<?> rsCls = rs.getClass();
        Class<?> rsCls = ResultSet.class;
        //log.info("PETER 1: " + rsCls.getCanonicalName());

        List<T> listEnt = new ArrayList<T>();
        String entMetName, rsMetName;
        // Map<String, String> mapPar;
        Map<String, Class<?>> mapPar;

        try {
//			@SuppressWarnings("unchecked")
//			T enta = (T) cls.newInstance();
            mapPar = PomDao.getTypParametrov(cls);

            while (rs.next()) {
                @SuppressWarnings("unchecked")
                T ent = (T) cls.newInstance();

                for (String pn : mapPar.keySet()) {

                    entMetName = PomDao.getG_SetterName(pn, "set");
                    rsMetName = PomDao.getGettersForResultSet(mapPar.get(pn)
                            .getCanonicalName());
                    // System.out.println(entMetName);
                    Method entMethod = cls.getDeclaredMethod(entMetName, new Class[]{mapPar.get(pn)});
                    //log.info("PETER: *" + rsMetName + "*");
                    Method rsMethod = rsCls.getDeclaredMethod(rsMetName, new Class[]{String.class});

                    entMethod.invoke(ent, rsMethod.invoke(rs, pn));
                }
                // Class c = Class.forName("MVP.model.entity.dao.VoteOfRole");

                listEnt.add(ent);
            }

            return listEnt;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | SQLException e) {
            //Notification.show("Chyba, uniRepo, fillListEntity(ResultSet rs)",
            //		Type.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean copy(T entFrom, T entTo) {
        try {
            if (entFrom.getClass() != entTo.getClass()) {
                throw new NoSuchFieldException();
            }

            String fromMetName, toMetName;
            Map<String, Class<?>> mapPar;

            mapPar = PomDao.getTypParametrov(cls);

            for (String pn : mapPar.keySet()) {

                fromMetName = PomDao.getG_SetterName(pn, "get");
                toMetName = PomDao.getG_SetterName(pn, "set");

                Method fromMethod = cls.getDeclaredMethod(fromMetName);
                Method toMethod = cls.getDeclaredMethod(toMetName,
                        new Class[]{mapPar.get(pn)});

                toMethod.invoke(entTo, fromMethod.invoke(entFrom));
            }
            return true;

        } catch (IllegalAccessException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            Notification.show("Chyba, uniRepo, copy(...)", Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

}
