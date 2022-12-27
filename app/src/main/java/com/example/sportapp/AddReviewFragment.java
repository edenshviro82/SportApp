package com.example.sportapp;

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

import com.example.sportapp.databinding.FragmentAddReviewBinding;
import com.example.sportapp.databinding.FragmentNewReviewBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;


public class AddReviewFragment extends Fragment {

    FragmentAddReviewBinding binding;
    Button saveBtn,cancelBtn;
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

        binding = FragmentAddReviewBinding.inflate(inflater, container, false);
        email = AddReviewFragmentArgs.fromBundle(getArguments()).getUserEmail();
        saveBtn = binding.getRoot().findViewById(R.id.add_Review_save_btn);
        cancelBtn = binding.getRoot().findViewById(R.id.add_Review_cancel_btn);


        saveBtn.setOnClickListener((view -> {

            String city = binding.addReviewCitysInputEt.getText().toString();
            String sport = binding.addReviewSportInputEt.getText().toString();
            String description = binding.addReviewDescriptionInputEt.getText().toString();


            Review newR = new Review("0", email, description, city, sport, "");
            Model.instance().getAllReviews().add(newR);

            Log.d("TAG", "num of reviews : " + Model.instance().getAllReviews().size());
            Navigation.findNavController(view).popBackStack();
        }));

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());


        return binding.getRoot();
    }
}