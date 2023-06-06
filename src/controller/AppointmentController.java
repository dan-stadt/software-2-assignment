package controller;

import main.Appointment;
import main.Contact;
import helper.AppointmentQuery;
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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
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

    /**
     * Initializes Appointment window.
     * Lambda 1: The ComboBox list is populated with a lambda that streams the contacts into a List.
     * Lambda 2: Creates a Listener for the table to update the selectedCustomer and fields based on the table selection.
     * @param url Pathway of FXML file
     * @param resourceBundle FXML file object.
     */
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
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        appointmentTable.setPlaceholder(new Label("No appointments found for the selected dates."));
        appointmentTable.setItems(appointmentList);
        appointmentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setEditInProcess(false);
        setNewInProcess(false);
        ListChangeListener<Appointment> tableListener = change -> {
            if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
                setSelectedAppointment(appointmentTable.getSelectionModel().getSelectedItem());
                if (!isNewInProcess() && !isEditInProcess()) {
                    setFields();
                    editButton.setVisible(true);
                    deleteButton.setVisible(true);
                    newButton.setVisible(true);
                } else confirmSelect();
            }
        };
        appointmentTable.getSelectionModel().getSelectedItems().addListener(tableListener);
    }

    /**
     * Clears all TextFields and ComboBoxes.
     */
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

    /**
     * Closes the Appointment window.
     */
    public void close() {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Confirms that user wants to select a new user if a new Appointment is being entered or an edit is in process.
     */
    private void confirmSelect() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Change appointment without saving?");
    alert.showAndWait().ifPresent(response ->{
            if ( response == ButtonType.OK){ setFields(); }
        });

    }

    /**
     * Uses a Contact Name to retrieve the associated Contact ID.
     * @param contactName Takes the Contact Name to search.
     * @return Returns the Contact ID for the associated Contact Name.
     */
    public Integer getContactIdFromName(String contactName){
        for (Contact contact : contactList){
            if (contact.getContactName().equals(contactName)){
                return contact.getContactId();
            }
        }
        return null;
    }

    /**
     * Uses a Contact ID to retrieve the associated Contact Name
     * @param contactId Takes the Contact ID to search
     * @return Returns the Contact Name associated with the Contact ID.
     */
    public String getContactNameFromId(Integer contactId){
        for (Contact contact : contactList){
            if (contactId.equals(contact.getContactId())){
                return  contact.getContactName();
            }
        }
        return null;
    }

    /**
     * Pulls all information from each field to create a new Appointment object.
     * @return Returns an Appointment object populated with the current fields.
     */
    public Appointment getAppointmentFromFields() throws SQLException {
        int appointmentId = 0;
        if (isEditInProcess()) appointmentId = Integer.parseInt(appointmentIdField.getText());
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

    /**
     * Checks whether an Appointment is currently being edited.
     * @return Returns true if an Appointment is being edited; returns false if an Appointment is not being edited.
     */
    private boolean isEditInProcess() {return editInProcess;}

    /**
     * Checks whether a new Appointment is currently being entered.
     * @return Returns true if a new Appointment is being entered and has not been saved; otherwise returns false.
     */
    private boolean isNewInProcess() {return newInProcess;}

    /**
     * Shows all appointments when All RadioButton is selected.
     * @param actionEvent RadioButton is selected.
     * @throws SQLException Exception thrown if error in SQL statement or parameter.
     */
    public void onAllClicked(ActionEvent actionEvent) throws SQLException {
        appointmentList = AppointmentQuery.getAppointmentList();
        refreshAppointmentTable();
    }

    /**
     * Opens the Customer window and closes the Appointment window.
     * @param actionEvent Button is clicked.
     * @throws IOException Exception thrown if error opening Customer FXML file.
     */
    public void onCustomerClicked(ActionEvent actionEvent) throws IOException {
        CustomerController customer = new CustomerController();
        customer.open();
        close();
    }

    /**
     * Deletes the selectedAppointment.
     * @param actionEvent Button is clicked.
     * @throws SQLException Exception thrown is error in SQL statement or parameter.
     */
    public void onDeleteClicked(ActionEvent actionEvent) throws SQLException {
        String type = selectedAppointment.getType();
        if (AppointmentQuery.deleteAppointment(selectedAppointment.getAppointmentId())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, type + " Appointment deleted.");
            alert.showAndWait();
            refreshAppointmentTable();
            clearFields();
            newButton.setVisible(true);
            editButton.setVisible(true);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to delete appointment.");
            alert.showAndWait();
        }
    }

    /**
     * Starts editing the selectedAppointment.
     * @param actionEvent Button is clicked.
     */
    public void onEditClicked(ActionEvent actionEvent) {
        setDisableFields(false);
        setEditInProcess(true);
        saveButton.setVisible(true);
        editButton.setVisible(false);
    }

    /**
     * Fires the save button if the Enter key is pressed while the focus is in a TextField and an Appointment is being edited.
     * @param actionEvent Enter key is pressed.
     */
    public void onFieldEntered(ActionEvent actionEvent) {saveButton.fire();}

    /**
     * When the Home Button is clicked, open the Home screen and close the Appointment screen.
     * @param actionEvent Button is clicked.
     * @throws IOException Exception thrown if error opening FXML file.
     */
    public void onHomeClicked(ActionEvent actionEvent) throws IOException {
        HomeController home = new HomeController();
        home.open();
        close();
    }

    /**
     * When the monthly Button is clicked, shows all of the Appointments in the month chosen in the DatePicker.
     * If no month is selected, displays the current month.
     * @param actionEvent Button is clicked.
     * @throws SQLException Exception thrown if error with SQL statement or parameter.
     */
    public void onMonthlyClicked(ActionEvent actionEvent) throws SQLException {
        appointmentList = AppointmentQuery.getMonthlyAppointments(tableDatePicker);
        refreshAppointmentTable();
    }

    /**
     * Clears and enables all fields. If a Appointment is being edited, it will confirm that user wants to change.
     * @param actionEvent Button is clicked.
     */
    public void onNewClicked(ActionEvent actionEvent) {
        clearFields();
        deleteButton.setVisible(false);
        editButton.setVisible(false);
        newButton.setVisible(false);
        saveButton.setVisible(true);
        setDisableFields(false);
        setNewInProcess(true);
    }

    /**
     * Performs input validation to confirm that all fields are entered properly. If the fields pass input validation,
     * a new Appointment will be entered into the database.
     * @param actionEvent Button is clicked.
     */
    public void onSaveClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment updated successfully.");
        try {
            Appointment appointment = getAppointmentFromFields();
            boolean inputValid = true;
            ZoneId estId = ZoneId.of("America/New_York");
            Timestamp startTimestamp = appointment.getStartTimestamp();
            Timestamp endTimestamp = appointment.getEndTimestamp();
            Instant startInstant = startTimestamp.toInstant();
            Instant endInstant = endTimestamp.toInstant();
            OffsetTime startOffsetTime = OffsetTime.ofInstant(startInstant, estId);
            OffsetTime endOffsetTime = OffsetTime.ofInstant(endInstant, estId);
            ZoneOffset estOffset = ZoneOffset.of(ZoneId.SHORT_IDS.get("EST"));
            OffsetTime minimumTime = OffsetTime.of(8, 0, 0, 0, estOffset);
            OffsetTime maximumTime = OffsetTime.of(22, 0, 0, 0, estOffset);
            if (startOffsetTime.isBefore(minimumTime)) inputValid = false;
            else if (startOffsetTime.isAfter(maximumTime)) inputValid = false;
            else if (endOffsetTime.isBefore(minimumTime)) inputValid = false;
            else if (endOffsetTime.isAfter(maximumTime)) inputValid = false;
            //Set alert if appointment outside of business hours
            if (!inputValid)
                alert = new Alert(Alert.AlertType.ERROR, "Appointment is scheduled outside of business hours (8:00 AM - 10:00 PM EST");
            else {   //Proceed to other input validation if time is within business hours.
                if (AppointmentQuery.isConflicting(appointment)) {
                    inputValid = false;
                    alert = new Alert(Alert.AlertType.ERROR, "Customer has a conflicting appointment.");
                } else if (startInstant.isAfter(endInstant)) {
                    inputValid = false;
                    alert = new Alert(Alert.AlertType.ERROR, "Start time is before end time.");
                }
                else if (startInstant.isBefore(Instant.now())){
                    inputValid = false;
                    alert = new Alert(Alert.AlertType.ERROR, "Appointment time has already passed.");
                }
            }
            if (inputValid) {
                if (isNewInProcess()) {
                    AppointmentQuery.insertAppointment(appointment);
                    setNewInProcess(false);
                } else if (isEditInProcess()) {
                    appointment.setAppointmentId(Integer.parseInt(appointmentIdField.getText()));
                    AppointmentQuery.updateAppointment(appointment);
                    setEditInProcess(false);
                }
                refreshAppointmentTable();
                newButton.setVisible(true);
                editButton.setVisible(false);
                saveButton.setVisible(false);
            }
        } catch(Exception e){
            alert = new Alert(Alert.AlertType.ERROR, "Confirm all fields entered properly");
        }
        alert.showAndWait();
    }

    /**
     * When the tableDatePicker is adjusted, it changes the Appointments displayed.
     * Will show Appointments in the selected month or week if either selected. Otherwise displays all Appointments.
     * @param actionEvent Date selected.
     */
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

    /**
     * When the weekly Button is clicked, shows all of the Appointments in the week selected in the DatePicker.
     * If no week is selected, displays the current week.
     * @param actionEvent Button is clicked.
     * @throws SQLException Exception thrown if error with SQL statement or parameter.
     */
    public void onWeeklyClicked(ActionEvent actionEvent) throws SQLException {
        appointmentList = AppointmentQuery.getWeeklyAppointments(tableDatePicker);
        refreshAppointmentTable();
    }

    /**
     * Opens the Appointment window.
     * @throws IOException Exception thrown if error opening FXML file.
     */
    public void open() throws IOException {
        Parent root = FXMLLoader.load(AppointmentController.class.getResource("../view/appointment.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Appointments");
        stage.show();
    }

    /**
     * Updates the Appointment Table based on the DatePicker and whether All, Monthly, or Weekly is selected.
     * @throws SQLException Exception thrown if error with SQL statement or parameter.
     */
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

    /**
     * Sets all fields, except for Appointment ID to either disabled or enabled
     * @param disable true disables all fields, false enables all fields.
     */
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

    /**
     * Sets whether an Appointment is currently being edited.
     * @param editInProcess true if an Appointment is edited; false if an Appointment is no longer being edited.
     */
    public void setEditInProcess(boolean editInProcess) {
        this.editInProcess = editInProcess;
    }

    /**
     * Populates fields with the values from the selected Appointment.
     */
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

    /**
     * Sets whether a new Appointment is currently being entered.
     * @param newInProcess true if a new Appointment is being entered; false if a new Appointment is not being entered.
     */
    public void setNewInProcess(boolean newInProcess) {
        this.newInProcess = newInProcess;
    }

    /**
     * Sets the selected Appointment with its values.
     * @param selectedAppointment the Appointment ot be set.
     */
    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }
}