package controller;

import com.sun.scenario.effect.Offset;
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
import main.Contact;
import main.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.zone.ZoneRulesProvider;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class AppointmentController implements Initializable {
    @FXML
    public AnchorPane appointmentWindow;
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
    @FXML
    public ComboBox<String> endHourBox;
    @FXML
    public ComboBox<String> endMinuteBox;
    @FXML
    public ComboBox<String> startHourBox;
    @FXML
    public ComboBox<String> startMinuteBox;
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public DatePicker tableDatePicker;
    @FXML
    public RadioButton weeklyButton;
    @FXML
    public RadioButton monthlyButton;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
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
    public TextField customerIdField;
    @FXML
    public TextField userIdField;
    @FXML
    public TableView<Appointment> appointmentTable;
    public RadioButton allButton;
    ToggleGroup toggleGroup = new ToggleGroup();
    private Appointment selectedAppointment;
    private boolean newInProcess;
    private boolean editInProcess;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();
    private static final ObservableList<String> minutes = FXCollections.observableArrayList("00", "15", "30", "45");
    private static final ObservableList<String> hours = FXCollections.observableArrayList(
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setCancelButton(true);
        setDisableFields(true);
        allButton.setToggleGroup(toggleGroup);
        weeklyButton.setToggleGroup(toggleGroup);
        monthlyButton.setToggleGroup(toggleGroup);
        allButton.fire();
        try {
            contactList = AppointmentQuery.getContactList();
            refreshAppointmentTable();
            for (Appointment appointment : appointmentList){
                String contactName = getContactNameFromId(appointment.getContactId());
                appointment.setContactName(contactName);
            }
            //TODO: Lambda README
            contactBox.setItems(FXCollections.observableArrayList(contactList.stream()
                    .map(Contact :: getContactName)
                    .collect(Collectors.toList()
            )));
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading appointments.");
            alert.showAndWait();
        }
        endHourBox.setItems(hours);
        endMinuteBox.setItems(minutes);
        startHourBox.setItems(hours);
        startMinuteBox.setItems(minutes);
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("ContactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("StartDateTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("EndDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        appointmentTable.setPlaceholder(new Label("No appointments found for the selected dates."));
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
        appointmentIdField.clear();
        titleField.clear();
        descriptionField.clear();
        locationField.clear();
        contactBox.setValue(null);
        typeField.clear();
        startDatePicker.setValue(null );
        startHourBox.setValue(null);
        startDatePicker.setValue(null);
        endHourBox.setValue(null);
        endMinuteBox.setValue(null);
        customerIdField.setText(null);
        userIdField.setText(null);
    }
    public void close() {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }
    private void confirmSelect() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Change appointment without saving?");
    alert.showAndWait().ifPresent(response ->{
            if ( response == ButtonType.OK){ setFields(); }
        });

    }
    public Integer getContactIdFromName(String contactName){
        for (Contact contact : contactList){
            if (contact.getContactName().equals(contactName)){
                return contact.getContactId();
            }
        }
        return null;
    }
    public String getContactNameFromId(Integer contactId){
        for (Contact contact : contactList){
            if (contactId.equals(contact.getContactId())){
                return  contact.getContactName();
            }
        }
        return null;
    }
    public Appointment getAppointmentFromFields() {
        Integer appointmentId = Integer.parseInt(appointmentIdField.getText());
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contactName = contactBox.getValue();
        Integer contactId = getContactIdFromName(contactName);
        String type = typeField.getText();
        LocalDate date = startDatePicker.getValue();
        int startHour = Integer.parseInt(startHourBox.getValue());
        int startMinute = Integer.parseInt(startMinuteBox.getValue());
        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalDateTime startLocalDateTime = LocalDateTime.of(date, startTime);
        Timestamp start = Timestamp.valueOf(startLocalDateTime);
        int endHour = Integer.parseInt(endHourBox.getValue());
        int endMinute = Integer.parseInt(endMinuteBox.getValue());
        LocalTime endTime= LocalTime.of(endHour, endMinute);
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
        Timestamp end = Timestamp.valueOf(endDateTime);
        Integer customerId= Integer.parseInt(customerIdField.getText());
        Integer userId= Integer.parseInt(userIdField.getText());
        Appointment appointment = new Appointment(appointmentId, title, description, location, type,
                start, end, customerId, userId, contactId);
        appointment.setContactName(contactName);
        return appointment;
    }
    private boolean isEditInProcess() {return editInProcess;}
    private boolean isNewInProcess() {return newInProcess;}
    public void isReadyToSave(Appointment appointment){
        //  scheduling an appointment outside of business hours defined as 8:00 a.m. to 10:00 p.m. EST, including weekends
        //  scheduling overlapping appointments for customers
    }
    public void onAllClicked(ActionEvent actionEvent) throws SQLException {
        appointmentList = AppointmentQuery.getAppointmentList();
        refreshAppointmentTable();
    }
    public void onCustomerClicked(ActionEvent actionEvent) throws IOException {
        CustomerController customer = new CustomerController();
        customer.open();
        close();
    }
    public void onDeleteClicked(ActionEvent actionEvent) throws SQLException {
        String type = selectedAppointment.getType();
        if (AppointmentQuery.deleteAppointment(selectedAppointment.getAppointmentId())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, type + " Appointment deleted.");
            alert.showAndWait();
            refreshAppointmentTable();
            clearFields();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to delete appointment.");
            alert.showAndWait();
        }
    }
    public void onEditClicked(ActionEvent actionEvent) {
        setDisableFields(false);
        setEditInProcess(true);
        saveButton.setVisible(true);
        editButton.setVisible(false);
    }
    public void onFieldEntered(ActionEvent actionEvent) {saveButton.fire();}
    public void onHomeClicked(ActionEvent actionEvent) throws IOException {
        HomeController home = new HomeController();
        home.open();
        close();
    }
    public void onMonthlyClicked(ActionEvent actionEvent) throws SQLException {
        appointmentList = AppointmentQuery.getMonthlyAppointments(tableDatePicker);
        refreshAppointmentTable();
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
    public void onSaveClicked(ActionEvent actionEvent) throws SQLException {
        boolean saveSuccess = false;
        Appointment appointment = getAppointmentFromFields();
        boolean inputValid = true;
        ZoneId estId = ZoneId.of("America/New_York");
        LocalDateTime startDateTime = appointment.getStartDateTime();
        LocalDateTime endDateTime = appointment.getEndDateTime();
        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, estId);
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, estId);
        OffsetTime startOffsetTime = OffsetTime.from(startZonedDateTime);
        OffsetTime endOffsetTime = OffsetTime.from(endZonedDateTime);
        ZoneOffset estOffset = ZoneOffset.of(ZoneId.SHORT_IDS.get("EST"));
        OffsetTime minimumTime = OffsetTime.of(8, 0, 0, 0,estOffset);
        OffsetTime maximumTime = OffsetTime.of(22, 0, 0, 0, estOffset);
        Alert alert = null;
        if(startOffsetTime.isBefore(minimumTime)) inputValid = false;
        else if(startOffsetTime.isAfter(maximumTime)) inputValid = false;
        else if(endOffsetTime.isBefore(minimumTime)) inputValid = false;
        else if(endOffsetTime.isAfter(maximumTime)) inputValid = false;
        //Set alert if appointment outside of business hours
        if (!inputValid) alert = new Alert(Alert.AlertType.ERROR, "Appointment is scheduled outside of business hours (8:00 AM - 10:00 PM EST");
        if (AppointmentQuery.isConflicting(appointment) && inputValid){
            inputValid = false;
            alert = new Alert (Alert.AlertType.ERROR, "Customer has a conflicting appointment.");
        }
        if (inputValid){
            try {
                if (isNewInProcess()) {
                    saveSuccess = AppointmentQuery.insertAppointment(appointment);
                    setNewInProcess(false);
                } else if (isEditInProcess()) {
                    appointment.setAppointmentId(Integer.parseInt(appointmentIdField.getText()));
                    saveSuccess = AppointmentQuery.updateAppointment(appointment);
                    setEditInProcess(false);
                }
                refreshAppointmentTable();
            } catch (InputMismatchException e) {
                saveSuccess = false;
                alert = new Alert(Alert.AlertType.ERROR, "Confirm all fields entered properly");
            }
        }
        if (saveSuccess){
            alert = new Alert(Alert.AlertType.INFORMATION, "Appointment updated successfully.");

        }
        alert.showAndWait();
    }
    public void onTableDatePicker(ActionEvent actionEvent){
        if (weeklyButton.isSelected()) {
            weeklyButton.setSelected(false);
            weeklyButton.fire();
        }
        else if (monthlyButton.isSelected()){
            monthlyButton.setSelected(false);
            monthlyButton.fire();
        }
    }
    public void onWeeklyClicked(ActionEvent actionEvent) throws SQLException {
        appointmentList = AppointmentQuery.getWeeklyAppointments(tableDatePicker);
        refreshAppointmentTable();
    }
    public void open() throws IOException {
        Parent root = FXMLLoader.load(AppointmentController.class.getResource("../view/appointment.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Appointments");
        stage.show();
    }
    public void refreshAppointmentTable() throws SQLException {
        if (allButton.isSelected()) {
            appointmentList = AppointmentQuery.getAppointmentList();
        }
        else if (weeklyButton.isSelected()){
            appointmentList = AppointmentQuery.getWeeklyAppointments(tableDatePicker);
        }
        else if (monthlyButton.isSelected()){
            appointmentList = AppointmentQuery.getMonthlyAppointments(tableDatePicker);
        }
        appointmentTable.setItems(appointmentList);

    }
    public void setDisableFields(boolean disable){
        titleField.setDisable(disable);
        descriptionField.setDisable(disable);
        locationField.setDisable(disable);
        contactBox.setDisable(disable);
        typeField.setDisable(disable);
        startDatePicker.setDisable(disable);
        startHourBox.setDisable(disable);
        startMinuteBox.setDisable(disable);
        endHourBox.setDisable(disable);
        endMinuteBox.setDisable(disable);
        customerIdField.setDisable(disable);
        userIdField.setDisable(disable);
    }
    public void setEditInProcess(boolean editInProcess) {
        this.editInProcess = editInProcess;
    }
    public void setFields() {
        appointmentIdField.setText(selectedAppointment.getAppointmentId().toString());
        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        contactBox.setValue(selectedAppointment.getContactName());
        typeField.setText(selectedAppointment.getType());
        startDatePicker.setValue(selectedAppointment.getStartTimestamp().toLocalDateTime().toLocalDate());
        startHourBox.setValue(selectedAppointment.getStartHour());
        startMinuteBox.setValue(selectedAppointment.getStartMinute());
        endHourBox.setValue(selectedAppointment.getEndHour());
        endMinuteBox.setValue(selectedAppointment.getEndMinute());
        customerIdField.setText(selectedAppointment.getCustomerId().toString());
        userIdField.setText(selectedAppointment.getUserId().toString());
    }
    public void setNewInProcess(boolean newInProcess) {
        this.newInProcess = newInProcess;
    }
    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }
}