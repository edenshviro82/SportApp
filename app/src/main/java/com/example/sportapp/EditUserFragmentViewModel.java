package com.example.sportapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.example.sportapp.model.User;

import java.util.List;

public class EditUserFragmentViewModel extends ViewModel {

    private static String[] type = Model.instance().getType();

    String[] getType() {
        return type;
    }

}
