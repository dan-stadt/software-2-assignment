package helper;

import main.JDBC;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class CustomerQuery {
    public enum customerFields{
        Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID;
    }
    public static int insertCustomer (String name, String address, String postalCode, String phone, int division) throws SQLException {
        String sql = "INSERT INTO customers (" + Arrays.toString(customerFields.values()) + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?"; //TODO: Confirm works
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        int id = 1;         //TODO: Update to select unused ID
        String date = "";    //TODO: Update to calculate today's today
        String user = "";    //TODO: Update to match current user, taken from login
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setString(6, date);
        ps.setString(7, user);
        ps.setString(8, date);
        ps.setString(9, user);
        ps.setInt(10, division);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
