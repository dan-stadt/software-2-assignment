package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Customer;
import main.JDBC;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerQuery {
    public static String getCountry(int divisionId) throws SQLException {
        ResultSet result = selectDivision(divisionId);
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
    public static int deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID=?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        return ps.executeUpdate();
    }
    public static String getDivision(int regionCode) throws SQLException {
        ResultSet result = selectDivision(regionCode);
        String region = null;
        while(result.next()){
            region = result.getString("Division");
        }
        return region;
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
    public static int insertCustomer (Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostal());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionId());
        return ps.executeUpdate();
    }
    public static ResultSet selectAll () throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet selectDivision(int divisionId) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        return ps.executeQuery();
    }
    public static int getDivisionId (String division) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division=?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet result = ps.executeQuery();
        if(result.next()){
            return result.getInt("Division_ID");
        }
        else{return 0;}
    }
}
