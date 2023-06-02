package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection = null;  // Connection Interface
    private static PreparedStatement preparedStatement;

    /**
     * Establish the connection between Java and MySQL
     */
    public static void makeConnection() {
         try {
             Class.forName(driver); // Locate Driver
             // password = Details.getPassword(); // Assign password
             connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
             System.out.println("Connection successful!");
         }
         catch(ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
         }
         catch(SQLException e) {
            System.out.println("Error:" + e.getMessage());
         }
     }

    /**
     * Retrieve the Connection object.
     * @return Returns a Connection object
     */
    public static Connection getConnection() { return connection; }

    /**
     * Close the Java-MySQL Database connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a PreparedStatement
     * @param sqlStatement Takes a String of the SQL Statement
     * @param conn Takes a Connection object of the Java-MySQL Database Connection
     * @throws SQLException Exception thrown if error in the SQL Statement or Parameter
     */
    public static void makePreparedStatement(String sqlStatement, Connection conn) throws SQLException {
        if (conn != null)
            preparedStatement = conn.prepareStatement(sqlStatement);
            else
               System.out.println("Prepared Statement Creation Failed!");
     }

    /**
     * Retrieve the JDBC's Prepared Statement
     * @return Returns a PreparedStatement object
     * @throws SQLException Exception thrown if error in the SQL Statement or Parameters
     */
    public static PreparedStatement getPreparedStatement() throws SQLException {
        if (preparedStatement != null)
            return preparedStatement;
        else System.out.println("Null reference to Prepared Statement");
            return null;
    }
}