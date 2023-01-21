package com.example.sportapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.example.sportapp.model.User;

import java.util.List;

public class AllReviewsFragmentViewModel extends ViewModel {

    private LiveData<List<Review>> data = Model.instance().getAllReviews();

    LiveData<List<Review>> getData() {
        return data;
    }
}
