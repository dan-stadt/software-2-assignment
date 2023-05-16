package helper;

import controller.LoginController;
import main.JDBC;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerQuery {
    public static int insertCustomer (String name, String address, String postalCode, String phone, int division) throws SQLException {
        String sql = "INSERT INTO customers Customer_Name, Address, Postal_Code, Phone VALUES(?, ?, ?, ?)"; //TODO: Confirm works
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        String date = LocalDate.now().toString();
        String user = LoginController.getUsername();
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        return ps.executeUpdate();
    }
    public static ResultSet selectAll () throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    }
}
