package helper;

import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Appointment;
import main.Customer;
import main.JDBC;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class AppointmentQuery {
    public static boolean deleteAppointment(Integer appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID=?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        return ps.executeUpdate() > 0;
    }
    public static ObservableList<Appointment> getAppointmentList() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            int appointmentId = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            Timestamp start = resultSet.getTimestamp("Start");
            Timestamp end= resultSet.getTimestamp("End");
            Integer customerID = resultSet.getInt("Customer_ID");
            Integer userId = resultSet.getInt("User_ID");
            Integer contactId = resultSet.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentId,title, description, location, type, start, end, customerID, userId, contactId);
            appointmentList.add(appointment);
        }
        resultSet.close();
        return appointmentList;
    }
    public static String getcontactName(int contactId) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet result = ps.executeQuery();
        String contact = null;
        if (result.next()){
            contact = result.getString("Contact_Name");
        }
        return contact;
    }
    public static ObservableList<String> getContactList() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        ObservableList<String> contactList = FXCollections.observableArrayList();
        while (result.next()){
            contactList.add(result.getString("Contact_Name"));
        }
        return contactList;
    }
    public static boolean insertAppointment (Customer customer) throws SQLException {
        String sql = "INSERT INTO appointments (Customer_Name, Address, Postal_Code, Phone, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
    /*
    public static int getDivisionId (String division) throws SQLException {
        ResultSet result = ps.executeQuery();
        if(result.next()){
            return result.getInt("Division_ID");
        }
        else{return 0;}
    }
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
*/
}
