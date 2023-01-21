package com.example.sportapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;

import java.util.List;

public class SignUpFragmentViewModel extends ViewModel {
    private LiveData<List<User>> data = Model.instance().getAllUsers();

    LiveData<List<User>> getData() {
        return data;
    }
}
