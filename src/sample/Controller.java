package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Text usernameText;
    public TextField usernameField;
    public Text passwordText;
    public PasswordField passwordField;
    public Text locationText;
    public TextField locationField;
    public Text loginText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals("en")){
            System.out.println("English");
        }
        if (locale.getLanguage().equals("fr")){
            System.out.println("french");
        }
    }

    public void enterClick(ActionEvent actionEvent) {
    }
}
