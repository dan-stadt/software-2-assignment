package main;

import helper.AppointmentQuery;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    //#region Class variables
    private final Contact contact = new Contact();
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
    //#endregion
    /**
     * Default constructor; fields are not populated.
     */
    public Appointment(){}

    /**
     * Constructor files all fields with parameters. Populates all associated date and time variables from start and end.
     * @param appointmentId Takes int Appointment ID as input to populate associated field.
     * @param title Takes String title as input to populate associated field.
     * @param description Takes String description as input to populate associated field.
     * @param location Takes String location as input to populate associated field.
     * @param type Takes String type as input to populate associated field.
     * @param start Takes Timestamp start as input. used to populate all start date and time fields.
     * @param end Takes Timestamp end as input. used to populate all start date and time fields.
     * @param customerId Takes int CustomerId as input to populate associated field.
     * @param userId Takes int userId as input to populate associated field.
     * @param contactId Takes int contactId as input to populate associated field.
     */
    public Appointment(Integer appointmentId, String title, String description, String location,
                       String type, Timestamp start, Timestamp end, Integer customerId,
                       Integer userId, Integer contactId) throws SQLException {
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
        setContactName(getContactNameFromId(contactId));
        setStartDateTime(start.toLocalDateTime());
        setDate(startDateTime.toLocalDate());
        setStartHour((startDateTime.getHour()));
        setStartMinute(startDateTime.getMinute());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a, MM/d/yy");
        setStart(startDateTime.format(format));
        setEndDateTime(end.toLocalDateTime());
        setEndHour(endDateTime.getHour());
        setEndMinute(endDateTime.getMinute());
        setEnd(endDateTime.format(format));
    }

    /**
     * Get Appointment ID as Integer
     * @return appointmentId as Integer
     */
    public Integer getAppointmentId() {return appointmentId; }

    /**
     * Get Contact Name as String
     * @return String contactName
     */
    public String getContactName() {return contact.getContactName();}

    /**
     * Retrieve the Contact Name associated with a Contact ID
     */
    public String getContactNameFromId(int contactId) throws SQLException {
        ObservableList<Contact> contactList= AppointmentQuery.getContactList();
        for (Contact contact : contactList){
            if (contactId == contact.getContactId()){
                return contact.getContactName();
            }
        }
        return null;
    }
    /**
     * Get Contact ID as Integer
      * @return Integer contactID
     */
    public Integer getContactId(){return contact.getContactId();}

    /**
     * Get Customer ID as Integer
     * @return Integer customerId
     */
    public Integer getCustomerId() {return customerId; }

    /**
     * Get date value as a LocalDate
     * @return LocalDate date
     */
    public LocalDate getDate() {return date; }

    /**
     * Get description
     * @return String description
     */
    public String getDescription() { return description; }

    /**
     * Get end formatted according to format variable
     * @return String of formatted end date and time
     */
    public String getEnd(){ return end; }

    /**
     * Get end value as LocalDateTIme
     * @return LocalDateTime end
     */
    public LocalDateTime getEndDateTime() {return endDateTime; }

    /**
     * Get end value as a Timestamp
     * @return Timestamp endTimestamp
     */
    public Timestamp getEndTimestamp() {return endTimestamp;}

    /**
     * Get end hour value as String
     * @return String endHour
     */
    public String getEndHour() {return endHour; }

    /**
     * Get end minute value as String
     * @return String endMinute
     */
    public String getEndMinute() { return endMinute; }

    /**
     * Get location as a String
     * @return String location
     */
    public String getLocation() { return location; }

    /**
     * Get String start formatted according to format variable
     * @return String of formatted start date and time
     */
    public String getStart() { return start; }

    /**
     * Get start value as LocalDateTime
     * @return LocalDateTime startDateTime
     */
    public LocalDateTime getStartDateTime() {return startDateTime; }

    /**
     * Get start hour value as String
     * @return String startHour
     */
    public String getStartHour() { return startHour; }

    /**
     * Get start minute value as String
     * @return String startMinute
     */
    public String getStartMinute() {return startMinute; }

    /**
     * Get start value as a Timestamp
     * @return Timestamp startTimestamp
     */
    public Timestamp getStartTimestamp() { return startTimestamp; }

    /**
     * Get title value as a String
     * @return String title
     */
    public String getTitle() { return title; }

    /**
     * Get type as a String
     * @return String type
     */
    public String getType() {return type;}

    /**
     * Get User ID as Integer
     * @return Integer userId
     */
    public Integer getUserId() {return userId; }

    /**
     * Set appointmentId as Integer value
     * @param appointmentId Integer to set appointmentId
     */
    public void setAppointmentId(Integer appointmentId) {this.appointmentId = appointmentId;}

    /**
     * Set contactName as String value
     * @param contactName String to set contactName
     */
    public void setContactName(String contactName) {this.contact.setContactName(contactName);}

    /**
     * Set contactId as Integer value
     * @param contactId Integer to set contactId
     */
    public void setContactId(Integer contactId) {contact.setContactId(contactId);}

    /**
     * Set customerId as Integer
     * @param customerId Integer to set customerId
     */
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    /**
     * Set date as a LocalDate
     * @param date LocalDate to set date
     */
    public void setDate(LocalDate date) {this.date = date; }

    /**
     * Set description as String value
     * @param description String to set description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Set endDateTime as LocalDateTime value
     * @param endDateTime LocalDateTime to set endDateTime
     */
    public void setEndDateTime(LocalDateTime endDateTime) {this.endDateTime = endDateTime; }

    /**
     * Set endHour as String value
     * @param endHour String to set endHour
     */
    public void setEndHour(Integer endHour){this.endHour = toTwoDigits(endHour);}
    /**
     * Set end as Integer value
     * @param end Integer to set end
     */
    public void setEnd(String end) { this.end = end; }

    /**
     * Set endHour as String value
     * @param endHour String to set endHour
     */
    public void setEndHour(String endHour) {this.endHour = endHour; }

    /**
     * Set endMinute as a two-digit String value
     * @param endMinute Integer to set endMinute
     */
    public void setEndMinute(Integer endMinute){this.endMinute = toTwoDigits(endMinute);}

    /**
     * Set end Timestamp value
     * @param endTimestamp Timestamp to set end
     */
    public void setEndTimestamp(Timestamp endTimestamp) { this.endTimestamp = endTimestamp; }

    /**
     * Set location String value
     * @param location String to set location
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * Set the start String value
     * @param start String of start time to set.
     */
    public void setStart(String start) { this.start = start; }

    /**
     * Set startHour as two-digit String from Integer
     * @param startHour Integer to parse for String startHour
     */
    public void setStartHour(Integer startHour){this.startHour = toTwoDigits(startHour);}

    /**
     * Set startDateTime as LocalDateTime
     * @param startDateTime LocalDateTime to set startDateTime
     */
    public void setStartDateTime(LocalDateTime startDateTime) {this.startDateTime = startDateTime; }

    /**
     * Set startMinute as two-digit String from Integer
     * @param startMinute Integer to parse for String startMinute
     */
    public void setStartMinute(Integer startMinute){this.startMinute = toTwoDigits(startMinute);}

    /**
     * Set startTimestamp Timestamp value
     * @param startTimestamp Timestamp to set startTimestamp
     */
    public void setStartTimestamp(Timestamp startTimestamp) { this.startTimestamp = startTimestamp; }

    /**
     * Set title as String value
     * @param title String to set title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Set type as String
     * @param type String to set type
     */
    public void setType(String type) { this.type = type; }

    /**
     * Set userId as Integer
     * @param userId Integer to set userId
     */
    public void setUserId(Integer userId) {this.userId = userId;}

    /**
     * Converts an Integer to a two-digit String, adds a leading zero for single-digit integers
     * @param time Integer of hours or minutes
     * @return two-digit String.
     */
    public String toTwoDigits(Integer time){
        if (time < 10){
            return "0" + time;
        }
        else return Integer.toString(time);
    }
}
