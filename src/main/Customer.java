package main;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private String region;
    private String country;

    public Integer getId(){return id;}
    public String getName(){return name;}
    public String getAddress(){return address;}
    public String getPostal(){return postal;}
    public String getPhone(){return phone;}
    public String getRegion(){return region;}
    public String getCountry(){return country;}

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setAddress(String address){this.address = address;}
    public void setPostal(String postal){this.postal = postal;}
    public void setPhone(String phone){this.phone = phone;}
    public void setRegion(String region){this.region = region;}
    public void setCountry(String country){this.country = country;}

    public Customer(){}
    public Customer(int id, String name, String country, String region, String address, String postal, String phone){
        setId(id);
        setName(name);
        setAddress(address);
        setPostal(postal);
        setPhone(phone);
        setCountry(country);
        setRegion(region);
    }
}
