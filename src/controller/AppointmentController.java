package controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    public TableColumn<Appointment, Time> startColumn;
    @FXML
    public TableColumn<Appointment, Time> endColumn;
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
    @FXML
    public Button deleteButton;
    @FXML
    public Button saveButton;
    @FXML
    public ComboBox<String> contactBox;
    public RadioButton weeklyButton;
    public RadioButton monthlyButton;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private Appointment selectedAppointment;
    private boolean newInProcess;
    private boolean editInProcess;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setCancelButton(true);
        setDisableFields(true);
        try{
            appointmentList = AppointmentQuery.getAppointmentList();
            for (Appointment appointment: appointmentList) {
                int contactId = appointment.getContactId();
                String contactName = AppointmentQuery.getcontactName(contactId);
                appointment.setContact(contactName);
            }
            contactBox.setItems(AppointmentQuery.getContactList());
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading appointments.");
            alert.showAndWait();
        }
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        appointmentTable.setItems(appointmentList);
        appointmentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setEditInProcess(false);
        setNewInProcess(false);
        //TODO: Lambda README
        ListChangeListener<Appointment> tableListener = change -> {
            if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
                setSelectedAppointment(appointmentTable.getSelectionModel().getSelectedItem());
                if (!isNewInProcess() && !isEditInProcess()) {
                    setFields();
                    editButton.setVisible(true);
                    deleteButton.setVisible(true);
                } else confirmSelect();
            }
        };
        appointmentTable.getSelectionModel().getSelectedItems().addListener(tableListener);
    }
    private void clearFields() {
        appointmentIdField.setText("");
        titleField.setText("");
        descriptionField.setText("");
        locationField.setText("");
        contactBox.setValue("");
        typeField.setText("");
        startsAtField.setText("");
        endsAtField.setText("");
        customerIdField.setText("");
        userIdField.setText("");
    }
    private void confirmSelect() {
        //TODO: Write method
    }
    private void setDisableFields(boolean disable){
        titleField.setDisable(disable);
        descriptionField.setDisable(disable);
        locationField.setDisable(disable);
        contactBox.setDisable(disable);
        typeField.setDisable(disable);
        startsAtField.setDisable(disable);
        endsAtField.setDisable(disable);
        customerIdField.setDisable(disable);
        userIdField.setDisable(disable);
    }
    private boolean isEditInProcess() {return editInProcess;}
    private boolean isNewInProcess() {return newInProcess;}
    public void close() {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }
    public void isReady(){
        //  scheduling an appointment outside of business hours defined as 8:00 a.m. to 10:00 p.m. EST, including weekends
        //  scheduling overlapping appointments for customers
    }
    public void onCustomerClicked(ActionEvent actionEvent) throws IOException {
        CustomerController customer = new CustomerController();
        customer.open();
        close();
    }
    public void onEditClicked(ActionEvent actionEvent) {
        setDisableFields(false);
        setEditInProcess(true);
    }
    public void onFieldEntered(ActionEvent actionEvent) {saveButton.fire();}
    public void onHomeClicked(ActionEvent actionEvent) throws IOException {
        HomeController home = new HomeController();
        home.open();
        close();
    }
    public void onNewClicked(ActionEvent actionEvent) {
        clearFields();
        deleteButton.setVisible(false);
        editButton.setVisible(false);
        newButton.setVisible(false);
        saveButton.setVisible(true);
        setDisableFields(false);
        setNewInProcess(true);
    }
    public void onDeleteClicked(ActionEvent actionEvent) throws SQLException {
        String type = selectedAppointment.getType();
        if (AppointmentQuery.deleteAppointment(selectedAppointment.getAppointmentId())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, type + " Appointment deleted.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to delete appointment.");
            alert.showAndWait();
        }
    }
    public void open() throws IOException {
        Parent root = FXMLLoader.load(AppointmentController.class.getResource("../view/appointment.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Appointments");
        stage.show();
    }
    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }
    public void setEditInProcess(boolean editInProcess) {
        this.editInProcess = editInProcess;
    }
    private void setFields() {
        appointmentIdField.setText(selectedAppointment.getAppointmentId().toString());
        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        contactBox.setValue(selectedAppointment.getContact());
        typeField.setText(selectedAppointment.getType());
        startsAtField.setText(selectedAppointment.getStart().toString());
        endsAtField.setText(selectedAppointment.getEnd().toString());
        customerIdField.setText(selectedAppointment.getCustomerId().toString());
        userIdField.setText(selectedAppointment.getUserId().toString());
    }
    public void setNewInProcess(boolean newInProcess) {
        this.newInProcess = newInProcess;
    }
}
