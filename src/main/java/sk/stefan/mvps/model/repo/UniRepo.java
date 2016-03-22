package sk.stefan.mvps.model.repo;

import com.vaadin.ui.Notification;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.dbConnection.DoDBconn;
import sk.stefan.mvps.model.entity.A_Change;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.interfaces.MyRepo;
import sk.stefan.utils.ToolsDao;

/**
 * Vrstva komunikujuca s na jednej strane s databazovymi nastrojmi (jdbc)
 * a na druhej strane so systemom (servisom).
 * 
 * Je specificka pre tu ktory entitu - preto genericky typ a reflexia.
 * 
 * @param <E>
 */
public class UniRepo<E> implements MyRepo<E> {

    private static final Logger log = Logger.getLogger(UniRepo.class);
    
    // table name:
    private final String TN;
    private final Class<?> clsE;

    //pre potreby ukladania do a_change;
    private Connection changeInvasiveConnection = null;

    /**
     * Konstruktor:
     *
     * @param cls
     */
    public UniRepo(Class<?> cls) {

        this.clsE = cls;
        this.TN = ToolsDao.getTableName(cls);
        this.changeInvasiveConnection = null;

    }

    //0B. konstruktor za ucelom ulozenia zmeny do DB.
    /**
     * 
     * @param cls
     * @param conn
     */
    public UniRepo(Class<?> cls, Connection conn) {

        this.clsE = cls;
        this.TN = ToolsDao.getTableName(cls);
        this.changeInvasiveConnection = conn;

    }


    
// ****************** NON INVASIVE **************************************        
    // 1.
    /**
     * Najde vsetky entity daneho typu E.
     *
     * @return
     */
    @Override
    public List<E> findAll() {
        
        Connection conn;
        Statement st;
        ResultSet rs;
        
        try {
            conn = DoDBconn.createNoninvasiveConnection();
            st = conn.createStatement();

            String sql = String.format("SELECT * FROM %s  WHERE visible = true", TN);

            rs = st.executeQuery(sql);
            
            List<E> listEnt = this.fillListEntity(rs);
            
            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);

            
            return listEnt;
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            log.error(e.getLocalizedMessage(), e);
            return null;
        } 
//        uzatvaranie connection vo finally bloku je z praktickeho hladiska 
//        zbytocne, lebo ked sa doserie nieco v DB, tak aj tak sa nepokracuje dalej.

    }

    // 2.
    /**
     * Najde prave jednu entitu E podla id.
     */
    @Override
    public E findOne(Integer id) {
        
        Connection conn;
        Statement st;
        ResultSet rs;

        try {
            
            conn = DoDBconn.createNoninvasiveConnection();

//            TOTO STATICKE SPOJENIE NEFUNGUJE (RESP. DAVA ZASTARALE VYSLEDKY),
//            NA AKTUALNE UDAJE SA MUSI VYTVORIT VZDY NOVE DB SPOJENIE!!!
//            if (DoDBconn.getNonInvasiveConn() == null) {
//                DoDBconn.createNoninvasiveConnection();
//            }

            st = conn.createStatement();

            String sql= String.format("SELECT * FROM %s WHERE id = %d AND visible = true", TN, id);
            

            rs = st.executeQuery(sql);
            log.debug(sql);
            E ent = this.fillEntity(rs);

            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);

            return ent;
        } catch (SecurityException | IllegalArgumentException | SQLException e) {
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
        
        Connection conn;
        Statement st;
        ResultSet rs;

        try {
            
            conn = DoDBconn.createNoninvasiveConnection();
            
            st = conn.createStatement();

            StringBuilder sql = new StringBuilder();

            sql.append(String.format("SELECT * FROM %s WHERE ", TN));
            sql.append(this.getFindByParamQuery(paramName, paramValue));
            sql.append(" AND visible = true");
            
            log.debug("SQL: *" + sql.toString() + "*");
            
            rs = st.executeQuery(sql.toString());

            List<E> listEnt = this.fillListEntity(rs);

            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);
            
            return listEnt;

        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            log.error(e.getMessage());
            return null;
        }

    }

    // 3.
    /**
     * Najde entitu podla zadanych parametrov.
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

        Connection conn;
        Statement st;
        ResultSet rs;

        try {
            
            conn = DoDBconn.createNoninvasiveConnection();
            st = conn.createStatement();

            // Notification.show("SOM TU!");
            StringBuilder sql = new StringBuilder();

            sql.append(String.format("SELECT * FROM %s WHERE ", TN));
            sql.append(this.getFindByParamQuery(p1Name, p1Value));
            sql.append(" AND ");
            sql.append(this.getFindByParamQuery(p2Name, p2Value));
            sql.append(" AND visible = true");
            
            rs = st.executeQuery(sql.toString());

            log.debug("SQL: *" + sql.toString() + "*");
            List<E> listEnt = this.fillListEntity(rs);

            rs.close();
            st.close();
            DoDBconn.releaseConnection(conn);

            return listEnt;

        } catch (SecurityException | IllegalArgumentException | SQLException e) {
            log.error(e.getMessage());
            return null;
        }

    }


    
    
// ****************** INVASIVE **************************************    
    
    // 4.
    /**
     * Uklada entitu do DB a vracia presne tu istu entitu, len ulozenu. tj. ten isty pointer.
     *
     * @param ent
     * @param noteChange ak je true, bude sa zapisovat do A_change, inak nie.
     * @param user
     * @return
     */
    @Override
    public E save(E ent, boolean noteChange, A_User user) {

        
        log.debug("SAVE: ent:" + (ent == null) + ", notechange: " + noteChange + ", user" + user);
        E entOrigin;
        Connection conn;
        PreparedStatement st;
        ResultSet rs;
        
        try {
//            log.info("TERAZ POJDEM VYTVORIT INVAZIVNE CONN" + DoDBconn.count);
            if (this.changeInvasiveConnection != null) {
                conn = changeInvasiveConnection;
            } else {
                conn = DoDBconn.createInvasiveConnection();
            }
            
            log.debug("conn: " + (conn == null));
            
            Map<String, Class<?>> mapPar;
            String sql;

            mapPar = ToolsDao.getTypParametrov(clsE);

            Integer eid = this.isNew(ent);
            boolean novy = (eid == null);

            if (novy) {
                sql = this.createInsertQueryPrepared(mapPar);
                entOrigin = null;
            } else {
                sql = this.createUpdateQueryPrepared(mapPar, eid);
                entOrigin = this.findOne(eid);
            }
            log.info("*"+sql+"*");
            st = this.createStatement(mapPar, conn, sql, ent);

            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (novy && rs.next()) {
                Integer newId = rs.getInt(1);
                Method entMethod = clsE.getMethod("setId", Integer.class);

                entMethod.invoke(ent, newId);
            }

            
            log.debug("som pred rozhodnutim do a_change!");
            log.debug("notechange: " + noteChange + ", TN: " + TN + ", changeInvasiveCOnnection: " + (this.changeInvasiveConnection == null));
            
            //to druhe a tretie je ten len kvoli poisteniu. staci len noteChange! 
            if (noteChange && !"a_change".equals(TN) && this.changeInvasiveConnection == null) {
                
                log.debug("som vnutri rozhodnutia, teda bude sa ukladat do a_change");
                List<A_Change> changes = this.createChangesToPersist(entOrigin, ent, mapPar, user);
                UniRepo<A_Change> changeRepo = new UniRepo<>(A_Change.class, conn);
                for (A_Change ch : changes) {
                    changeRepo.save(ch, false, null);
                }

            }

            rs.close();
            st.close();

            //ak sa vsetko podarilo(tj. ulozenie aj zapis do a_change), az teraz nastane commit:
            if (this.changeInvasiveConnection == null) {
                log.debug("comitujem dane connection!!!");
                conn.commit();
                DoDBconn.releaseConnection(conn);
            }

            return ent;

        } catch (IllegalAccessException | NoSuchFieldException |
                SecurityException | NoSuchMethodException |
                IllegalArgumentException | InvocationTargetException | SQLException e) {
            Notification.show("Zápis do databáze sa nepodaril!");
            log.error(e.getMessage(), e);
            return null;
        }

    }

    // 5.B
    /**
     * deaktivuje len 1 entitu. pokial potrebujete deaktivovat cely strom
     * zavislych entit, pouzite generalRepo...deactivateAll.
     * 
     * Preco existuju obe, vid vysvetlenie v generalRepo..deactivate...
     *
     * @param ent
     * @param noteChange
     * @param user
     * @return
     */
    @Override
    public boolean deactivateOneOnly(E ent, boolean noteChange, A_User user) {

        Connection conn;
        Statement st;
        
        try {
            if (this.changeInvasiveConnection != null) {
                conn = changeInvasiveConnection;
            } else {
                conn = DoDBconn.createInvasiveConnection();
            }

            st = conn.createStatement();

            Integer id = isNew(ent);

            if (id != null) {
                String sql = String.format("UPDATE %s SET visible = false WHERE id = %d", TN, id);
                st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            } else {
//                do nothing
                return true;
            }
            //to druhe a tretie je ten len kvoli poisteniu. staci len noteChange! 
            if (noteChange && !"a_change".equals(TN) && this.changeInvasiveConnection == null) {
                A_Change change = this.createDeactivateChangeToPersist(ent, user);
                UniRepo<A_Change> changeRepo = new UniRepo<>(A_Change.class, conn);
                changeRepo.save(change, false, null);
            }

            st.close();

            //ak sa vsetko podarilo(tj. ulozenie aj zapis do a_change), az teraz nastane commit:
            if (this.changeInvasiveConnection == null) {
                conn.commit();
                DoDBconn.releaseConnection(conn);
            }

            return true;

        } catch (SQLException | SecurityException | IllegalArgumentException e) {
            Notification.show("Zápis do databáze sa nepodaril!");
            log.error(e.getLocalizedMessage(), e);
            return false;
        }

    }

    /**
     * Modifikuje iba specifikovany parameter.
     *
     * @param paramName the name of parameter.
     * @param paramValue the new value of parameter
     * @param entId id of row where the value is updated.
     * @param noteChange
     * @param user
     * @throws java.sql.SQLException
     * @throws java.lang.NoSuchFieldException
     */
    public void updateParam(String paramName, String paramValue, String entId, boolean noteChange, A_User user)
            throws SQLException, NoSuchFieldException {

        Connection conn;
        Statement st;
                
        try {
            
            if (this.changeInvasiveConnection != null) {
                conn = changeInvasiveConnection;
            } else {
                conn = DoDBconn.createInvasiveConnection();
            }

            st = conn.createStatement();

            StringBuilder sql = new StringBuilder();

            sql.append(String.format("UPDATE %s SET ", TN));
            sql.append(this.getFindByParamQuery(paramName, paramValue));
            sql.append(String.format(" WHERE id =%s", entId));

            log.debug("SQL:*" + sql + "*");
            int i = st.executeUpdate(sql.toString(),Statement.RETURN_GENERATED_KEYS);

            //to druhe a tretie je ten len kvoli poisteniu. staci len noteChange! 
            if (noteChange && !"a_change".equals(TN) && this.changeInvasiveConnection == null) {
                A_Change change = this.createParamChangeToPersist(paramName, paramValue, entId, user);
                UniRepo<A_Change> changeRepo = new UniRepo<>(A_Change.class, conn);
                changeRepo.save(change, false, null);
            }

            st.close();

            //ak sa vsetko podarilo(tj. ulozenie aj zapis do a_change), az teraz nastane commit:
            if (this.changeInvasiveConnection == null) {
                conn.commit();
                DoDBconn.releaseConnection(conn);
            }

        } catch (SecurityException | IllegalArgumentException | SQLException e) {

            Notification.show("Zápis do databáze sa nepodaril!");
            log.error(e.getLocalizedMessage(), e);
            throw e;
        }

    }

    
    
    
    
    
    
    
