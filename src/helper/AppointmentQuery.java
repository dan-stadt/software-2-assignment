package helper;

import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Appointment;
import main.Contact;
import main.JDBC;
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
    public static ObservableList<Contact> getContactList() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        while (result.next()){
            String contactName = result.getString("Contact_Name");
            Integer contactId = result.getInt("Contact_Id");
            String email = result.getString("Email");
            Contact contact = new Contact(contactId, contactName, email);
            contactList.add(contact);
        }
        return contactList;
    }
    public static boolean insertAppointment (Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End," +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        Instant now = Instant.now();
        Timestamp timeStamp = Timestamp.from(now);
        String user = LoginController.getUsername();
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, appointment.getStartTimestamp());
        ps.setTimestamp(6, appointment.getEndTimestamp());
        ps.setTimestamp(7, timeStamp);
        ps.setString(8, user);
        ps.setTimestamp(9, timeStamp);
        ps.setString(10, user);
        ps.setInt(11, appointment.getCustomerId());
        ps.setInt(12, appointment.getUserId());
        ps.setInt(13, appointment.getContactId());
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
