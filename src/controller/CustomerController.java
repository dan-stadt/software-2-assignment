package controller;

import helper.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import main.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    public TableColumn phoneColumn;
    @FXML
    public TableColumn idColumn;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn addressColumn;
    @FXML
    public TableColumn regionColumn;
    @FXML
    public TableColumn countryColumn;
    @FXML
    public TableColumn postalColumn;
    public Button backBtn;
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backBtn.setCancelButton(true);
        ResultSet resultSet;
        try{
            resultSet = CustomerQuery.selectAll();
            while (resultSet.next()) {
                int id = resultSet.getInt(0);
                String name = resultSet.getString(1);
                String address =resultSet.getString(2);
                String postal = resultSet.getString(3);
                String phone = resultSet.getString(4);
                Customer customer = new Customer(id, name, address, postal, phone);
                customerList.add(customer);
            }
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading customers.");
            alert.showAndWait();
        }
    }
    public void appointmentBtnPress(ActionEvent actionEvent) throws IOException {
        HomeController homeController = new HomeController();
        homeController.appointmentButton(actionEvent);
        backBtnPress(actionEvent);
    }
    public void backBtnPress(ActionEvent actionEvent) {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }


}
