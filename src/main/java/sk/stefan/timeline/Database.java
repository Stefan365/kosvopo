package sk.stefan.timeline;



import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Random;


public class Database {
    private SimpleJDBCConnectionPool connectionPool;

    public void initConnectionPool() {
        try {
            connectionPool = new SimpleJDBCConnectionPool(
                    "org.hsqldb.jdbc.JDBCDriver",
                    "jdbc:hsqldb:mem:sqlcontainer", "SA", "", 2, 5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initDatabase(int dataPoints, long startTime, long endTime) {
        try {
            Connection conn = connectionPool.reserveConnection();
            Statement statement = conn.createStatement();
            try {
                statement.execute("drop table cpuload");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement
                    .execute("create table cpuload (dateTime timestamp primary key, value double)");

            populateWithData(conn, startTime, endTime, dataPoints);
            statement.close();
            conn.commit();
            connectionPool.releaseConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateWithData(Connection c, long startTime, long endTime,
            int dataPoints) throws SQLException {

        double cpuLoad = 0.0;
        Random r = new Random(System.currentTimeMillis());

        // Simulate data with given interval during the period
        PreparedStatement ps = c
                .prepareStatement("INSERT INTO cpuload (dateTime,value) VALUES (?,?)");
        long intervalInMs = (endTime - startTime) / dataPoints;
        for (int i = 0; i < dataPoints; i++) {
            long timestamp = startTime + i * intervalInMs;
            // Increase or decrease the load by a maximum of 0.5
            cpuLoad += (r.nextDouble() - 0.5);
            if (cpuLoad < 0.0) {
                cpuLoad = 0.0;
            }
            ps.setTimestamp(1, new Timestamp(timestamp));
            ps.setDouble(2, cpuLoad);
            ps.executeUpdate();

            // System.out.println(timestamp + "," + cpuLoad);
        }

        System.out.println(dataPoints + " rows inserted into database");
    }

    public double getMaxValue() throws SQLException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Connection c = null;
        try {
            c = getConnectionPool().reserveConnection();
            s = c.prepareStatement("SELECT max(value) FROM cpuload");
            rs = s.executeQuery();
            rs.next();
            return rs.getDouble(1);
        } finally {
            if (c != null) {
                getConnectionPool().releaseConnection(c);
            }
            close(rs);
            close(s);
        }
    }

    private void close(PreparedStatement s) {
        if (s == null) {
            return;
        }
        try {
            s.close();
        } catch (SQLException e) {
        }

    }

    private void close(ResultSet s) {
        if (s == null) {
            return;
        }
        try {
            s.close();
        } catch (SQLException e) {
        }

    }

    public JDBCConnectionPool getConnectionPool() {
        return connectionPool;
    }
}