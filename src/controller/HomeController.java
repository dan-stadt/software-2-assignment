package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Button exitButton;
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
     *  Generates a report with the next appointment for each customer.
     * @param actionEvent Button is clicked.
     */
    //TODO: Generate reports
    public void onCustomerReportClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("");
    }

    /**
     * Generates a report of the schedule for each contact that includes appointment ID, title,
     * type and description, start date and time, end date and time, and customer ID
     * @param actionEvent Button is clicked.
     */
    public void onEmployeeReportClicked(ActionEvent actionEvent) {

    }

    /**
     * Generates a report with the total number of customer appointments by type and month.
     * @param actionEvent Button is clicked.
     */
    public void onSummaryReportClicked(ActionEvent actionEvent) {

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