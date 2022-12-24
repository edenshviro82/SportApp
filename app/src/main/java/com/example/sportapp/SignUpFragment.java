package com.example.sportapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sportapp.databinding.FragmentSignUpBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;

import java.util.List;


public class SignUpFragment extends Fragment {

    FragmentSignUpBinding binding;
    Button logInBtn,signUpBtn;
    private NavDirections action;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        logInBtn= binding.getRoot().findViewById(R.id.SUfrag_SI_btn);
        signUpBtn= binding.getRoot().findViewById(R.id.SUfrag_SU_btn);

        signUpBtn.setOnClickListener((view)->{

            String name= binding.SUFragNameInputEt.getText().toString();
            String email= binding.SUFragEmailInputEt.getText().toString();
            String pass= binding.SUFragPassInputEt.getText().toString();
            String city= binding.SUFragCitysInputEt.getText().toString();
            String sport= binding.SUFragSportInputEt.getText().toString();

            Model.instance().addUser(new User(name,email,pass,city,sport,""));

            Intent i = new Intent(getActivity(), HomeActivity.class);
            i.putExtra("userEmail",email);
            startActivity(i);

        });

        logInBtn.setOnClickListener((view)->{
            action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
            Navigation.findNavController(view).navigate(action);

        });
        




        return binding.getRoot();

    }

}