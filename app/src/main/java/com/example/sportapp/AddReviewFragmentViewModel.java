package com.example.sportapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;

import java.util.List;

public class AddReviewFragmentViewModel extends ViewModel {

    private String[] type = Model.instance().getType();

    String[] getType() {
        return type;
    }

}
