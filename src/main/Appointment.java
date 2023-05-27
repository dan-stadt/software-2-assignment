package main;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    //#region Class variables
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
    //#endregion
    /**
     * Default constructor; fields are not populated.
     */
    public Appointment(){}

    /**
     * Constructor files all fields with parameters. Populates all associated date & time variables from start and end.
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

    /**
     * Get Appointment ID as Integer
     * @return appointmentId as Integer
     */
    public Integer getAppointmentId() {return appointmentId; }

    /**
     *
     * @return
     */
    public Integer getCustomerId() {return customerId; }

    /**
     *
     * @return
     */
    public Integer getUserId() {return userId; }

    /**
     *
     * @return
     */
    public String getContactName() {return contact.getContactName();}

    /**
     *
      * @return
     */
    public Integer getContactId(){return contact.getContactId();}

    /**
     *
     * @return
     */
    public String getDescription() { return description; }
    /**
     * Get String end formatted according to format variable.
     * @return String of formatted end date & time
     */
    public String getEnd(){ return end; }

    /**
     *
      * @return
     */
    public LocalDateTime getEndDateTime() {return endDateTime; }

    /**
     *
     * @return
     */
    public Timestamp getEndTimestamp() {return endTimestamp;}

    /**
     *
     * @return
     */
    public String getEndHour() {return endHour; }

    /**
     *
     * @return
     */
    public String getEndMinute() { return endMinute; }

    /**
     *
     * @return
     */
    public String getLocation() { return location; }

    /**
     * Get String start formatted according to format variable.
     * @return String of formatted start date & time
     */
    public String getStart() { return start; }
    /**
     *
     * @return
     */
    public LocalDateTime getStartDateTime() {return startDateTime; }

    /**
     *
     * @return
     */
    public String getStartHour() { return startHour; }

    /**
     *
     * @return
     */
    public String getStartMinute() {return startMinute; }

    /**
     *
     * @return
     */
    public Timestamp getStartTimestamp() { return startTimestamp; }

    /**
     *
     * @return
     */
    public String getTitle() { return title; }

    /**
     *
     * @return
     */
    public String getType() {return type;}

    /**
     *
     * @return
     */
    public LocalDate getDate() {return date; }

    /**
     *
     * @param appointmentId
     */
    public void setAppointmentId(Integer appointmentId) {this.appointmentId = appointmentId;}

    /**
     *
     * @param contactName
     */
    public void setContactName(String contactName) {this.contact.setContactName(contactName);}

    /**
     *
     * @param contactId
     */
    public void setContactId(Integer contactId) {contact.setContactId(contactId);}

    /**
     *
     * @param customerId
     */
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    /**
     *
     * @param description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     *
     * @param endDateTime
     */
    public void setEndDateTime(LocalDateTime endDateTime) {this.endDateTime = endDateTime; }

    /**
     *
     * @param endHour
     */
    public void setEndHour(Integer endHour){this.endHour = toTwoDigits(endHour);}
    /**
     * Set the object's String end variable.
     * @param end String of end time to set.
     */
    public void setEnd(String end) { this.end = end; }
    public void setEndHour(String endHour) {this.endHour = endHour; }
    public void setEndMinute(Integer endMinute){this.endMinute = toTwoDigits(endMinute);}
    public void setEndMinute(String endMinute) { this.endMinute = endMinute; }
    public void setEndTimestamp(Timestamp endTimestamp) { this.endTimestamp = endTimestamp; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(LocalDate date) {this.date = date; }
    /**
     * Set the object's String start variable.
     * @param start String of start time to set.
     */
    public void setStart(String start) { this.start = start; }
    public void setStartHour(Integer startHour){this.startHour = toTwoDigits(startHour);}
    public void setStartHour(String startHour) { this.startHour = startHour; }
    public void setStartDateTime(LocalDateTime startDateTime) {this.startDateTime = startDateTime; }
    public void setStartMinute(Integer startMinute){this.startMinute = toTwoDigits(startMinute);}
    public void setStartMinute(String startMinute) {this.startMinute = startMinute; }
    public void setStartTimestamp(Timestamp startTimestamp) { this.startTimestamp = startTimestamp; }
    public void setTitle(String title) { this.title = title; }
    public void setType(String type) { this.type = type; }
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
