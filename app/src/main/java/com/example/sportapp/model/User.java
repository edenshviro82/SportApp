package com.example.sportapp.model;


public class User {

     String name;
     String email;
     String password;
     String city;
     String sport;
     String img;


    public User(String name, String email, String password, String city, String sport, String img) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.sport = sport;
        this.img = img;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }




}
