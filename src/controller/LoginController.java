package controller;

import helper.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Text usernameText;
    public TextField usernameField;
    public Text passwordText;
    public PasswordField passwordField;
    public Text locationText;
    public TextField locationField;
    public Text loginText;
    private static String username;    //TODO: Pull username forward for update field
    private Locale locale;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locale = Locale.getDefault();
        ZoneId zoneId = ZoneId.systemDefault();
        locationField.setText(zoneId.toString());
        if (locale.getLanguage().equals("en")){
            System.out.println("English");
        }
        if (locale.getLanguage().equals("fr")){
            usernameText.setText("Nom d'utilisateur");
            passwordText.setText("Mot de passe");
            loginText.setText("Connexion");
            locationText.setText("Emplacement");
        }
    }
    public static String getUsername(){
        return username;
    }
    public void enterClick(ActionEvent actionEvent) throws SQLException {loginCheck();}
    public void enterPassword(ActionEvent actionEvent) throws SQLException {loginCheck();}
    public void enterUsername(ActionEvent actionEvent) throws SQLException {loginCheck();}
    //TODO: Translate to French
    public void loginCheck() throws SQLException {
        File loginFile = new File("login_activity.txt");
        username = usernameField.getText();
        String password = passwordField.getText();
        boolean loginResults = UserQuery.checkLogin(username, password);
        try {
            FileWriter writer = new FileWriter(loginFile,true);
            String time = ZonedDateTime.now().toString();
            writer.write("login to " +username + " at " + time);
            if (loginResults){
                writer.append(" successful\n");
                writer.close();
                Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Home");
                stage.show();
            }
            else{
                writer.append(" unsuccessful\n");
                writer.close();
                Alert error = new Alert(Alert.AlertType.ERROR,"Username & password not found.");
                error.showAndWait();
            }
        }catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR,"Error. Login attempt unsuccessful.");
            error.showAndWait();
        }
    }
}
