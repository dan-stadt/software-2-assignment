package main;

import helper.CustomerQuery;

import java.sql.SQLException;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private String division;
    private int divisionId;
    private String country;

    public Customer(int id, String name, String address, String postal, String phone, int divisionId) throws SQLException {
        setId(id);
        setName(name);
        setAddress(address);
        setPostal(postal);
        setPhone(phone);
        setDivisionId(divisionId);
        String division = CustomerQuery.getDivision(divisionId);
        String country = CustomerQuery.getCountry(divisionId);
        setCountry(country);
        setDivision(division);
    }

    public Customer(String name, String address, String postal, String phone, String division) throws SQLException {
        setName(name);
        setAddress(address);
        setPostal(postal);
        setPhone(phone);
        setDivision(division);
        int divisionId = CustomerQuery.getDivisionId(division);
        setDivisionId(divisionId);
        String country = CustomerQuery.getCountry(divisionId);
        setCountry(country);
    }
    public String getAddress(){return address;}
    public String getCountry(){return country;}
    public int getDivisionId() {
        return divisionId;
    }
    public String getDivision(){return division;}
    public Integer getId(){return id;}
    public String getName(){return name;}
    public String getPostal(){return postal;}
    public String getPhone(){return phone;}
    public void setAddress(String address){this.address = address;}
    public void setCountry(String country){this.country = country;}
    public void setDivision(String division){this.division = division;}
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setPostal(String postal){this.postal = postal;}
    public void setPhone(String phone){this.phone = phone;}

}
