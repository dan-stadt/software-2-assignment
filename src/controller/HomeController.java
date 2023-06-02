package controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
import helper.UserQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Appointment;
import main.Contact;
import main.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static helper.AppointmentQuery.getAppointmentList;

public class HomeController implements Initializable {
    /**
     * Button to exit the Home window.
     */
    public Button exitButton;
    /**
     * The FXML AnchorPane of the Home window.
     */
    public AnchorPane homeWindow;

    /**
     * Closes the Home window.
     */
    public void close(){
        Stage stage = (Stage) homeWindow.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens the Home window.
     * @throws IOException Exception thrown if error locating or opening Home FXML file.
     */
    public void open() throws IOException{
        Parent root = FXMLLoader.load(HomeController.class.getResource("../view/home.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Home");
        stage.show();
    }

    /**
     * Initializes the Home Screen.
     * @param url The path of the Home FXML file.
     * @param resourceBundle The Home FXML file object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setCancelButton(true);
    }

    /**
     * Opens the Appointment window and closes the current Home window.
     * @param actionEvent Button is clicked.
     * @throws IOException Exception thrown if error opening the Appointment FXML file.
     */
    public void onAppointmentClicked(ActionEvent actionEvent) throws IOException {
        AppointmentController appointments = new AppointmentController();
        appointments.open();
        close();
    }

    /**
     * Opens the Customer window and closes the current Home window.
     * @param actionEvent Button is clicked.
     * @throws IOException Exception thrown if error opening the Customer FXML file.
     */
    public void onCustomerClicked(ActionEvent actionEvent) throws IOException {
        CustomerController customer = new CustomerController();
        customer.open();
        close();
    }

    /**
     *  Generates a report with the total appointments for each customer and the next appointment.
     * @param actionEvent Button is clicked.
     */
    //TODO: Generate reports
    public void onCustomerReportClicked(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> appointmentList= AppointmentQuery.getAppointmentList();
        ObservableList<Customer> customerList = CustomerQuery.getCustomerList();
        //Loop through appointment list
        for (Appointment appointment : appointmentList){
            int customerId = appointment.getAppointmentId();
            for (Customer customer : customerList){
                if (customerId == customer.getId()){
                    customer.addAppointment(appointment);
                    if (customer.getTotalAppointments() < 2) customer.setNextAppointment(appointment);
                    else{
                        LocalDateTime appointmentTime = appointment.getStartDateTime();
                        LocalDateTime nextAppointment = customer.getNextAppointment().getStartDateTime();
                        if (appointmentTime.isBefore(nextAppointment)){
                            customer.setNextAppointment(appointment);
                        }
                    }
                }
            }
        }
        StringBuilder alertStringBuilder = new StringBuilder();
        for (Customer customer : customerList) {
            alertStringBuilder.append(customer.getName());
            alertStringBuilder.append("\n\n Total Appointments: ");
            int totalAppointments = customer.getTotalAppointments();
            alertStringBuilder.append(totalAppointments);
            if (totalAppointments > 0) {
                alertStringBuilder.append("\n Next appointment is with ");
                Appointment appointment = customer.getNextAppointment();
                alertStringBuilder.append(appointment.getContactId());
                alertStringBuilder.append(" at ");
                alertStringBuilder.append(appointment.getStart());
            }
            alertStringBuilder.append("\n\n");
        }
        String alertText = alertStringBuilder.toString();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, alertText);
        alert.setTitle("Customer Report");
        alert.setHeaderText("Next appoint and total appointments for each customer.");
        alert.showAndWait();

    }

    /**
     * Generates a report of the schedule for each contact that includes appointment ID, title,
     * type and description, start date and time, end date and time, and customer ID
     * @param actionEvent Button is clicked.
     */
    public void onContactReportClicked(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> appointmentList= AppointmentQuery.getAppointmentList();
        ObservableList<Contact> contactList = AppointmentQuery.getContactList();
        for (Appointment appointment : appointmentList){
            int contactId = appointment.getContactId();
            for (Contact contact : contactList){
                if (contactId == contact.getContactId()){
                    contact.addAppointment(appointment);
                    break;
                }
            }
        }
        StringBuilder alertStringBuilder = new StringBuilder();
        for (Contact contact : contactList){
            alertStringBuilder.append("\n Appointments for ");
            alertStringBuilder.append(contact.getContactName());
            alertStringBuilder.append(":\n\n");
            for (Appointment appointment : contact.getAppointmentList()){
                alertStringBuilder.append("#");
                alertStringBuilder.append(appointment.getAppointmentId());
                alertStringBuilder.append(" ");
                alertStringBuilder.append(appointment.getTitle());
                alertStringBuilder.append(" ");
                alertStringBuilder.append(appointment.getType());
                alertStringBuilder.append(" ");
                alertStringBuilder.append(appointment.getDescription());
                alertStringBuilder.append(" starts at ");
                alertStringBuilder.append(appointment.getStart());
                alertStringBuilder.append(", ends at ");
                alertStringBuilder.append(appointment.getEnd());
                alertStringBuilder.append(" for Customer #");
                alertStringBuilder.append(appointment.getCustomerId());
                alertStringBuilder.append("\n");
            }
        }
        String alertText = alertStringBuilder.toString();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, alertText);
        alert.setTitle("Appointments by Contact");
        alert.setHeaderText("Appointments with appointment ID, title, type, description, and start/end times, and Customer ID.");
        alert.showAndWait();
    }

