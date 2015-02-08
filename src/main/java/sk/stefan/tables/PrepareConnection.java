package sk.stefan.tables;

import com.vaadin.data.util.sqlcontainer.query.generator.DefaultSQLGenerator;
import com.vaadin.data.util.sqlcontainer.query.generator.SQLGenerator;

public class PrepareConnection {

	public enum DB {
		MYSQL, POSTGRESQL;
	}

	/* 0 = HSQLDB, 1 = MYSQL, 2 = POSTGRESQL, 3 = MSSQL, 4 = ORACLE */
	public static final DB db = DB.MYSQL;

	/* DB Drivers, urls, usernames and passwords */
	public static String dbDriver;
	public static String dbURL;
	public static String dbUser;
	public static String dbPwd;
	
	
	/* SQL Generator used during the testing */
	public static SQLGenerator sqlGen;

	/* Set DB-specific settings based on selected DB */
	static {
		sqlGen = new DefaultSQLGenerator();
		switch (db) {
		
		case MYSQL:
			dbDriver = "com.mysql.jdbc.Driver";
			dbURL = "jdbc:mysql://localhost/kosvopo4";
			dbUser = "root";
			dbPwd = "";
	
			break;
			
		case POSTGRESQL:
			dbDriver = "org.postgresql.Driver";
			dbURL = "jdbc:postgresql://localhost/kosvopo5";
			dbUser = "stefan";
			dbPwd = "";
			
			break;
		}
	}
}
