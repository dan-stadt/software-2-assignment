package controller;

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
import main.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    //#region FXML Declarations
    @FXML
    public TableColumn<Customer,String> addressColumn;
    @FXML
    public TextField addressField;
    @FXML
    public ComboBox<String> countryBox;
    @FXML
    public TableColumn<Customer,String> countryColumn;
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public ComboBox<String> divisionBox;
    @FXML
    public Button homeButton;
    @FXML
    public TableColumn<Customer,Integer> idColumn;
    @FXML
    public TextField idField;
    @FXML
    public TableColumn<Customer,String> nameColumn;
    @FXML
    public TextField nameField;
    @FXML
    public TableColumn<Customer,String> phoneColumn;
    @FXML
    public TextField phoneField;
    @FXML
    public TableColumn<Customer,String> postalColumn;
    @FXML
    public TextField postalField;
    @FXML
    public TableColumn<Customer,String> regionColumn;
    @FXML
    public Button saveButton;
    @FXML
    public AnchorPane customerWindow;
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button editButton;
    //#endregion
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private boolean editInProcess;
    private boolean newInProcess;
    private static Customer editCustomer;
    private static Customer selectedCustomer;

    /**
     * Opens the Customer FXML Interface and configures Customer table.
     * Fields are disabled at startup until a new customer is added or an exisitng customer is edited.
     * @param url The location of the Customer FXML file.
     * @param resourceBundle The Customer FXML file object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setCancelButton(true);
        setNewInProcess(false);
        try{
            customerList = CustomerQuery.getCustomerList();
            countryBox.setItems(CustomerQuery.getCountryList());
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading customers.");
            alert.showAndWait();
        }
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("Division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));
        postalColumn.setCellValueFactory(new PropertyValueFactory<>("Postal"));
        customerTable.setItems(customerList);
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //TODO: Lambda README
        ListChangeListener<Customer> tableListener = change -> {
            setSelectedCustomer(customerTable.getSelectionModel().getSelectedItem());
            if(!isNewInProcess() && !isEditInProcess()){
                setFields(selectedCustomer);
            }
            else confirmSelect();
        };
        customerTable.getSelectionModel().getSelectedItems().addListener(tableListener);
    }

    /**
     * Erases the value in each field. Called after new clicked, or a successful save or deletion.
     */
    public void clearFields(){
        idField.clear();
        nameField.clear();
        addressField.clear();
        postalField.clear();
        phoneField.clear();
        countryBox.setValue(null);
        divisionBox.setValue(null);
    }

    /**
     * Closes the Customer window. Called after switching to the Home or Window appointments.
     */
    public void close(){
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }
    public void confirmSelect(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Change customer without saving?");
        //TODO: Lambda README
        alert.showAndWait().ifPresent(response ->{
            if (response == ButtonType.OK) setFields(selectedCustomer);
        });
    }

    /**
     * Disables all fields, except the ID field, which is disable by default and not enabled.
     */
    public void disableFields(){
        nameField.setDisable(true);
        addressField.setDisable(true);
        postalField.setDisable(true);
        phoneField.setDisable(true);
        countryBox.setDisable(true);
        divisionBox.setDisable(true);
    }

    /**
     * Enables all fields, except the ID field, which should not be enabled per specifications.
     */
    public void enableFields(){
        nameField.setDisable(false);
        addressField.setDisable(false);
        postalField.setDisable(false);
        phoneField.setDisable(false);
        countryBox.setDisable(false);
        divisionBox.setDisable(false);
    }

    /**
     * Pulls text entered into each input control in the form and returns a Customer object with that information.
     * @return Returns a Customer based on the values entered into the fields.
     * @throws SQLException Exception thrown if there is an error with the SQL Statement or parameters.
     */
    public Customer getCustomerFields() throws SQLException {
        String name = nameField.getText();
        String address = addressField.getText();
        String postal = postalField.getText();
        String phone = phoneField.getText();
        String division = divisionBox.getValue();
        Customer customer = new Customer(name, address, postal, phone, division);
        if (!isNewInProcess()) {
            int id = Integer.parseInt(idField.getText());
            customer.setId(id);
        }
        return customer;
    }

    /**
     * Checks if each field has data entered.
     * @return Returns false is any field is blank, otherwise true.
     * @throws SQLException Exception thrown if errors in SQL Statement or parameters.
     */
    public boolean isFormComplete() throws SQLException {
        Customer customer = getCustomerFields();
        if (customer.getAddress().isBlank()) return false;
        else if (customer.getName().isBlank()) return false;
        else if (customer.getPhone().isBlank()) return false;
        else if (customer.getPostal().isBlank()) return false;
        else return !customer.getDivision().isBlank();
    }

    /**
     * Checks if a Customer record is currently being edited.
     * @return Returns true if edit is in process; if not, returns false.
     */
    public boolean isEditInProcess(){
        return editInProcess;
    }
    /**
     * Checks if a new Customer record is currently being entered.
     * @return Returns true if a new Customer record is being entered; if not, returns false.
     */
    public boolean isNewInProcess() {
        return newInProcess;
    }

    /**
     * Method called when the Appointment button is clicked. Opens the Appointment window and closes the Customer window.
     * @param actionEvent Button clicked
     * @throws IOException Exception if unable to locate or open Appointment file.
     */
    public void onAppointmentClicked(ActionEvent actionEvent) throws IOException {
        AppointmentController appointment = new AppointmentController();
        appointment.open();
        close();
    }

    /**
     * Called when the Country dropdown menu is selected. Pulls all regions associated with that country.
     * @param actionEvent Dropdown menu selected with a country.
     * @throws SQLException Exception if there is an error in the SQL Statement or Parameters.
     */
    public void onCountrySelected(ActionEvent actionEvent) throws SQLException {
        String country = countryBox.getValue();
        ObservableList<String> divisionList = CustomerQuery.getDivisionList(country);
        divisionBox.setItems(divisionList);
    }
    public void onDeleteClicked(ActionEvent actionEvent) throws SQLException {
        if (CustomerQuery.isSafeToDelete(selectedCustomer)){
            int deleteId = selectedCustomer.getId();
            boolean deleted = CustomerQuery.deleteCustomer(deleteId);
            if (!deleted){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting customer in database.");
                alert.showAndWait();
            }
            else{
                customerTable.setItems(CustomerQuery.getCustomerList());
                customerTable.getSelectionModel().clearSelection();
                clearFields();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer successfully deleted.");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot delete customer. Existing appointments found.");
            alert.showAndWait();
        }
    }

    /**
     * When edit button is clicked, all fields are enabled. Edit button is only visible when a Customer is selected.
     * Fields will have already populated upon customer selection. Save button becomes visible. Edit & Delete buttons
     * are hidden.
     * @param actionEvent Edit button clicked.
     */
    public void onEditClicked(ActionEvent actionEvent) {
        if (selectedCustomer != null) {
            setEditCustomer(selectedCustomer);
            editButton.setVisible(false);
            saveButton.setVisible(true);
            deleteButton.setVisible(false);
            enableFields();
            setEditInProcess(true);
            setNewInProcess(false);
        }
    }

    /**
     * When the enter key is pressed while the focus is in a TextField, calls the save button.
     * @param actionEvent Enter key pressed
     */
    public void onFieldEntered(ActionEvent actionEvent){
        saveButton.fire();
    }

    /**
     * When the Home Button is pressed, opens the Home window and closes the current Customer window.
     * @param actionEvent Home Button pressed
     * @throws IOException If there is an error opening the Home FXML File.
     */
    public void onHomeClicked(ActionEvent actionEvent) throws IOException{
        HomeController home = new HomeController();
        home.open();
        close();
    }

    /**
     * Clears and enables editable fields and sets newInProcess to true.
     * @param actionEvent New Button clicked.
     */
    public void onNewClicked(ActionEvent actionEvent) {
        if (isEditInProcess()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Add new customer without saving?");
            alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> startNewCustomer());
        }
        else{
            startNewCustomer();
        }
    }

    /**
     * When the Save Button is clicked, performs input validation. If fields pass input validation tests, a new Customer
     * is added to the SQL Database. Input validation checks that all fields are entered.
     * @param actionEvent Save Button is clicked.
     * @throws SQLException Exception thrown is error in SQL statement or parameters.
     */
    public void onSaveClicked(ActionEvent actionEvent) throws SQLException {
        boolean saveSuccess = false;
        editCustomer = getCustomerFields();
        if(!isFormComplete()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot save file unless all fields are completed.");
            alert.showAndWait();
        }
        else if (isNewInProcess()) saveSuccess = CustomerQuery.insertCustomer(editCustomer);
        else if (isEditInProcess()) saveSuccess = CustomerQuery.updateCustomer(editCustomer);
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Error. Unable to save customer.");
            alert.showAndWait();
        }
        if (saveSuccess) {
            setEditInProcess(false);
            setNewInProcess(false);
            customerList = CustomerQuery.getCustomerList();
            customerTable.setItems(customerList);
            customerTable.getSelectionModel().clearSelection();
            clearFields();
            disableFields();
            saveButton.setVisible(false);
            editButton.setVisible(false);
            deleteButton.setVisible(false);
            addButton.setVisible(true);
        }
    }

    /**
     * Opens a new Customer window. Called by other controllers.
     * @throws IOException Exception thrown if the FXML file cannot be loaded or found.
     */
    public void open() throws IOException{
        Parent root = FXMLLoader.load(CustomerController.class.getResource("../view/customer.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customers");
        stage.show();
    }

    /**
     * Sets the editInProcess field to true or false.
     * @param editInProcess Boolean value to set editInProcess
     */
    public void setEditInProcess(boolean editInProcess) {
        this.editInProcess = editInProcess;
    }

    /**
     * Sets a Customer object to the editCustomer. Used prior to saving a new or edited Customer
     * @param customer Customer object to be assigned to editCustomer.
     */
    public static void setEditCustomer(Customer customer){
        editCustomer = customer;
    }

    /**
     * Set a Customer object to the selectedCustomer. Used upon selecting a Customer in the table.
     * @param customer Customer object to be assigned to selectedCustomer.
     */
    public static void setSelectedCustomer(Customer customer){
        selectedCustomer = customer;
    }

    /**
     * Populates fields with information from a Customer object.
     * @param customer Customer object to set fields.
     */
    public void setFields(Customer customer){
        if(customer != null){
            idField.setText(customer.getId().toString());
            nameField.setText(customer.getName());
            addressField.setText(customer.getAddress());
            postalField.setText(customer.getPostal());
            phoneField.setText(customer.getPhone());
            countryBox.setValue(customer.getCountry());
            divisionBox.setValue(customer.getDivision());
            setNewInProcess(false);
            setEditInProcess(false);
            addButton.setVisible(true);
            deleteButton.setVisible(true);
            editButton.setVisible(true);
            saveButton.setVisible(false);
            disableFields();
        }
    }

    /**
     * Sets whether a new customer is being entered.
     * @param newInProcess true if new has been clicked and customer not saved, otherwise false.
     */
    public void setNewInProcess(boolean newInProcess) {
        this.newInProcess = newInProcess;
    }

    /**
     * Starts a new  Customer entry by enabling and clearing fields. Save is the only visible Button.
     * New Customer is not added to the database until Save is clicked and fields pass input validation.
     */
    public void startNewCustomer(){
        customerTable.getSelectionModel().clearSelection();
        clearFields();
        enableFields();
        setNewInProcess(true);
        addButton.setVisible(false);
        saveButton.setVisible(true);
        deleteButton.setVisible(false);
        editButton.setVisible(false);
        nameField.requestFocus();
        setEditInProcess(false);
    }
}