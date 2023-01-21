package com.example.sportapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Entity
public class Review {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int reviewId;
    String emailOfOwner="";
    String city="";
    String sport="";
    String img="";
    String description="";

    public Review() {}

    public Review(String emailOfOwner,String description, String city, String sport, String img) {
        this.emailOfOwner = emailOfOwner;
        this.city = city;
        this.sport = sport;
        this.img = img;
        this.description=description;

    }

    public Review(String id,String emailOfOwner,String description, String city, String sport, String img) {
        this.reviewId=Integer.parseInt(id);
        this.emailOfOwner = emailOfOwner;
        this.city = city;
        this.sport = sport;
        this.img = img;
        this.description=description;

    }

    public void generateID(){
        Random rand = new Random();
        this.reviewId = rand.nextInt((200000 - 100000) + 1) + 100000;
    }


    static final String REVIEWID = "reviewid";
    static final String EMAILOFOWNER = "emailOfOwner";
    static final String DESCRIPTION = "description";
    static final String CITY = "city";
    static final String SPORT = "sport";
    static final String IMG = "img";
    static final String COLLECTION = "reviews";

    public static Review fromJson(Map<String,Object> json){
        String reviewid=String.valueOf(json.get(REVIEWID));
        String emailOfOwner = (String)json.get(EMAILOFOWNER);
//        String reviewid = (String)json.get(REVIEWID);
        String city = (String)json.get(CITY);
        String sport = (String)json.get(SPORT);
        String img = (String)json.get(IMG);
        String description = (String)json.get(DESCRIPTION);
        Review r = new Review(reviewid,emailOfOwner,description,city,sport,img);
        return r;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(REVIEWID, getId());
        json.put(EMAILOFOWNER, getEmailOfOwner());
        json.put(DESCRIPTION, getDescription());
        json.put(CITY, getCity());
        json.put(SPORT, getSport());
        json.put(IMG, getImg());
        return json;
    }
    public int getId() {
        return reviewId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int reviewId) {
        this.reviewId = reviewId;
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
