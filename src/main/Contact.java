package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contact {
    private  int contactId;
    private String contactName;
    private String email;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    /**
     * Generates a contact with Contact Id, Name, & Email for the Contact object.
     * @param contactId Contact ID to set.
     * @param contactName Contact Name to set.
     * @param email Contact email to set.
     */
    public Contact(int contactId, String contactName, String email){
        setContactId(contactId);
        setContactName(contactName);
        setEmail(email);
    }

    /**
     * Default constructor
     */
    public Contact() { }

    /**
     * Add an Appointment object to the Appointment List for the Contact
     * @param appointment Takes an Appointment object to add to list
     */
    public void addAppointment(Appointment appointment) { appointmentList.add(appointment); }

    /**
     * Retrieve the ObservableList of Appointments associated with the Contact object.
     * @return Returns an ObservableList of Appointments
     */
    public ObservableList<Appointment> getAppointmentList() { return appointmentList; }

    /**
     * Retrieve the Contact ID associated with the Contact object
     * @return Returns an integer of the Contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Retrieve the Contact Name associated with the Contact object
     * @return Returns a String with the Contact Name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Retrieve the email associated with the Contact object
     * @return Returns a String with the Contact's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the Contact ID with an integer value
     * @param contactId Takes an integer as input to set the Contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Set the Contact Name with a String value
     * @param contactName Takes a String as input to set the Contact Name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Set the Contact's email with a String value
     * @param email Takes a String as input to set the Contact email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
