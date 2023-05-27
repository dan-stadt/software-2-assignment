package helper;

import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import main.Appointment;
import main.Contact;
import main.JDBC;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeParseException;

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
        return parseAppointmentList(ps.executeQuery());
    }
    private static ObservableList<Appointment> parseAppointmentList(ResultSet resultSet) throws SQLException {
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
    public static String getContactName(int contactId) throws SQLException {
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
            int contactId = result.getInt("Contact_Id");
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
    public static ObservableList<Appointment> getMonthlyAppointments(DatePicker tableDatePicker) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        LocalDate selectedDate = tableDatePicker.getValue();
        if (selectedDate == null) selectedDate = LocalDate.now();
        int year = selectedDate.getYear();
        Month month = selectedDate.getMonth();
        int firstDayOfMonth = 1;
        LocalDate firstDate = LocalDate.of(year, month, firstDayOfMonth);
        LocalDateTime firstDateTime = LocalDateTime.of(firstDate, LocalTime.MIN);
        int lastDay = 31;
        boolean check = true;
        while (check) {
            try{
                MonthDay lastMonthDay = MonthDay.of(month, lastDay);
                check = false;
            }
            catch (DateTimeParseException e){
                lastDay -=  1;
            }
        }
        LocalDate lastDate = LocalDate.of(year, month, lastDay);
        LocalDateTime lastDateTime = LocalDateTime.of(lastDate, LocalTime.MAX);
        Timestamp firstTimestamp = Timestamp.valueOf(firstDateTime);
        Timestamp lastTimestamp = Timestamp.valueOf(lastDateTime);
        ps.setTimestamp(1, firstTimestamp);
        ps.setTimestamp(2, lastTimestamp);
        return parseAppointmentList(ps.executeQuery());
    }
    public static boolean updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, " +
                "End=?, Last_Update=?, Last_Updated_By=?, User_ID=?, Contact_ID=? " +
                "WHERE Appointment_ID=?";
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
        ps.setInt(9, appointment.getUserId());
        ps.setInt(10, appointment.getContactId());
        ps.setInt(11,appointment.getAppointmentId());
        return ps.executeUpdate() > 0;
    }
    public static ObservableList<Appointment> getWeeklyAppointments(DatePicker tableDatePicker) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        LocalDate selectedDate = tableDatePicker.getValue();
        if (selectedDate == null) selectedDate = LocalDate.now();
        int year = selectedDate.getYear();
        Month month = selectedDate.getMonth();
        int day = selectedDate.getDayOfMonth();
        LocalDate firstDate = LocalDate.of(year, month, day);
        LocalDate lastDate = LocalDate.of(year, month, day);
        while(!firstDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            firstDate = firstDate.minusDays(1);
        }
        while (!lastDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            lastDate = lastDate.plusDays(1);
        }
        LocalDateTime firstDateTime = LocalDateTime.of(firstDate,LocalTime.MIN);
        LocalDateTime lastDateTime = LocalDateTime.of(lastDate,LocalTime.MAX);
        Timestamp firstTimeStamp = Timestamp.valueOf(firstDateTime);
        Timestamp lastTimeStamp = Timestamp.valueOf(lastDateTime);
        ps.setTimestamp(1, firstTimeStamp);
        ps.setTimestamp(2, lastTimeStamp);
        return parseAppointmentList(ps.executeQuery());
    }

    public static boolean isConflicting(Appointment appointment) throws SQLException{
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ? OR End BETWEEN ? AND ? AND Appointment_ID <> ? AND Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        Timestamp startTime = appointment.getStartTimestamp();
        Timestamp endTime = appointment.getEndTimestamp();
        int appointmentId = appointment.getAppointmentId();
        int customerId = appointment.getCustomerId();
        ps.setTimestamp(1, startTime);
        ps.setTimestamp(2, endTime);
        ps.setTimestamp(3, startTime);
        ps.setTimestamp(4, endTime);
        ps.setInt(5, appointmentId);
        ps.setInt(6, customerId);
        ResultSet result = ps.executeQuery();
        return !result.next();
    }
}
