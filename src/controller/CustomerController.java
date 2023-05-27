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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setCancelButton(true);
        setNewInProcess(false);
        try{
            customerList = CustomerQuery.getCustomerList();
            countryBox.setItems(CustomerQuery.getCountryList());
            //TODO: Populate appointmentList for each customer
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
    public void clearFields(){
        idField.clear();
        nameField.clear();
        addressField.clear();
        postalField.clear();
        phoneField.clear();
        countryBox.setValue(null);
        divisionBox.setValue(null);
    }
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
    public void disableFields(){
        nameField.setDisable(true);
        addressField.setDisable(true);
        postalField.setDisable(true);
        phoneField.setDisable(true);
        countryBox.setDisable(true);
        divisionBox.setDisable(true);
    }
    public void enableFields(){
        nameField.setDisable(false);
        addressField.setDisable(false);
        postalField.setDisable(false);
        phoneField.setDisable(false);
        countryBox.setDisable(false);
        divisionBox.setDisable(false);
    }
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
    public boolean isFormComplete() throws SQLException {
        Customer customer = getCustomerFields();
        if (customer.getAddress().isBlank()) return false;
        else if (customer.getName().isBlank()) return false;
        else if (customer.getPhone().isBlank()) return false;
        else if (customer.getPostal().isBlank()) return false;
        else return !customer.getDivision().isBlank();
    }
    public boolean isEditInProcess(){
        return editInProcess;
    }
    public boolean isNewInProcess() {
        return newInProcess;
    }
    public void onAppointmentClicked(ActionEvent actionEvent) throws IOException {
        AppointmentController appointment = new AppointmentController();
        appointment.open();
        close();
    }
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
    public void onFieldEntered(ActionEvent actionEvent) throws SQLException {
        saveButton.fire();
    }
    public void onHomeClicked(ActionEvent actionEvent) throws IOException{
        HomeController home = new HomeController();
        home.open();
        close();
    }
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
    public void open() throws IOException{
        Parent root = FXMLLoader.load(CustomerController.class.getResource("../view/customer.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customers");
        stage.show();
    }
    public void setEditInProcess(boolean fieldChanged) {
        this.editInProcess = fieldChanged;
    }
    public static void setEditCustomer(Customer customer){
        editCustomer = customer;
    }
    public static void setSelectedCustomer(Customer customer){
        selectedCustomer = customer;
    }
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
    public void setNewInProcess(boolean newInProcess) {
        this.newInProcess = newInProcess;
    }
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