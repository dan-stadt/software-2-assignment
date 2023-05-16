package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void appointmentButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/appointments.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Appointments");
        stage.show();
    }
    public void customerButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/customers.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customers");
        stage.show();
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


}