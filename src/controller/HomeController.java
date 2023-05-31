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
     *
     */
    public void close(){
        Stage stage = (Stage) homeWindow.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @throws IOException
     */
    public void open() throws IOException{
        Parent root = FXMLLoader.load(HomeController.class.getResource("../view/home.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Home");
        stage.show();
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setCancelButton(true);
    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void appointmentButton(ActionEvent actionEvent) throws IOException {
        AppointmentController appointments = new AppointmentController();
        appointments.open();
        close();
    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onCustomerClicked(ActionEvent actionEvent) throws IOException {
        CustomerController customer = new CustomerController();
        customer.open();
        close();
    }

    /**
     *
     * @param actionEvent
     */
    //TODO: Generate reports
    public void onCustomerReportClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("");
    }

    /**
     *
     * @param actionEvent
     */
    public void onEmployeeReportClicked(ActionEvent actionEvent) {

    }
    public void onSummaryReportClicked(ActionEvent actionEvent) {

    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onExitClicked(ActionEvent actionEvent) throws IOException{
        LoginController login = new LoginController();
        login.open();
        close();
    }
}