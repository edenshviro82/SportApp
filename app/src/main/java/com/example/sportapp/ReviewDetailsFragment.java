package com.example.sportapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;

import java.util.LinkedList;
import java.util.List;


public class ReviewDetailsFragment extends Fragment {

    int pos;
    TextView cityTV, sportTV, descriptionTV, emailTV;
    Button backBtn,editBtn;
    Review re;
    List<Review> data= new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_details, container, false);
        pos = ReviewDetailsFragmentArgs.fromBundle(getArguments()).getPos();

        cityTV = view.findViewById(R.id.ReviewDetailsFragment_tv_edit_city);
        sportTV = view.findViewById(R.id.ReviewDetailsFragment_tv_edit_sport);
        descriptionTV=view.findViewById(R.id.ReviewDetailsFragment_tv_edit_description);
        emailTV= view.findViewById(R.id.ReviewDetailsFragment_details_tv_edit_mail);

        backBtn=view.findViewById(R.id.ReviewDetailsFragment_back_btn);

        reloadData();




        backBtn.setOnClickListener(view1 ->{
            Navigation.findNavController(view1).popBackStack();
        } );
        return view;
    }

    public void bind(Review re, int pos) {
        cityTV.setText(re.getCity());
        sportTV.setText(re.getSport());
        descriptionTV.setText(re.getDescription());
        emailTV.setText(re.getEmailOfOwner());
    }


    @Override
    public void onStart() {
        super.onStart();
        reloadData();
    }

    void reloadData(){
        Model.instance().getAllReviews((allReviews)->{
            data=allReviews;
            re=data.get(pos);
            this.bind(re,pos);
        });
    }

}