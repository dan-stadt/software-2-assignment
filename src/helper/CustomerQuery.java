package helper;

import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerQuery {
    public static int insertCustomer (String name, String address, String postalCode, String phone, int division) throws SQLException {
        String sql = "INSERT INTO customers Customer_Name, Address, Postal_Code, Phone, Division_ID VALUES(?, ?, ?, ?)"; //TODO: Confirm works
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        String date = LocalDate.now().toString();
        String user = LoginController.getUsername();
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, division);
        return ps.executeUpdate();
    }
    public static ResultSet selectAll () throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet selectRegion(int regionCode) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, regionCode);
        return ps.executeQuery();
    }
    public static int getCountryId(String country) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);
        ResultSet result = ps.executeQuery();
        int countryId = 0;
        while (result.next()){
            countryId = result.getInt("Country_ID");
        }
        return countryId;
    }
    public static String getRegion(int regionCode) throws SQLException {
        ResultSet result = selectRegion(regionCode);
        String region = null;
        while(result.next()){
            region = result.getString("Division");
        }
        return region;
    }
    public static String getCountry(int regionCode) throws SQLException {
        ResultSet result = selectRegion(regionCode);
        int countryCode = 0;
        while(result.next()) {
            countryCode = result.getInt(7);
        }
        String sql = "SELECT * FROM countries WHERE Country_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryCode);
        ResultSet countryResult = ps.executeQuery();
        String country = null;
        while(countryResult.next()){
            country = countryResult.getString("Country");
        }
        return country;
    }
    public static ObservableList<String> getCountryList() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        ObservableList<String> countrylist = FXCollections.observableArrayList();
        while (result.next()){
            countrylist.add(result.getString("Country"));
        }
        return countrylist;
    }
    public static ObservableList<String> getDivisionList(String country) throws SQLException {
        int countryCode = getCountryId(country);
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryCode);
        ResultSet result = ps.executeQuery();
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        while (result.next()){
            divisionList.add(result.getString("Division"));
        }
        return divisionList;
    }
}
