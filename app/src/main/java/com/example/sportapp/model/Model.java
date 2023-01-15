package com.example.sportapp.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//Dao- all the function that i can access trough them to my DB


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
    AppLocalDbRepository localDb=AppLocalDb.getAppDb();
    Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());


    List<Review> allReviews = new LinkedList<>();
    HashMap<String,User> userMap = new HashMap<>();
    List<Review> myReviews=new LinkedList<>();
    String[] type={"Running","Skiing","Kiting","Tennis","Yoga","Biking","Badminton","Outside walking","Football","Basketball","Abseiling"};

    public String[] getType() {
        return type;
    }

    public void addUser(User u){
        userMap.put(u.email,u);
    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public void getAllUsers(Listener<List<User>> callback){
            executor.execute(()->{
                List<User> data = localDb.userDao().getAllUsers();
                //return to the main thread because we want the executor to do only DB missions
                mainHandler.post(()->{
                    callback.onComplete(data);
                });
        });

       // return userMap;
    }
    public void printUser(String email){
        Log.d("TAG","user name : "+ userMap.get(email).getName().toString() +" user email : "+ userMap.get(email).getEmail().toString()+" user password : "+ userMap.get(email).getPassword().toString()+" user city : "+ userMap.get(email).getCity().toString()+" user sport : "+ userMap.get(email).getSport().toString());
    }
    public User getUserByEmail(List<User> users, String email){
      for(User u:users)
        {
            if(u.getEmail().equals(email))
                return u;
        }
      return new User();

    }

    public interface Listener2<Void>{
        void onComplete();
    }

//**********************************************
    public void addReview(Review r, Listener2<Void> listener) {
        executor.execute(() -> {
         //   List<Review> data = localDb.reviewDao().getAllReviews();
            localDb.reviewDao().insertAll(r);
            //return to the main thread because we want the executor to do only DB missions
            mainHandler.post(() -> {
                listener.onComplete();
            });

        });
    }

    public void getAllReviews(Listener<List<Review>> callback){
        executor.execute(() -> {
            List<Review> data = localDb.reviewDao().getAllReviews();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //return to the main thread because we want the executor to do only DB missions
            mainHandler.post(() -> {
                callback.onComplete(data);
            });
        });

    }
    public List<Review> getMyReviews(List<Review> all, String email){

        List<Review> mine=new LinkedList<>();
        for(Review r: all)
        {
            if(r.getEmailOfOwner().equals(email))
            {
                mine.add(r);
            }
        }
        return mine;
    }
    public int getIndexByReviewId(int id)
    {
        for(int i=0; i<allReviews.size(); i++)
            if(allReviews.get(i).getId()==id)
                return i;
        return -1;
    }

    public Review getReviewById(int id)
    {
        for(int i=0; i<allReviews.size(); i++)
            if(allReviews.get(i).getId()==id)
                return allReviews.get(i);
        return null;
    }

    public List<Review> getReviewsOrderByCity(String city){
        List<Review> list = new LinkedList<>();
        for(Review r: allReviews)
        {
            if(r.getCity().equals(city))
            {
                list.add(r);
            }
        }
        return list;
    }
    public void deleteReview(int id) {

        allReviews.remove(getIndexByReviewId(id));
    }
}
