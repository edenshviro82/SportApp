package com.example.sportapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportapp.databinding.FragmentUserDetailsBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;
import com.squareup.picasso.Picasso;


public class UserDetailsFragment extends Fragment {

    @NonNull
    FragmentUserDetailsBinding binding;
    String email;
    private NavDirections action;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("User's details");
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        email = UserDetailsFragmentArgs.fromBundle(getArguments()).getUserEmail();
        reloadData();

        binding.UserDetailsFragmentEditBtn.setOnClickListener((view1)->{
            action= UserDetailsFragmentDirections.actionUserDetailsFragmentToEditUserFragment(email);
            Navigation.findNavController(this.getActivity(), R.id.home_navhost).navigate(action);
        });
        return view;
    }

    public void bind(User user) {
        // set the text of the city TextView to the city of the passed user
        binding.UserDetailsFragmentTvEditCity.setText(user.getCity());
        binding.UserDetailsFragmentTvEditSport.setText(user.getSport());
        binding.UserDetailsFragmentDetailsTvEditMail.setText(user.getEmail());
        binding.UserDetailsFragmentTvEditName.setText(user.getName());
        if (user.getImg() != null && !user.getImg().equals("")) {
            // if the image of the passed user is not null or empty
            // load the image using Picasso library and set it to the image view
            Picasso.get().load(user.getImg()).placeholder(R.drawable.no_photo).into(binding.UserDetailsFragmentReviewImageview);
        } else {
            // set the default image to the image view
            binding.UserDetailsFragmentReviewImageview.setImageResource(R.drawable.no_photo);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData();
    }

    private void reloadData() {
        Model.instance().getAllUsers((l)->{
            bind(Model.instance().getUserByEmail(l,email));
        });
    }

}