package controller;

import helper.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private static String username;
    public AnchorPane loginWindow;
    public Button exitButton;
    private Alert ioError;
    private Alert loginError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setCancelButton(true);
        Locale locale = Locale.getDefault();
        ZoneId zoneId = ZoneId.systemDefault();
        locationField.setText(zoneId.toString());
        if (locale.getLanguage().equals("en")){
            ioError = new Alert(Alert.AlertType.ERROR, "Error. Login unsuccessful.");
            loginError = new Alert(Alert.AlertType.ERROR, "Matching username and password not found.");
        }
        if (locale.getLanguage().equals("fr")){
            usernameText.setText("Nom d'utilisateur");
            passwordText.setText("Mot de passe");
            loginText.setText("Connexion");
            locationText.setText("Emplacement");
            ioError = new Alert(Alert.AlertType.ERROR, "Erreur. Échec de la connexion.");
            loginError = new Alert(Alert.AlertType.ERROR, "Nom d'utilisateur et mot de passe correspondants introuvables.");
        }
    }
    public static String getUsername(){
        return username;
    }
    public void close(){
        Stage stage = (Stage) loginWindow.getScene().getWindow();
        stage.close();
    }
    public void exitClick(ActionEvent actionEvent) {
        close();
    }
    public void loginCheck() throws SQLException {
        File loginFile = new File("login_activity.txt");
        username = usernameField.getText();
        String password = passwordField.getText();
        boolean loginResults = UserQuery.checkLogin(username, password);
        try {
            FileWriter writer = new FileWriter(loginFile,true);
            String time = ZonedDateTime.now().toString();
            writer.write("login attempt to " +username + " at " + time);
            if (loginResults){
                writer.append(" successful\n");
                writer.close();
                HomeController home = new HomeController();
                home.open();
                close();
            }
            else{
                writer.append(" unsuccessful\n");
                writer.close();
                loginError.showAndWait();
            }
        }catch (IOException e) {
            ioError.showAndWait();
        }
    }
    public void onEnterClicked(ActionEvent actionEvent) throws SQLException {loginCheck();}
    public void onFieldEntered(ActionEvent actionEvent) throws SQLException {loginCheck();}
    public void open() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(LoginController.class.getResource("../view/login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();

    }

}
