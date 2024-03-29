GUI-Based Scheduling Application: A Graphical User Interface Application to maintain company Customers and Appointments.
    Allows users to insert, update, and delete appointments and Customers.
    Displays Tables with customer and appointment information.
    Provides updates when an appointment occurs within 15 minutes of login.
Author: Daniel Stadt
Contact: dstadt@wgu.edu
Version: 1.1
IntelliJ IDEA 2021.1.1.3 (Community Edition) Build #IC-211.7628.21, built on June 30, 2021
Java SE 17.0.1
JavaFX-SDK-17.0.1
To Run: Open File/Project Structure... on the top menu of IntelliJ IDEA
        Navigate to Project on the left pane and set the Project SDK to java-17.0.1
        Navigate to Modules on the left pane and the Sources tab on the right pane
        Set the src folder as a Source Folder
        Navigate to libraries and add my-sql-connector-java-8.0.25 and javafx-sdk-17.0.1 as libraries
        Close the Project Structure window and open File/Settings...
        Under Appearance & Behavior on the left pane, select Path Variables.
        Set PATH_TO_FX to the path location of the lib folder for javafx-sdk-17.0.1 on your machine.
        Close the Settings window
        Open the Edit Configurations... via the icon on the upper right side of the IntelliJ dashboard.
        Click Modify Options, and ensure that Add VM Options is selected.
        In the VM Options field, enter the following, without quotations:
            "--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics"
        Compile and run the program via the 'play' icon in the top right that will Run Main.
Report: The third report provides the next upcoming appointment and total number of appointments for each Customer
        If the Customer does not have any appointments, the number of appointments is shown and no additional information is provided.
        Appointments that have passed are included in the total, but will not be shown as the next appointment.
MySQL Connector: mysql-connector-java-8.0.25
