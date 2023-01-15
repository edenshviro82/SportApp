package com.example.sportapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.nullness.qual.NonNull;

@Entity
public class Review {
    @PrimaryKey
    @NonNull
    int id=0;
    String emailOfOwner="";
    String city="";
    String sport="";
    String img="";
    String description="";

    public Review() {

    }

    public Review(int id, String emailOfOwner,String description, String city, String sport, String img) {
        this.id = id;
        this.emailOfOwner = emailOfOwner;
        this.city = city;
        this.sport = sport;
        this.img = img;
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
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


}
