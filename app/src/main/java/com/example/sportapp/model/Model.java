package com.example.sportapp.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    AppLocalDbRepository localDb=AppLocalDb.getAppDb();
    Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    private LiveData<List<Review>> reviewList;

    static String[] type={"Running","Skiing","Kiting","Tennis","Yoga","Biking","Badminton","Outside walking","Football","Basketball","Abseiling"};

    public String[] getType() {
        return type;
    }

    public void addUser(User u, Listener2<Void> listener) {
        firebaseModel.addUser(u,listener);
    }

    public interface Listener<T>{
        void onComplete(T data);
    }
    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }
    final public MutableLiveData<LoadingState> EventReviewsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);



    public void getAllUsers(Listener<List<User>> callback){
        firebaseModel.getAllUsers(callback);
    }
    public void printUser(User u){

        Log.d("TAG","user name : "+ u.getName().toString());
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


    public LiveData<List<Review>> getAllReviews() {
        if(reviewList == null){
            reviewList = localDb.reviewDao().getAllReviews();
            refreshAllReviews();
        }
        return reviewList;
    }

    public void refreshAllReviews(){
        EventReviewsListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = Review.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllReviewsSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(Review st:list){
                    // insert new records into ROOM
                    localDb.reviewDao().insertAll(st);
                    if (time < st.getLastUpdated()){
                        time = st.getLastUpdated();
                    }
                }
                // update local last update
                Review.setLocalLastUpdate(time);
                Log.d("TAG", " loading");
                EventReviewsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }


    public void addReview(Review r, Listener2<Void> listener) {
        //firebaseModel.addReview(r,listener);
        firebaseModel.addReview(r,()->{
            refreshAllReviews();
            listener.onComplete();
        });
    }

    public void getAllReviews(Listener<List<Review>> callback){
        firebaseModel.getAllReviews(callback);
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

    public void deleteReview(Review r, Listener2<Void> listener) {

        firebaseModel.deleteReview(r,()->{
            refreshAllReviews();
            listener.onComplete();
        });
        executor.execute(() -> {
            //   List<Review> data = localDb.reviewDao().getAllReviews();
            localDb.reviewDao().delete(r);
            //return to the main thread because we want the executor to do only DB missions
            mainHandler.post(() -> {
                listener.onComplete();
            });

        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }
}