//**************    POMOCNE METODY: *************************   
    /**
     * Vytvara databazovy preparedStatement pre save(). 
     * 
     * Tymto sposobom preto, lebo metody statementu su zavisle od triedy.
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
            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Format formatterSql = new SimpleDateFormat("yyyy-MM-dd");
            String date;

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
//                setter for prepared statemet:
                stMetName = ToolsDao.getSetterForPreparedStatement(mapPar.get(pn));
//                medzi typom v statemente a typom v java entite je rozdiel, 
//                tj. spravny typ sa musi namapovat:  
                pomCls = ToolsDao.transformToPrimitive(mapPar.get(pn));
                
//                log.info("STAT MET NAME: " + stMetName);
                stMethod = stCls.getMethod(stMetName, int.class, pomCls);
//                stMethod = stCls.getMethod(stMetName, int.class, mapPar.get(pn)); //nefunguje, vid vyssie

                entMetName = ToolsDao.getG_SetterName(pn, "get");
                entMethod = clsE.getMethod(entMetName);

                Object o = entMethod.invoke(ent);
                String namec = (mapPar.get(pn)).getCanonicalName();
//                log.info("DATUM1: " + namec + " : " + o);
                
//                dalsie nestandardne typy metod preparedStatementu:
                if (o == null) {
                    st.setNull(i, Types.NULL);
                } else if ("java.util.Date".equals(namec) || "java.sql.Date".equals(namec)) {
                    date = formatter.format(o);
                    log.info("DATUM: " + date);
                    st.setString(i, date);
                } else if (namec.contains(".enums")) {
                    Integer sh = ToolsDao.getShortFromEnum(mapPar.get(pn), o);
                    st.setInt(i, sh);
                } else {
                    log.info("case ELSE, use type as is: " + o.toString());
                    stMethod.invoke(st, i, o);
                }
                i++;

            }
            
            return st;
            
        } catch (IllegalAccessException |
                SecurityException | NoSuchMethodException |
                IllegalArgumentException | InvocationTargetException | SQLException e) {
//            Notification.show("Chyba, uniRepo::save(...)", Type.ERROR_MESSAGE);
            log.error("KAROLKO" + e.getMessage(), e);
            return null;
        }

    }

    /**
     * Vytvara SQL dotaz.
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

            log.debug("SQL:*" + sql + "*");
            return sql;

        } catch (SecurityException ex) {
            
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

            update2.append("id = ");
            update2.append(eid);

            String sql = String.format("UPDATE %s SET %s WHERE %s", TN,
                    udpate.toString(), update2.toString());

            log.debug("SQL:*" + sql + "*");
            return sql;

        } catch (SecurityException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }

    }

    /**
     * metoda na naplnenie entity z navratoveho objektu Db(resultset).
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
     * naplna zoznam entit. vid vyssie.
     *
     * @param rs
     * @return
     */
    private List<E> fillListEntity(ResultSet rs) {

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
                    rsMetName = ToolsDao.getGettersForResultSet(mapPar.get(pn));

                    Method entMethod = clsE.getMethod(entMetName, mapPar.get(pn));

                    Method rsMethod = rsCls.getMethod(rsMetName, String.class);

//                    pre potreby transformacie do enum:
                    if ("getShort".equals(rsMetName)) {

                        Short sh = (Short) rsMethod.invoke(rs, pn);

                        Class<?> clsEnum = mapPar.get(pn);
                        Method enumMethod = clsEnum.getDeclaredMethod("values");
                        Object[] enumVals = (Object[]) (enumMethod.invoke(null));
                        Object enumVal = enumVals[sh];

                        entMethod.invoke(ent, enumVal);

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
//            Notification.show("Chyba, uniRepo, copy(...)", Type.ERROR_MESSAGE);
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    /**
     * Zisti, ci je entita nova, pokial nie, vrati jej id.
     *
     * @param ent
     * @return
     */
    private Integer isNew(E ent) {
        try {

            String entMetName = "getId";
            Method entMethod = clsE.getMethod(entMetName);
            return (Integer) entMethod.invoke(ent);

        } catch (IllegalAccessException | SecurityException | NoSuchMethodException |
                IllegalArgumentException | InvocationTargetException e) {
//            Notification.show("Chyba, uniRepo, copy(...)", Type.ERROR_MESSAGE);
            log.error(e.getMessage(), e);
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


    

//******************** auxiliary  methods for saving change into database **************
    
    /**
     * Metoda vytiahne z entity hodnoty a ulozi ich do mapy. kde:
     * key = nazov parametru entity a
     * value = objekt jeho hodnoty.
     * 
     * Vyuziva sa pri hladani zmien pri pouziti metody save() (pretoze nie vsetky 
     * parametre entity museli byt pred jej ulozeniim zmenene.)
     * aby sa vedelo, aka zmena sa ma do DB ulozit.
     * 
     * @param ent
     * @param mapPar
     * @return 
     */
    private Map<String, Object> getEntityValues(E ent, Map<String, Class<?>> mapPar) {

        Map<String, Object> valuesAsObjects = new HashMap<>();
        String getterName;
        Object val;

        try {
            for (String param : mapPar.keySet()) {
                if (ent == null) {
                    valuesAsObjects.put(param, null);
                } else {
                    getterName = ToolsDao.getG_SetterName(param, "get");
                    Method entMethod = clsE.getMethod(getterName);
                    val = entMethod.invoke(ent);
                    valuesAsObjects.put(param, val);
                }
            }

            return valuesAsObjects;

        } catch (IllegalAccessException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
//            Notification.show("Chyba, createPreSaveSnapshot", Type.ERROR_MESSAGE);
            log.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * Vrati zoznam zmien danej entity (v porovnani pred a po ulozeni do DB). 
     *
     * @param entOrigin entity before change from DB. if entChanged is new, this
     * is null.
     * @param entChanged must be after saving to database (i.e must have an
     * id,also for new entity)
     * @param mapPar key = parameter name, value = class of that parameter
     * 
     * @return list of changes.
     * 
     */
    private List<A_Change> createChangesToPersist(E entOrigin, E entChanged, Map<String, Class<?>> mapPar, A_User user) {

        Integer userId;
        Integer rowId;
        Object orig;
        Object changed;

        if (user == null) {
            log.warn("This shouldnt be possible!!");
            return null;
        } else {
            userId = user.getId();
        }

        rowId = this.isNew(entChanged);

        Map<String, Object> pre = getEntityValues(entOrigin, mapPar);
        Map<String, Object> post = getEntityValues(entChanged, mapPar);

        List<A_Change> changesToPersist = new ArrayList<>();
        A_Change zmena;

        for (String param : pre.keySet()) {
            orig = pre.get(param);
            changed = post.get(param);

            //odfiltrovanie policok, kde zmena nenastala. pokial je entita nova, uklada sa vsetko:
            if (entOrigin == null || !(orig + "").equals(changed+"")) {
                
                zmena = new A_Change();
                zmena.setUser_id(userId);
                zmena.setTable_name(TN);
                zmena.setColumn_name(param);
                zmena.setRow_id(rowId);
                //zistit, ci je to Strinfg postacujuce pre spatnu konverziu!
                zmena.setOld_value(ToolsDao.getBytes(orig, mapPar.get(param)));
                zmena.setNew_value(ToolsDao.getBytes(changed, mapPar.get(param)));
                zmena.setVisible(Boolean.TRUE);

                changesToPersist.add(zmena);
            }
        }

        return changesToPersist;
    }

    /**
     * Vrati zmenu z deaktivacie entity.
     *
     * @param entChanged
     * @return
     */
    public A_Change createDeactivateChangeToPersist(E entChanged, A_User user) {

        Integer userId;
        Integer rowId;
        A_Change zmena;

        if (user == null) {
            log.warn("This shouldnt be possible!!");
            return null;
        } else {
            userId = user.getId();
        }

        rowId = this.isNew(entChanged);

        zmena = new A_Change();
        zmena.setUser_id(userId);
        zmena.setTable_name(TN);
        zmena.setColumn_name("visible");
        zmena.setRow_id(rowId);
        zmena.setOld_value(ToolsDao.getBytes(Boolean.TRUE, Boolean.class));
        zmena.setNew_value(ToolsDao.getBytes(Boolean.FALSE, Boolean.class));
        zmena.setVisible(Boolean.TRUE);

        return zmena;

    }

    /**
     * Vracia zmenu(objekt) zo zmeny hodnoty parametra entity.
     *
     * @param paramName
     * @param paramNewValue
     * @param entId
     */
    private A_Change createParamChangeToPersist(String paramName, String paramNewValue, String entId, A_User user) throws NoSuchFieldException {

        E entOrig;
        Integer userId;
        Object valOrig;
        A_Change zmena;
        
        Integer rowId = Integer.parseInt(entId);
        if (user == null) {
            log.warn("This shouldnt be possible!!");
            return null;
        } else {
            userId = user.getId();
        }

        entOrig = this.findOne(rowId);

//        Type typ = clsE.getDeclaredField(paramName).getType();
//        typy.put(p, (Class<?>) typ);

        try {
            String getterName = ToolsDao.getG_SetterName(paramName, "get");
            Method entMethod = clsE.getMethod(getterName);
            valOrig = entMethod.invoke(entOrig);

            zmena = new A_Change();

            zmena.setUser_id(userId);
            zmena.setTable_name(TN);
            zmena.setColumn_name(paramName);
            zmena.setRow_id(rowId);
            zmena.setOld_value(ToolsDao.getBytes(valOrig, clsE.getDeclaredField(paramName).getType()));
            zmena.setNew_value(ToolsDao.getBytes(paramNewValue, clsE.getDeclaredField(paramName).getType()));
            zmena.setVisible(Boolean.TRUE);

            return zmena;

        } catch (IllegalAccessException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }
    


}
