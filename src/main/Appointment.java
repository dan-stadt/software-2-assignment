package main;

import com.sun.source.tree.UsesTree;

import java.sql.Time;

public class Appointment {
    private Integer appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private Time startDateTime;
    private Time endDateTime;
    private Integer customerId;
    private  Integer userId;

    public Appointment(){}
    public Appointment(Integer appointmentId, String title, String description, String location, String contact,
                       String type, Time startDateTime, Time endDateTime, Integer customerId, Integer userId){
        setAppointmentId(appointmentId);
        setType(title);
        setDescription(description);
        setContact(contact);
        setLocation(location);
        setType(type);
        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
        setCustomerId(customerId);
        setUserId(userId);
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
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public String getTitle() {
        return title;
    }
    public String getType() {
        return type;
    }
    public Time getEndDateTime() {
        return endDateTime;
    }
    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setEndDateTime(Time endDateTime) {
        this.endDateTime = endDateTime;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setStartDateTime(Time startDateTime) {
        this.startDateTime = startDateTime;
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
