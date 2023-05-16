package helper;

import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserQuery {
    public static boolean checkLogin (String username, String password) throws SQLException {
        CustomerQuery.customerFields.values();
        String sql = "SELECT * FROM users WHERE User_Name=? AND Password=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        return ps.execute();
    }
}
