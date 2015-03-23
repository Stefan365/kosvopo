package sk.stefan.DBconnection;

import com.vaadin.data.util.sqlcontainer.query.generator.DefaultSQLGenerator;
import com.vaadin.data.util.sqlcontainer.query.generator.SQLGenerator;

/* Set the DB used for testing here! */
public class PrepDBconn {

    public enum DB {

        MYSQL, POSTGRESQL;
    }

    /* 0 = HSQLDB, 1 = MYSQL, 2 = POSTGRESQL, 3 = MSSQL, 4 = ORACLE */
    // public static final DB db = DB.POSTGRESQL;
    public static final DB db = DB.MYSQL;

    /* Auto-increment column offset (HSQLDB = 0, MYSQL = 1, POSTGRES = 1) */
    public static int offset;

    //create table:
    public static String createTablePre;
    public static String createTablePost;


    /* DB Drivers, urls, usernames and passwords */
    public static String dbDriver;
    public static String dbURL;
    public static String dbUser;
    public static String dbPwd;

	//public static String[] versionStatements;
    /* SQL Generator used during the testing */
    public static SQLGenerator sqlGen;

    /* Set DB-specific settings based on selected DB */
    static {
        sqlGen = new DefaultSQLGenerator();
        switch (db) {

            case MYSQL:
                dbDriver = "com.mysql.jdbc.Driver";
                dbURL = "jdbc:mysql://localhost/kosvopo7?zeroDateTimeBehavior=convertToNull";
                dbUser = "root";
                dbPwd = "";

                createTablePre = "CREATE TABLE ";
                createTablePost = " (ID INT not null AUTO_INCREMENT, FIRST_NAME varchar(50), LAST_NAME varchar(50), "
                        + "COMPANY varchar(50), primary key(ID))";

			// versionStatements = new String[] {
                // "create table VERSIONED (ID integer auto_increment not null, TEXT varchar(255), VERSION tinyint default 0, primary key(ID))",
                // "CREATE TRIGGER upd_version BEFORE UPDATE ON VERSIONED"
                // + " FOR EACH ROW SET NEW.VERSION = OLD.VERSION+1" };
                break;

            case POSTGRESQL:
                dbDriver = "org.postgresql.Driver";
                dbURL = "jdbc:postgresql://localhost/kosvopo9";
                dbUser = "stefan";
                dbPwd = "";
                createTablePre = "CREATE TABLE ";
                createTablePost = " (\"ID\" INTEGER serial primary key, \"FIRST_NAME\" VARCHAR(50),  \"LAST_NAME\" VARCHAR(50), "
                        + " \"COMPANY\" VARCHAR(50))";

                break;
        }
    }
}
