package main;

import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * Starts the program from the Login window.
     * @param primaryStage Takes the Primary Stage for the Login window
     * @throws Exception Exception thrown if error starting program
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        LoginController login = new LoginController();
        login.open();
    }

    /**
     * Main method to start program by running start method and opening Login window.
     * @param args Takes the command line value as a String
     */
    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
