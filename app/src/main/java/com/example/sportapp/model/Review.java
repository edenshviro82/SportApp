package com.example.sportapp.model;

public class Review {

    String id;
    String emailOfOwner;
    String city;
    String sport;
    String img;
    String description;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailOfOwner() {
        return emailOfOwner;
    }

    public void setEmailOfOwner(String emailOfOwner) {
        this.emailOfOwner = emailOfOwner;
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

    public Review(String id, String emailOfOwner,String description, String city, String sport, String img) {
        this.id = id;
        this.emailOfOwner = emailOfOwner;
        this.city = city;
        this.sport = sport;
        this.img = img;
        this.description=description;
    }
}
