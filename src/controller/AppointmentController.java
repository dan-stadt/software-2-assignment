package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    @FXML
    public AnchorPane appointmentWindow;
    @FXML
    public TableView<Appointment> appointmentTable;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
    //TODO: Update to have date/time
    public TableColumn<Appointment, Locale> locationColumn;
    @FXML
    public TableColumn<Appointment, String> contactColumn;
    @FXML
    public TableColumn<Appointment, String> typeColumn;
    @FXML
    public TableColumn<Appointment, Time> startsAtColumn;
    @FXML
    public TableColumn<Appointment, Time> endsAtColumn;
    @FXML
    public TableColumn<Appointment, Integer> customerIdColumn;
    @FXML
    public TableColumn<Appointment, Integer> userIdColumn;
    @FXML
    public TextField appointmentIdField;
    @FXML
    public TextField titleField;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField locationField;
    @FXML
    public TextField typeField;
    @FXML
    public TextField contactField;
    @FXML
    public TextField startsAtField;
    @FXML
    public TextField endsAtField;
    @FXML
    public TextField customerIdField;
    @FXML
    public TextField userIdField;
    @FXML
    public Button customerButton;
    @FXML
    public Button editButton;
    @FXML
    public Button homeButton;
    @FXML
    public Button newButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setCancelButton(true);
    }
    public void close() {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }

    public void onCustomerClicked(ActionEvent actionEvent) throws IOException {
        CustomerController customer = new CustomerController();
        customer.open();
        close();
    }
    public void onEditClicked(ActionEvent actionEvent) {
    }
    public void onFieldEntered(ActionEvent actionEvent) {
    }
    public void onHomeClicked(ActionEvent actionEvent) throws IOException {
        HomeController home = new HomeController();
        home.open();
        close();
    }
    public void onNewClicked(ActionEvent actionEvent) {
    }
    public void open() throws IOException {
        Parent root = FXMLLoader.load(AppointmentController.class.getResource("../view/appointment.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Appointments");
        stage.show();
    }
}