    /**
     * Generates a report with the total number of customer appointments by type and month.
     * @param actionEvent Button is clicked.
     */
    public void onSummaryReportClicked(ActionEvent actionEvent) throws SQLException {
        class MonthCount{   //Local class for ArrayList to count types of appointments.
            Month month;
            int count;
            MonthCount(Month month, int count){
                this.month = month;
                this.count = count;
            }
        }
        class TypeCount{    //Local class for ArrayList to count types of appointments.
            String type;
            int count;
            TypeCount(String type, int count){
                this.type = type;
                this.count = count;
            }
        }
        ObservableList<Appointment> appointmentList = getAppointmentList();
        ArrayList<MonthCount> monthList = new ArrayList<>(13);
        ArrayList<TypeCount> typeList = new ArrayList<>();
        for (int i = 1; i < Month.values().length + 1; i++){
            Month month = Month.of(i);
            MonthCount monthCount = new MonthCount(month, 0);
            monthList.add(monthCount);
        }
        for (Appointment appointment : appointmentList){
            Month month = appointment.getDate().getMonth();
            int monthNum = month.getValue();
            monthList.get(monthNum).count++;
            String type = appointment.getType();
            boolean found = false;
            for (TypeCount typeCount : typeList){
                if (type.equals(typeCount.type)){        //Increase the count of that type if found
                    typeCount.count++;
                    found = true;
                }
            }
            if (!found){    //If type is not found, create a new type in the list with a count of 1.
                TypeCount typeCount = new TypeCount(type, 1);
                typeList.add(typeCount);
            }
        }
        StringBuilder alertStringBuilder = new StringBuilder();
        alertStringBuilder.append("Appointments by month: \n\n");
        for (MonthCount monthCount : monthList){
            String monthString =  monthCount.month.toString();
            alertStringBuilder.append(monthString);
            alertStringBuilder.append(": ");
            alertStringBuilder.append(monthCount.count);
            alertStringBuilder.append(" appointments.\n");
        }
        alertStringBuilder.append("\nAppointments by Type: \n\n");
        for (TypeCount typeCount : typeList){
            alertStringBuilder.append(typeCount.type);
            alertStringBuilder.append(": ");
            alertStringBuilder.append(typeCount.count);
            alertStringBuilder.append(" appointments\n");
        }
        String alertText = alertStringBuilder.toString();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, alertText);
        alert.setTitle("Summary report");
        alert.setHeaderText("Customer appointments by type and month.");
        alert.showAndWait();
    }

    /**
     * Exits the Home window and opens the Login window.
     * @param actionEvent Button is clicked.
     * @throws IOException Exception is thrown if the Login FXML file cannot be opened.
     */
    public void onExitClicked(ActionEvent actionEvent) throws IOException{
        LoginController login = new LoginController();
        login.open();
        close();
    }
}