package com.example.sportapp.model;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Model {

    private static final Model _instance = new Model();

    public static Model instance(){
        return _instance;
    }
    private Model(){
//        for(int i=0;i<20;i++){
//            addUser(new User("name " + i,"id "+i,"phone "+i,"address "+i, "avatar", false));
//        }
    }


    List<Review> allReviews = new LinkedList<>();
    HashMap<String,User> userMap = new HashMap<>();



    public void addUser(User u){userMap.put(u.email,u);}
    public HashMap<String,User> getAllUsers(){
        return userMap;
    }
    public void printUser(String email){
        Log.d("TAG","user name : "+ userMap.get(email).getName().toString() +" user email : "+ userMap.get(email).getEmail().toString()+" user password : "+ userMap.get(email).getPassword().toString()+" user city : "+ userMap.get(email).getCity().toString()+" user sport : "+ userMap.get(email).getSport().toString());
    }

    public void addReview(Review r){allReviews.add(r);}
    public List<Review> getAllReviews(){
        return allReviews;
    }





}
