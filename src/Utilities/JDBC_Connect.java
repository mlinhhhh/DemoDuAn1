package Utilities;

//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
//import java.sql.Connection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC_Connect {
//    public static Connection getConnection() {
//        SQLServerDataSource sds = new SQLServerDataSource();
//        sds.setServerName("MSI\\SQLEXPRESS");
//        sds.setDatabaseName("FINALASS_FPOLYSHOP_FA22_SOF205__SOF2041");
//        sds.setUser("sa");
//        sds.setPassword("123456");
//        sds.setPortNumber(1433);
//        try {
//            Connection conn = sds.getConnection();
//            return conn;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    public static final String HOSTNAME = "MSI\\SQLEXPRESS";
    public static final String PORT = "1433";
    public static final String DBNAME = "FINALASS_FPOLYSHOP_FA22";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "123456";

    /**
     * Get connection to MSSQL Server
     *
     * @return Connection
     */
    public static Connection getConnection() {

        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://" + HOSTNAME + ":" + PORT + ";"
                + "databaseName=" + DBNAME + ";encrypt=true;trustServerCertificate=true;";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionUrl, USERNAME, PASSWORD);
        } // Handle any errors that may have occurred.
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}
