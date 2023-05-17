package main;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;

    public Integer getId(){return id;}
    public String getName(){return name;}
    public String getAddress(){return address;}
    public String getPostal(){return postal;}
    public String getPhone(){return phone;}

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setAddress(String address){this.address = address;}
    public void setPostal(String postal){this.postal = postal;}
    public void setPhone(String phone){this.phone = phone;}

    public Customer(){}
    public Customer(int id, String name, String address, String postal, String phone){
        setId(id);
        setName(name);
        setAddress(address);
        setPostal(postal);
        setPhone(phone);
    }
}
