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
     String password="";
     String city="";
     String sport="";
     String img="";
    public Long lastUpdated;


    public User() {

    }

    public User(String name, String email, String password, String city, String sport, String img) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.sport = sport;
        this.img = img;
    }


    static final String USERID = "userId";
    static final String EMAIL = "email";
    static final String NAME = "name";
    static final String PASSWORD = "password";
    static final String CITY = "city";
    static final String SPORT = "sport";
    static final String IMG = "img";
    static final String COLLECTION = "users";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "users_local_last_update";

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public static User fromJson(Map<String,Object> json){
        String email = (String)json.get(EMAIL);
        String name = (String)json.get(NAME);
        String password = (String)json.get(PASSWORD);
        String city = (String)json.get(CITY);
        String sport = (String)json.get(SPORT);
        String img = (String)json.get(IMG);
        User u = new User(name,email,password,city,sport,img);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            u.setLastUpdated(time.getSeconds());
        }catch(Exception e){}
        return u;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(USERID, getUserId());
        json.put(EMAIL, getEmail());
        json.put(NAME, getName());
        json.put(PASSWORD, getPassword());
        json.put(CITY, getCity());
        json.put(SPORT, getSport());
        json.put(IMG, getImg());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
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

    public int getUserId() {
        return userId;
    }



    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }





}
