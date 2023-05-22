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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public TableColumn<Customer,String> phoneColumn;
    @FXML
    public TableColumn<Customer,Integer> idColumn;
    @FXML
    public TableColumn<Customer,String> nameColumn;
    @FXML
    public TableColumn<Customer,String> addressColumn;
    @FXML
    public TableColumn<Customer,String> regionColumn;
    @FXML
    public TableColumn<Customer,String> countryColumn;
    @FXML
    public TableColumn<Customer,String> postalColumn;
    @FXML
    public Button homeButton;
    @FXML
    public ComboBox<String> divisionBox;
    @FXML
    public ComboBox<String> countryBox;
    @FXML
    public TextField idField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField postalField;
    @FXML
    public TextField phoneField;
    @FXML
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    @FXML
    public AnchorPane customerWindow;
    @FXML
    public Button saveButton;
    @FXML
    public Button addButton;
    @FXML
    public Button editButton;
    public Button deleteButton;
    private boolean fieldChanged;
    private boolean newInProcess;
    private static Customer editCustomer;
    private static Customer selectedCustomer;
    private static Customer newCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setCancelButton(true);
        setNewInProcess(false);
        try{
            ResultSet resultSet = CustomerQuery.selectAll();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String address =resultSet.getString(3);
                String postal = resultSet.getString(4);
                String phone = resultSet.getString(5);
                int regionCode = resultSet.getInt("Division_ID");
                Customer customer = new Customer(id, name, address, postal, phone, regionCode);
                customerList.add(customer);
                countryBox.setItems(CustomerQuery.getCountryList());
            }
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
        //Lambda
        ListChangeListener<Customer> tableListener = change -> {
            setSelectedCustomer(customerTable.getSelectionModel().getSelectedItem());
            if(!isNewInProcess() && !isFieldChanged()){
                setFields(selectedCustomer);
                editButton.setVisible(true);
                addButton.setVisible(true);
                deleteButton.setVisible(true);
                saveButton.setVisible(false);
                disableFields();
                setFieldChanged(false);
            }
            else if(selectedCustomer != null){confirmSelect();
            }
        };
        customerTable.getSelectionModel().getSelectedItems().addListener(tableListener);
    }
    public void clearFields(){
        idField.clear();
        nameField.clear();
        addressField.clear();
        postalField.clear();
        phoneField.clear();
        countryBox.setValue("");
        divisionBox.setValue("");
    }
    public void close(){
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }
    //TODO: Method to check for unsaved changes
    public void confirmSelect(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Change customer without saving?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                setNewInProcess(false);
                setFields(selectedCustomer);
                addButton.setVisible(true);
                editButton.setVisible(true);
                saveButton.setVisible(false);
                disableFields();
                setFieldChanged(false);
            } else {
                customerTable.getSelectionModel().clearSelection();
            }
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
    public static Customer getEditCustomer(){
        return editCustomer;
    }
    public Customer getCustomerFields() throws SQLException {
        String name = nameField.getText();
        String address = addressField.getText();
        String postal = postalField.getText();
        String phone = phoneField.getText();
        String division = divisionBox.getValue();
        Customer customer = new Customer(name, address, postal, phone, division);
        return customer;
    }
    public static Customer getSelectedCustomer(){
        return selectedCustomer;
    }
    public boolean isFieldChanged(){
        return fieldChanged;
    }
    public boolean isNewInProcess() {
        return newInProcess;
    }
    public void onAppointmentClicked(ActionEvent actionEvent) throws IOException {
        AppointmentController appointment = new AppointmentController();
        appointment.open();
        close();
    }
    public void onComboBoxChanged(ContextMenuEvent keyEvent) {
        setFieldChanged(true);
    }
    public void onCountrySelected(ActionEvent actionEvent) throws SQLException {
        String country = countryBox.getValue();
        ObservableList<String> divisionList = CustomerQuery.getDivisionList(country);
        divisionBox.setItems(divisionList);
    }
    public void onDeleteClicked(ActionEvent actionEvent) throws SQLException {
        if (selectedCustomer.isSafeToDelete()){
            int deleteId = selectedCustomer.getId();
            int deleted = CustomerQuery.deleteCustomer(deleteId);
            if (deleted < 1){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting customer in database.");
                alert.showAndWait();
            }
            else{
                //TODO: Lambda for README
                customerList.removeIf(customer -> customer.getId() == deleteId);
                customerTable.setItems(customerList);
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
            setFieldChanged(false);
            setNewInProcess(false);
        }
    }
    public void onFieldChanged(KeyEvent keyEvent){
        setFieldChanged(true);
    }
    public void onFieldEntered(ActionEvent actionEvent) throws SQLException {
        saveCustomer();
    }
    public void onHomeClicked(ActionEvent actionEvent) throws IOException{
        HomeController home = new HomeController();
        home.open();
        close();
    }
    public void onNewClicked(ActionEvent actionEvent) {
        if (isFieldChanged()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Add new customer without saving?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    startNewCustomer();
                }
            });
        }
        else{
            startNewCustomer();
        }
    }
    public void open() throws IOException{
        Parent root = FXMLLoader.load(CustomerController.class.getResource("../view/customer.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customers");
        stage.show();
    }
    public void onSaveClicked(ActionEvent actionEvent) throws SQLException {
        saveCustomer();
    }
    private void saveCustomer() throws SQLException {
        //TODO: Error Checking
        boolean saveSuccess = false;
        if (isNewInProcess()){
            newCustomer = getCustomerFields();
            CustomerQuery.insertCustomer(newCustomer);
            customerList.add(newCustomer);
            customerTable.setItems(customerList);
            saveSuccess = true;
        }
        else if (isFieldChanged()){
            editCustomer = getCustomerFields();
            CustomerQuery.
        }
        if (saveSuccess) {
            disableFields();
            saveButton.setVisible(false);
            editButton.setVisible(false);
            deleteButton.setVisible(false);
            setNewInProcess(false);
            setFieldChanged(false);
        }
    }
    public void setFieldChanged(boolean fieldChanged) {
        this.fieldChanged = fieldChanged;
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
        }
    }
    public void setNewInProcess(boolean newInProcess) {
        this.newInProcess = newInProcess;
    }
    public void startNewCustomer(){
        customerTable.getSelectionModel().clearSelection();
        setFieldChanged(false);
        clearFields();
        enableFields();
        setNewInProcess(true);
        addButton.setVisible(false);
        saveButton.setVisible(true);
        deleteButton.setVisible(false);
        editButton.setVisible(false);
        nameField.requestFocus();
    }
}