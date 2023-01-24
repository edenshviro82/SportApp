package com.example.sportapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;

import java.util.List;

public class MyReviewsFragmentViewModel extends ViewModel {


    private LiveData<List<Review>> data = Model.instance().getAllReviews();

    List<Review> getMyData(String email) {
        return Model.instance().getMyReviews(data.getValue(),email);
    }

    List<Review> getMyData(List<Review> l, String email) {
        return Model.instance().getMyReviews(l,email);
    }
    LiveData<List<Review>> getData() {
        return data;
    }
}
