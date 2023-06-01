package helper;

import main.JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQuery {
    /**
     * Checks if username and password match the combination in the database.
     * @param username Takes the username entered in the associated field.
     * @param password Takes the password entered in the associated field.
     * @return returns true if matching username/password found, otherwise false.
     * @throws SQLException Exception is thrown if there is an error with the SQL statement or parameter.
     */
    public static boolean checkLogin (String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE (User_Name= ? AND Password= ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet results = ps.executeQuery();
        return results.next();
    }

    /**
     * Retrieves a User ID from a username
     * @param userName Takes the username to search for its ID
     * @return Returns an integer with the value of the User ID. Returns null if not found.
     * @throws SQLException Exception thrown if error in the SQL statement or parameter.
     */
    public static int getUserId(String userName) throws SQLException {
        String sql ="SELECT * FROM users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        return resultSet.getInt("User_ID");
    }
}
