package main;

import helper.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private String division;
    private int divisionId;
    private String country;
    private ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
    private Appointment nextAppointment = new Appointment();
    private int totalAppointments = 0;

    /**
     * Creates a Customer object with all fields set, except for the appointmentsList and nextAppointment
     * @param id Takes an integer to set the Customer ID of the Customer object
     * @param name Takes a String to set the Customer Name of the Customer object
     * @param address Takes a String to set the Address of the Customer object
     * @param postal Takes a String to set the Postal Code of the Customer object
     * @param phone Takes a String to set the Phone Number of the Customer object
     * @param divisionId Takes an integer to set the Division ID of the Customer object
     * @throws SQLException Exception thrown if there is an error in the SQL Statement or Parameters
     */
    public Customer(int id, String name, String address, String postal, String phone, int divisionId) throws SQLException {
        setId(id);
        setName(name);
        setAddress(address);
        setPostal(postal);
        setPhone(phone);
        setDivisionId(divisionId);
        String division = CustomerQuery.getDivision(divisionId);
        String country = CustomerQuery.getCountry(divisionId);
        setCountry(country);
        setDivision(division);
    }

    /**
     * Creates a Customer object without the integer values for the Customer ID or Division ID.
     * Uses the Customer Name and Division Name to perform queries and retrieve and set respective ID integers.
     * @param name Takes a String to set the Customer Name of the Customer object
     * @param address Takes a String to set the Address of the Customer object
     * @param postal Takes a String to set the Postal Code of the Customer object
     * @param phone Takes a String to set the Phone Number of the Customer object
     * @param division Takes a String to set the Division of the Customer object
     * @throws SQLException Exception thrown if there is an error in the SQL Statement or Parameters
     */
    public Customer(String name, String address, String postal, String phone, String division) throws SQLException {
        setName(name);
        setAddress(address);
        setPostal(postal);
        setPhone(phone);
        setDivision(division);
        int divisionId = CustomerQuery.getDivisionId(division);
        setDivisionId(divisionId);
        String country = CustomerQuery.getCountry(divisionId);
        setCountry(country);
    }

    /**
     * Add an Appointment to the Appointment list associated with the Customer object.
     * Increases the totalAppointments number to the size the of the Appointment list
     * @param appointment Takes an Appointment object to add to the Customer's list of Appointments
     */
    public void addAppointment(Appointment appointment){
        appointmentsList.add(appointment);
        setTotalAppointments(appointmentsList.size());
    }

    /**
     * Retrieves the address associated with the Customer
     * @return Returns a String of the Customer's address.
     */
    public String getAddress(){return address;}

    /**
     * Retrieves the list of Appointments associated with the Customer object
      * @return Returns an ObservableList of Appointments
     */
    public ObservableList<Appointment> getAppointmentsList() { return appointmentsList; }

    /**
     * Retrieves the country of the Customer object
     * @return Returns a String of the Customer's country
     */
    public String getCountry(){return country;}

    /**
     * Retrieve the Division ID of the Customer object
     * @return Returns an integer of the Customer's Division ID
     */
    public int getDivisionId() { return divisionId; }

    /**
     * Retrieve the Division of the Customer object
     * @return Returns a String of the Customer's Division
     */
    public String getDivision(){return division;}

    /**
     * Retrieve the Customer ID of the Customer object
     * @return Returns an integer of the Customer ID
     */
    public Integer getId(){return id;}

    /**
     * Retrieve the Customer Name of the Customer object
     * @return Returns a String of the Customer Name
     */
    public String getName(){return name;}

    /**
     * Retrieve the Postal Code of the Customer object
     * @return Returns a String of the Customer's Postal code
     */
    public String getPostal(){return postal;}

    /**
     * Retrieve the Customer's next appointment.
     * @return Returns an Appointment that is the next to occur for the Customer
     */
    public Appointment getNextAppointment() { return nextAppointment; }

    /**
     * Retrieve the Customer's phone number
     * @return Returns a String with the Customer's phone number.
     */
    public String getPhone(){return phone;}

    /**
     * Retrieve the total number of Appointments associated with the Customer object.
     * @return Returns an integer with the size of the Customer's Appointment List.
     */
    public int getTotalAppointments() { return totalAppointments; }

    /**
     * Set the Customer's Address
     * @param address Takes a String as input.
     */
    public void setAddress(String address){this.address = address;}

    /**
     * Set the Customer's country.
     * @param country Takes a String as input.
     */
    public void setCountry(String country){this.country = country;}

    /**
     * Set the Customer's Division.
     * @param division Takes a String as input.
     */
    public void setDivision(String division){this.division = division;}

    /**
     * Set the Customer's Division ID
     * @param divisionId Takes an integer as input.
     */
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    /**
     * Set the Customer ID
     * @param id Takes an integer as input.
     */
    public void setId(int id){this.id = id;}

    /**
     * Set the Customer's Name.
     * @param name Takes a String as input.
     */
    public void setName(String name){this.name = name;}

    /**
     * Set the Customer's postal code
     * @param postal Takes a String as input.
     */
    public void setPostal(String postal){this.postal = postal;}

    /**
     * Set the Customer's Next Appointment
     * @param nextAppointment Takes an Appointment object as input.
     */
    public void setNextAppointment(Appointment nextAppointment) { this.nextAppointment = nextAppointment; }

    /**
     * Set the Customer's phone number.
     * @param phone Takes a String as input.
     */
    public void setPhone(String phone){this.phone = phone;}

    /**
     * Set the Customer's number of Total Appointments. Called whenever an Appointment is added.
     * @param totalAppointments Takes an integer as input.
     */
    public void setTotalAppointments(int totalAppointments) { this.totalAppointments = totalAppointments; }
}
