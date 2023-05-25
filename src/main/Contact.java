package main;

public class Contact {
    public Contact(int contactId, String contactName, String email){
        setContactId(contactId);
        setContactName(contactName);
        setEmail(email);
    }
    private  int contactId;
    private String contactName;
    private String email;

    public Contact() { }

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
