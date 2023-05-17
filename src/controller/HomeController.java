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

    public static void open() throws IOException{
        HomeController homeController = new HomeController();
        Parent root = FXMLLoader.load(HomeController.class.getResource("../view/home.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Home");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setCancelButton(true);
    }
    public void appointmentButton(ActionEvent actionEvent) throws IOException {
        AppointmentController appointments = new AppointmentController();
        appointments.open();
        close();
    }
    public void customerButton(ActionEvent actionEvent) throws IOException {
        CustomerController customer = new CustomerController();
        customer.open();
        close();
    }
    public void customerReportBtn(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //TODO: Generate report for summary by type and month
        alert.setContentText("");
    }
    public void employeeReportBtn(ActionEvent actionEvent) {

    }
    public void summaryReportBtn(ActionEvent actionEvent) {

    }


    public void exitClicked(ActionEvent actionEvent) throws IOException{
        LoginController login = new LoginController();
        login.open();
        close();
    }
    public void close(){
        Stage stage = (Stage) homeWindow.getScene().getWindow();
        stage.close();
    }
}