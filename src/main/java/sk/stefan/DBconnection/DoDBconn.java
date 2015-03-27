package sk.stefan.DBconnection;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.ui.Notification;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 * Trieda, ktora sa stara o pripojenie k DB. connectionpool ma synchronizaciu
 * implicitne vyriesenu, preto sa o nu viac netreba starat.
 *
 *
 * @author veres
 *
 *
 */
public class DoDBconn {

    private static final Logger log = Logger.getLogger(DoDBconn.class);
        
    private static final Locale locale = new Locale("cz", "CZ");
    // načtení properties
    private static ResourceBundle properties;
    
    private static Connection nonInvasiveConn;

    private static JDBCConnectionPool connectionPool = null;
    private static final JDBCConnectionPool connectionPoolCont = null;

//    private static final String driver;
//    private static final String url;
//    private static final String user;
//    private static final String passwd;

    static {
        connectToDb(PrepDBconn.dbDriver, PrepDBconn.dbURL, PrepDBconn.dbUser, PrepDBconn.dbPwd, 2, 55);
        //connectToDb(PrepDBconn.dbDriver, PrepDBconn.dbURL, PrepDBconn.dbUser, PrepDBconn.dbPwd, 2, 55);
        
//        properties = ResourceBundle.getBundle("dbconnection", locale);
//        driver = properties.getString("dbDriver");
//        url = properties.getString("dbUrl");
//        user = properties.getString("dbUser");
//        passwd = properties.getString("dbPwd");
//        connectToDb(driver, url, user, passwd, 2, 55);
    }

    /**
     * @return the conn
     */
    public static Connection getNonInvasiveConn() {
        return nonInvasiveConn;
    }

    /**
     * @param aConn the conn to set
     */
    public static void setNonInvasiveConn(Connection aConn) {
        nonInvasiveConn = aConn;
    }

    // 0.A konstruktor:
    public DoDBconn() {

        connectToDb(PrepDBconn.dbDriver, PrepDBconn.dbURL, PrepDBconn.dbUser, PrepDBconn.dbPwd, 2, 55);
        
//        properties = ResourceBundle.getBundle("dbconnection", locale);
//        String driver = properties.getString("dbDriver");
//        String url = properties.getString("dbUrl");
//        String user = properties.getString("dbUser");
//        String passwd = properties.getString("dbPwd");
//        connectToDb(driver, url, user, passwd, 2, 55);
    }

    // 1.
    private static void connectToDb(String dbDriver, String dbURL, String dbUser,
            String dbPwd, int iniCon, int maxCon) {
        try {
            connectionPool = new SimpleJDBCConnectionPool(dbDriver, dbURL, dbUser, dbPwd, iniCon, maxCon);
        } catch (SQLException ex) {
            log.error(ex.getMessage(),ex);
        }
    }

    // 2.A connection for non invasive DB operations.
    public static void createNoninvasiveConnection() {
        try {
            nonInvasiveConn = connectionPool.reserveConnection();
        } catch (SQLException ex) {
            log.error(ex.getMessage(),ex);
        }
    }
    
    // 2.B. Connection for invasive DB operations.
    public static Connection getConnection() {
        try {
            Connection conn = connectionPool.reserveConnection();
            return conn;
        } catch (SQLException ex) {
            log.error(ex.getMessage(),ex);
            return null;
        }
    }


    //3.
    public static JDBCConnectionPool getPool() {
        return connectionPool;
    }

    // 4.
    public static void releaseConnection(Connection conn) throws SQLException {
        connectionPool.releaseConnection(conn);
    }

    // 5.
    public static SQLContainer getContainer(String TableName) throws SQLException {

        TableQuery q1 = new TableQuery(TableName, connectionPool);
        q1.setVersionColumn("id");
        SQLContainer tableContainer = new SQLContainer(q1);
        return tableContainer;

    }
    
//    /**
//     * commituje zmeny do DB.
//     */
//    public static synchronized void doCommit() {
//        try {
//            nonInvasiveConn.commit();
//        } catch (SQLException ex) {
//            try {
//                nonInvasiveConn.rollback();
//                log.error(ex.getMessage(), ex);
//                Notification.show("Ukladanie sa nevydarilo, SPRAVIL SA ROLLBACK!");
//            } catch (SQLException ex1) {
//                log.error(ex.getMessage(), ex);
//                Notification.show("ANI TEN BLBY ROLLBACK SA NEPODARIL!");
//            }
//        }
//    }
//    /**
//     * commituje zmeny do DB.
//     */
//    public static synchronized void doRollback() {
//        try {
//            nonInvasiveConn.rollback();
//        } catch (SQLException ex) {
//            log.error(ex.getMessage(), ex);
//        }
//        
//    }

}
