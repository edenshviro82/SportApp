package com.example.sportapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;

import java.util.List;

public class EditMyReviewFragmentViewModel extends ViewModel {

    private static String[] type = Model.instance().getType();
    String[] getType() {
        return type;
    }

    private LiveData<List<Review>> data = Model.instance().getAllReviews();

    List<Review> getMyData(String email) {
        return Model.instance().getMyReviews(data.getValue(),email);
    }

}
