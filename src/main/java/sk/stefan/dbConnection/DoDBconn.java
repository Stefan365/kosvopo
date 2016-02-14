package sk.stefan.dbConnection;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * Trieda, ktora sa stara o pripojenie k DB. connectionpool ma synchronizaciu
 * implicitne vyriesenu, preto sa o nu viac netreba starat.
 *
 * @author veres
 *
 *
 */
public class DoDBconn {

    private static final Logger log = Logger.getLogger(DoDBconn.class);
        
    public static int count =0;

    //TOTO NEFUNGUJE DOBRE: DAVA TO ZASTARALE VYSLEDKY.
//    private static Connection nonInvasiveConn;

    private static JDBCConnectionPool connectionPool = null;


    static {
        connectToDb(PrepDBconn.dbDriver, PrepDBconn.dbURL, PrepDBconn.dbUser, PrepDBconn.dbPwd, 2, 55);
    }

    // 0.A konstruktor:
    /**
     * Konstruktor. nepouziva sa.
     */
    public DoDBconn() {

        connectToDb(PrepDBconn.dbDriver, PrepDBconn.dbURL, PrepDBconn.dbUser, PrepDBconn.dbPwd, 2, 255);
        
    }

    // 1.
    /**
     * Stara sa o pripojenie k DB.
     */
    private static void connectToDb(String dbDriver, String dbURL, String dbUser,
            String dbPwd, int iniCon, int maxCon) {
        try {
            connectionPool = new SimpleJDBCConnectionPool(dbDriver, dbURL, dbUser, dbPwd, iniCon, maxCon);
        } catch (SQLException ex) {
            log.error(ex.getMessage(),ex);
        }
    }

    // 2.A connection for non invasive DB operations.
    /**
     * Rozdiel je v nazve len z dvovodu lepsej orientacie pre uzivatela.
     * @return 
     */
    public static Connection createNoninvasiveConnection() {
        try {
            Connection conn = connectionPool.reserveConnection();
            count++;
//            log.info("VYTVORIL SOM NEINVAZIVNE CONN: " + count);
            return conn;
        } catch (SQLException ex) {
            log.error(ex.getMessage(),ex);
            return null;
        }
    }
    
    // 2.B. Connection for invasive DB operations.
    /**
     * Vracia invazivne spojenie s DB. nazov Invazivne len pre lepsiu orientaciu
     * uzivatela.
     * 
     * @return 
     */
    public static Connection createInvasiveConnection() {
        try {
            Connection conn = connectionPool.reserveConnection();
            count++;
            log.info("VYTVORIL SOM INVAZIVNE CONNECTION:" + count );
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
        count--;
//        log.info("UVOLNIL SOM INVASIVE, ALEBO NON INVASIVE CONNECTION: " + count);

    }

    // 5.
    /**
     * Factory metoda na tvorbu sql containeru.
     * @param TableName
     * @return 
     * @throws java.sql.SQLException
     */
    public static SQLContainer createSqlContainera(String TableName) throws SQLException {

        TableQuery q1 = new TableQuery(TableName, connectionPool);
        q1.setVersionColumn("id");
        SQLContainer tableContainer = new SQLContainer(q1);
        return tableContainer;

    }

}
