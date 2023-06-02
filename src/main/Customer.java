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
     *
     * @return
     */
    public String getPostal(){return postal;}

    /**
     *
     * @return
     */
    public Appointment getNextAppointment() { return nextAppointment; }

    /**
     *
     * @return
     */
    public String getPhone(){return phone;}

    /**
     *
     * @return
     */
    public int getTotalAppointments() { return totalAppointments; }

    /**
     *
     * @param address
     */
    public void setAddress(String address){this.address = address;}

    /**
     *
     * @param country
     */
    public void setCountry(String country){this.country = country;}

    /**
     *
     * @param division
     */
    public void setDivision(String division){this.division = division;}

    /**
     *
     * @param divisionId
     */
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    /**
     *
     * @param id
     */
    public void setId(int id){this.id = id;}

    /**
     *
     * @param name
     */
    public void setName(String name){this.name = name;}

    /**
     *
     * @param postal
     */
    public void setPostal(String postal){this.postal = postal;}
    public void setNextAppointment(Appointment nextAppointment) { this.nextAppointment = nextAppointment; }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone){this.phone = phone;}

    /**
     *
     * @param totalAppointments
     */
    public void setTotalAppointments(int totalAppointments) { this.totalAppointments = totalAppointments; }
}
