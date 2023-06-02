package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contact {
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
    public Contact() { }

    private  int contactId;
    private String contactName;
    private String email;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public void addAppointment(Appointment appointment) { appointmentList.add(appointment); }
    public ObservableList<Appointment> getAppointmentList() { return appointmentList; }
    public int getContactId() {
        return contactId;
    }
    public String getContactName() {
        return contactName;
    }
    public String getEmail() {
        return email;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
