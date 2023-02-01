package com.example.sportapp;

import androidx.lifecycle.ViewModel;
import com.example.sportapp.model.Model;

public class SignUpFragmentViewModel extends ViewModel {

    private static String[] type = Model.instance().getType();

    String[] getType() {
        return type;
    }
}
