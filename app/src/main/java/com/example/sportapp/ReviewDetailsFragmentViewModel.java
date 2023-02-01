package com.example.sportapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;

import java.util.List;

public class ReviewDetailsFragmentViewModel extends ViewModel {

    private LiveData<List<Review>> data = Model.instance().getAllReviews();

    LiveData<List<Review>> getData() {
        return data;
    }
}
