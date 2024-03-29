package com.example.sportapp.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.sportapp.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int userId;
    String email="";
    String name="";
    String city="";
    String sport="";
    String img="";


    public User() {}

    public User(String name, String email, String city, String sport, String img) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.sport = sport;
        this.img = img;
    }


    static final String USERID = "userId";
    static final String EMAIL = "email";
    static final String NAME = "name";
    static final String CITY = "city";
    static final String SPORT = "sport";
    static final String IMG = "img";
    static final String COLLECTION = "users";


    public static User fromJson(Map<String,Object> json){
        String email = (String)json.get(EMAIL);
        String name = (String)json.get(NAME);
        String city = (String)json.get(CITY);
        String sport = (String)json.get(SPORT);
        String img = (String)json.get(IMG);
        User u = new User(name,email,city,sport,img);
        return u;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(USERID, getUserId());
        json.put(EMAIL, getEmail());
        json.put(NAME, getName());
        json.put(CITY, getCity());
        json.put(SPORT, getSport());
        json.put(IMG, getImg());
        return json;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}