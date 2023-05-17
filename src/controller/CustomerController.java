package controller;

import helper.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    public AnchorPane customerWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setCancelButton(true);
        ResultSet resultSet;
        customerTable = new TableView<>();
        try{
            resultSet = CustomerQuery.selectAll();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String address =resultSet.getString(3);
                String postal = resultSet.getString(4);
                String phone = resultSet.getString(5);
                Customer customer = new Customer(id, name, address, postal, phone);
                customerList.add(customer);
            }
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading customers.");
            alert.showAndWait();
        }
        customerTable.setItems(customerList);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("Region"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));
        postalColumn.setCellValueFactory(new PropertyValueFactory<>("Postal"));
        customerTable.setItems(customerList);
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
        Parent root = FXMLLoader.load(CustomerController.class.getResource("../view/customers.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Customers");
        stage.show();
    }
    public void close(){
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }
}
