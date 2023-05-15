package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.ZoneId;
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
    private String username;    //TODO: Pull username forward for update field

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
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

    public void enterClick(ActionEvent actionEvent) {loginCheck();}
    public void enterPassword(ActionEvent actionEvent) {loginCheck();}
    public void enterUsername(ActionEvent actionEvent) {loginCheck();}
    public void loginCheck(){
        boolean loginResult;
        if(true){
            Alert error = new Alert(Alert.AlertType.ERROR,"Username & password not found.");
            error.showAndWait();
        }
        //TODO: Continually update login_activity.txt with date, time, and if successful
    }
}
