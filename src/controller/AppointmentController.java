package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    @FXML
    public Button homeButton;
    @FXML
    public AnchorPane appointmentWindow;

    public void open() throws IOException {
        Parent root = FXMLLoader.load(AppointmentController.class.getResource("../view/appointments.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Appointments");
        stage.show();
    }
    public void close() {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setCancelButton(true);
    }

    public void homeClicked(ActionEvent actionEvent) throws IOException{
        HomeController home = new HomeController();
        home.open();
        close();
    }
    public void customerClicked(ActionEvent actionEvent) throws IOException {
        CustomerController customer = new CustomerController();
        customer.open();
        close();
    }
}
