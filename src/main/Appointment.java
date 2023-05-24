package main;

import helper.AppointmentQuery;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Appointment {
    private Integer appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Integer customerId;
    private Integer userId;
    private Integer contactId;

    public Appointment(){}
    public Appointment(Integer appointmentId, String title, String description, String location,
                       String type, Timestamp start, Timestamp end, Integer customerId,
                       Integer userId, Integer contactId) throws SQLException {
        setAppointmentId(appointmentId);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setStart(start);
        setEnd(end);
        setCustomerId(customerId);
        setUserId(userId);
        setContactId(contactId);
    }
    public Integer getAppointmentId() {
        return appointmentId;
    }
    public Integer getCustomerId() {
        return customerId;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getContact() {
        return contact;
    }
    public Integer getContactId(){return contactId;}
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public Timestamp getStart() {
        return start;
    }
    public String getTitle() {
        return title;
    }
    public String getType() {
        return type;
    }
    public Timestamp getEnd() {
        return end;
    }
    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setStart(Timestamp start) {
        this.start = start;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
