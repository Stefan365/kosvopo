package sk.stefan.tables;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class CreateContainer {

	private static JDBCConnectionPool connectionPool = null;
	
	
	static {
		connectToDb(PrepareConnection.dbDriver, PrepareConnection.dbURL, PrepareConnection.dbUser, 
				PrepareConnection.dbPwd, 2, 20);
	}

	// 0. konstruktor:
	public CreateContainer(String tableName) {
		createContainer(tableName);
	}
	

	// 1.
	private static void connectToDb(String dbDriver, String dbURL, String dbUser,
			String dbPwd, int iniCon, int maxCon) {
		try {
			connectionPool = new SimpleJDBCConnectionPool(dbDriver, dbURL,
					dbUser, dbPwd, iniCon, maxCon);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	// 2.
	/*
	public static void createConn() throws SQLException {
		conn = connectionPool.reserveConnection();
		statement = conn.createStatement();
	}*/
	
	

	// 3.
	public static SQLContainer createContainer(String tableName) {
		try {
			TableQuery q1 = new TableQuery(tableName, connectionPool);
			q1.setVersionColumn("ksp_id");
			return (new SQLContainer(q1));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		
	}

	//4.
	public static List<String> getColNames(String tableName){
		Collection<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		
		SQLContainer container = CreateContainer.createContainer(tableName);
		
		list = (Collection<String>) container.getContainerPropertyIds();
		for (String lis : list) {
			list2.add(lis);
			System.out.println("*" + lis + "*");
			System.out.println("%karol maly%");
			
		}
		
		return list2;
	}
	

	//5.
	public static JDBCConnectionPool getPool() {
		return connectionPool;
	}
	
}
