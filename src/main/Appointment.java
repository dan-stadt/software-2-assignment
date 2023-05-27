package main;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private Contact contact = new Contact();
    private Integer appointmentId;
    private Integer customerId;
    private Integer userId;
    private LocalDate date;
    private LocalDateTime endDateTime;
    private LocalDateTime startDateTime;
    private String description;
    private String endHour;
    private String endMinute;
    private String end;
    private String location;
    private String title;
    private String type;
    private String startHour;
    private String startMinute;
    private String start;
    private Timestamp endTimestamp;
    private Timestamp startTimestamp;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a, MM/d/yy");

    public Appointment(){}
    public Appointment(Integer appointmentId, String title, String description, String location,
                       String type, Timestamp start, Timestamp end, Integer customerId,
                       Integer userId, Integer contactId){
        setAppointmentId(appointmentId);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setStartTimestamp(start);
        setEndTimestamp(end);
        setCustomerId(customerId);
        setUserId(userId);
        setContactId(contactId);
        setStartDateTime(start.toLocalDateTime());
        setDate(startDateTime.toLocalDate());
        setStartHour((startDateTime.getHour()));
        setStartMinute(startDateTime.getMinute());
        setStart(startDateTime.format(format));
        setEndDateTime(end.toLocalDateTime());
        setEndHour(endDateTime.getHour());
        setEndMinute(endDateTime.getMinute());
        setEnd(endDateTime.format(format));
    }

    public Integer getAppointmentId() {return appointmentId; }
    public Integer getCustomerId() {return customerId; }
    public Integer getUserId() {return userId; }
    public String getContactName() {return contact.getContactName();}
    public Integer getContactId(){return contact.getContactId();}
    public String getDescription() { return description; }
    public LocalDateTime getEndDateTime() {return endDateTime; }
    public Timestamp getEndTimestamp() {return endTimestamp;}
    public String getEndHour() {return endHour; }
    public String getEndMinute() { return endMinute; }
    public String getLocation() { return location; }
    public LocalDateTime getStartDateTime() {return startDateTime; }
    public String getStartHour() { return startHour; }
    public String getStartMinute() {return startMinute; }
    public Timestamp getStartTimestamp() { return startTimestamp; }
    public String getTitle() { return title; }
    public String getType() {return type;}
    public LocalDate getDate() {return date; }


    public void setAppointmentId(Integer appointmentId) {this.appointmentId = appointmentId;}
    public void setContactName(String contactName) {this.contact.setContactName(contactName);}
    public void setContactId(Integer contactId) {contact.setContactId(contactId);}
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public void setDescription(String description) { this.description = description; }
    public void setEndDateTime(LocalDateTime endDateTime) {this.endDateTime = endDateTime; }
    public void setEndHour(Integer endHour){this.endHour = toTwoDigits(endHour);}
    public void setEndHour(String endHour) {this.endHour = endHour; }
    public void setEndMinute(Integer endMinute){this.endMinute = toTwoDigits(endMinute);}
    public void setEndMinute(String endMinute) { this.endMinute = endMinute; }
    public void setEndTimestamp(Timestamp endTimestamp) { this.endTimestamp = endTimestamp; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(LocalDate date) {this.date = date; }
    public void setStartHour(Integer startHour){this.startHour = toTwoDigits(startHour);}
    public void setStartHour(String startHour) { this.startHour = startHour; }
    public void setStartDateTime(LocalDateTime startDateTime) {this.startDateTime = startDateTime; }
    public void setStartMinute(Integer startMinute){this.startMinute = toTwoDigits(startMinute);}
    public void setStartMinute(String startMinute) {this.startMinute = startMinute; }
    public void setStartTimestamp(Timestamp startTimestamp) { this.startTimestamp = startTimestamp; }
    public void setTitle(String title) { this.title = title; }
    public void setType(String type) { this.type = type; }
    public void setUserId(Integer userId) {this.userId = userId;}

    public String toTwoDigits(Integer time){
        if (time < 10){
            return "0" + time;
        }
        else return Integer.toString(time);
    }
    public String getEnd(){ return end; }
    public String getStart() { return start; }
    public void setStart(String start) { this.start = start; }
    public void setEnd(String end) { this.end = end; }
}
