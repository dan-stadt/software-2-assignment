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
    public ComboBox<String> regionBox;
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
    public Button saveButton;
    public Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setCancelButton(true);
        ResultSet resultSet;
        try{
            resultSet = CustomerQuery.selectAll();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String address =resultSet.getString(3);
                String postal = resultSet.getString(4);
                String phone = resultSet.getString(5);
                int regionCode = resultSet.getInt("Division_ID");
                String region = CustomerQuery.getRegion(regionCode);
                String country = CustomerQuery.getCountry(regionCode);
                Customer customer = new Customer(id, name, country, region, address, postal, phone);
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
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("Region"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));
        postalColumn.setCellValueFactory(new PropertyValueFactory<>("Postal"));
        customerTable.setItems(customerList);
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //Lambda
        ListChangeListener<Customer> tableListener = change -> {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            idField.setText(customer.getId().toString());
            nameField.setText(customer.getName());
            addressField.setText(customer.getAddress());
            postalField.setText(customer.getPostal());
            phoneField.setText(customer.getPhone());
            countryBox.setValue(customer.getCountry());
            regionBox.setValue(customer.getRegion());
            saveButton.setVisible(true);
        };
        customerTable.getSelectionModel().getSelectedItems().addListener(tableListener);
    }
    public void appointmentClicked(ActionEvent actionEvent) throws IOException {
        AppointmentController appointment = new AppointmentController();
        appointment.open();
        close();
    }
    public void homeClicked(ActionEvent actionEvent) throws IOException{
        HomeController home = new HomeController();
        home.open();
        close();
    }
    public void open() throws IOException{
        Parent root = FXMLLoader.load(CustomerController.class.getResource("../view/customer.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customers");
        stage.show();
    }
    public void close(){
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }

    public void updateClicked(ActionEvent actionEvent) {
    }

    public void countryChange(ActionEvent actionEvent) throws SQLException {
        String country = countryBox.getValue();

        regionBox.setItems(CustomerQuery.getDivisionList(country));
    }

    public void addClicked(ActionEvent actionEvent) {
        checkChanges();
        customerTable.getSelectionModel().clearSelection();
        idField.clear();
        nameField.clear();
        addressField.clear();
        postalField.clear();
        phoneField.clear();
        countryBox.setValue("");
        regionBox.setValue("");
        saveButton.setVisible(true);    }
    //TODO: Method to check for unsaved changes
    public void checkChanges(){
        
    }
}
