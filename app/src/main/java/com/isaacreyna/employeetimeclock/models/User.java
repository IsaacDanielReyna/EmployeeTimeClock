package com.isaacreyna.employeetimeclock.models;




public class User {
    // Following variables must match json keys
    public int id;
    public String username;
    public String email;
    public String token;
    public String photo;
    public String first_name;
    public String middle_name;
    public String last_name;
    public String phone_number;
    public String address;
    public String city;
    public String state;
    public String zip_code;
    public int default_company;

    public String DisplayName(){
        String name;
        if ((this.first_name == null) || (this.middle_name == null) || (this.last_name == null))
            name = this.username;
        else if (this.first_name.isEmpty() || this.middle_name.isEmpty() || this.last_name.isEmpty())
            name = this.username;
        else
            name = this.first_name + " " + this.middle_name + " " + this.last_name;
     return name;
    }
}



