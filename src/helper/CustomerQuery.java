package helper;

import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Customer;
import main.JDBC;
import java.sql.*;
import java.time.Instant;

public class CustomerQuery {
    /**
     *
     * @param customerId
     * @return
     * @throws SQLException
     */
    public static boolean deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID=?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        return ps.executeUpdate() > 0;
    }

    /**
     *
     * @param divisionId
     * @return
     * @throws SQLException
     */
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

    /**
     *
     * @param country
     * @return
     * @throws SQLException
     */
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

    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getCountryList() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        ObservableList<String> countryList = FXCollections.observableArrayList();
        while (result.next()){
            countryList.add(result.getString("Country"));
        }
        return countryList;
    }

    /**
     *
     * @param regionCode
     * @return
     * @throws SQLException
     */
    public static String getDivision(int regionCode) throws SQLException {
        ResultSet result = selectDivision(regionCode);
        String region = null;
        while(result.next()){
            region = result.getString("Division");
        }
        return region;
    }

    /**
     *
     * @param division
     * @return
     * @throws SQLException
     */
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

    /**
     *
     * @param country
     * @return
     * @throws SQLException
     */
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

    /**
     *
     * @param customer
     * @return
     * @throws SQLException
     */
    public static boolean insertCustomer (Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        Instant now = Instant.now();
        Timestamp timeStamp = Timestamp.from(now);
        String user = LoginController.getUsername();
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostal());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionId());
        ps.setTimestamp(6, timeStamp);
        ps.setString(7, user);
        ps.setTimestamp(8, timeStamp);
        ps.setString(9, user);
        return !ps.execute();
    }

    /**
     *
     * @param customer
     * @return
     * @throws SQLException
     */
    public static boolean isSafeToDelete (Customer customer) throws SQLException{
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        int customerId = customer.getId();
        ps.setInt(1, customerId);
        ResultSet result = ps.executeQuery();
        return !result.next();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomerList () throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String postal = resultSet.getString(4);
            String phone = resultSet.getString(5);
            int regionCode = resultSet.getInt("Division_ID");
            Customer customer = new Customer(id, name, address, postal, phone, regionCode);
            customerList.add(customer);
        }
        return customerList;
    }

    /**
     *
     * @param divisionId
     * @return
     * @throws SQLException
     */
    public static ResultSet selectDivision(int divisionId) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        return ps.executeQuery();
    }

    /**
     *
     * @param customer
     * @return
     * @throws SQLException
     */
    public static boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=?, Last_Update=?, Last_Updated_By=? WHERE Customer_ID=?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,customer.getName());
        ps.setString(2,customer.getAddress());
        ps.setString(3,customer.getPostal());
        ps.setString(4,customer.getPhone());
        ps.setInt(5,customer.getDivisionId());
        Instant now = Instant.now();
        Timestamp timestamp = Timestamp.from(now);
        ps.setTimestamp(6,timestamp);
        ps.setString(7, LoginController.getUsername());
        ps.setInt(8, customer.getId());
        return ps.executeUpdate() > 0;
    }
}
