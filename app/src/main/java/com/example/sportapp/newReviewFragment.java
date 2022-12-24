package com.example.sportapp;


import static androidx.core.app.Person.fromBundle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sportapp.databinding.FragmentNewReviewBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;


public class newReviewFragment extends Fragment {

    FragmentNewReviewBinding binding;
    Button saveBtn,cancelBtn;
    private NavDirections action;
    String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            this.email = bundle.getString("Email");
        }

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.newReviewFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewReviewBinding.inflate(inflater, container, false);
        email = newReviewFragmentArgs.fromBundle(getArguments()).getUserEmail();

        saveBtn.setOnClickListener((view -> {

            String city= binding.newReviewCitysInputEt.getText().toString();
            String sport= binding.newReviewSportInputEt.getText().toString();
            String description= binding.newReviewDescriptionInputEt.getText().toString();


            Review newR= new Review("0",email,description,city,sport,"");
            Model.instance().getAllReviews().add(newR);

            Log.d("TAG","num of reviews : "+Model.instance().getAllReviews().size());
        }));

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());


        return binding.getRoot();
    }
}