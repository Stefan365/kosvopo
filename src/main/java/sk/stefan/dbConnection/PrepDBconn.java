package sk.stefan.dbConnection;

import java.util.ResourceBundle;

/**
 * Trieda pripravuje spojenie s DB.
 */
public class PrepDBconn {


    public enum DB {
        MYSQL, POSTGRESQL;
    }

    /* 0 = HSQLDB, 1 = MYSQL, 2 = POSTGRESQL, 3 = MSSQL, 4 = ORACLE */
    // public static final DB db = DB.POSTGRESQL;
    public static final DB db = DB.MYSQL;

    /* DB Drivers, urls, usernames and passwords */
    public static String dbDriver;
    public static String dbURL;
    public static String dbUser;
    public static String dbPwd;

    static {
        
      ResourceBundle props = ResourceBundle.getBundle("db_conn");
        
        switch (db) {

            case MYSQL:
                
                dbDriver = props.getString("dbDriver");
                dbURL = props.getString("dbUrl");
                dbUser = props.getString("dbUser");
                dbPwd = props.getString("dbPwd");
                
                break;

            case POSTGRESQL:
                dbDriver = "org.postgresql.Driver";
                dbURL = "jdbc:postgresql://localhost/kosvopo9";
                dbUser = "stefan";
                dbPwd = "";
                
                break;
        }
    }
}
