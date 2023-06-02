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
     * Deletes a Customer from the SQL Database.
     * @param customerId Takes the Customer ID of the Customer to delete.
     * @return Returns true if the deletion successful, false if no Customer deleted due to failure to find ID or other error.
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
     */
    public static boolean deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID=?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        return ps.executeUpdate() > 0;
    }

    /**
     * Retrieves the Country String for the Division ID from the SQL Database.
     * Used to properly format Country ComboBox when a Customer is selected.
     * @param divisionId Takes an integer of the Division ID as input.
     * @return Returns a String of the Country associated with the Division ID.
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
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
     * Retrieves the Country ID integer from the SQL Database
     * @param country Takes the String of the Country Name as input
     * @return Returns an integer of the County ID
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
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
     *  Retrieves the list of countries as an ObservableList from the SQL Database.
     * @return Returns an ObservableList with Strings of the Country names.
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
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
     * Retrieves the Division Name associated with a Division ID from the SQL Database.
     * @param divisionId Takes the Division ID to search as input.
     * @return Returns the Division Name associated with the Division ID.
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
     */
    public static String getDivision(int divisionId) throws SQLException {
        ResultSet result = selectDivision(divisionId);
        String region = null;
        while(result.next()){
            region = result.getString("Division");
        }
        return region;
    }

    /**
     * Retrieves the Division ID associated with a Division Name from the SQL Database.
     * @param division Takes the Division Name to search as Input.
     * @return Returns an integer of the Division ID.
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
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
     * Retrieves an ObservableList of Divisions within a Country from the SQL Database.
     * Uses to update the Division ComboBox when the selected Country changes.
     * @param country Takes a Country Name as input.
     * @return Returns an ObservableList of Strings with the Division names.
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
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
     * Adds a new Customer to the SQL Database.
     * @param customer Takes a Customer object as input.
     * @return Returns true if Customer successfully added to Database, false if Customer not added.
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
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
     * Checks if Customer has any associated appointments, which determines if a Customer can be deleted.
     * @param customer Takes a Customer object as input.
     * @return Returns true if Customer does not have any associated Appointments, false if one or more Appointments for Customer.
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
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
     * Retrieves a list of all Customers in the SQL Database
     * @return Returns an ObservableList of Customers
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
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
     * Returns a ResultSet of a Division record(s). from the SQL Database that match the Division ID input.
     * @param divisionId Takes the Division ID to search as input.
     * @return Returns a ResultSet with the matching Division record(s).
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
     */
    public static ResultSet selectDivision(int divisionId) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        return ps.executeQuery();
    }

    /**
     * Updates a Customer Record in the SQL Database with the values of an input Customer object.
     * @param customer Takes a Customer object as input
     * @return Returns true if update successful, false if Customer ID not found or Customer unable to update.
     * @throws SQLException exception thrown if error in SQL statement or parameter(s).
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
