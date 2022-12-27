package com.example.sportapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;

import java.util.List;


public class MyReviewDetailsFragment extends Fragment {

    int pos;
    TextView cityTV, sportTV, descriptionTV, emailTV;
    Button backBtn,editBtn;
    Review re;
    List<Review> data= Model.instance().getAllReviews();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_review_details, container, false);
        pos = MyReviewDetailsFragmentArgs.fromBundle(getArguments()).getPos();

        cityTV = view.findViewById(R.id.MyReviewDetailsFragment_tv_edit_city);
        sportTV = view.findViewById(R.id.MyReviewDetailsFragment_tv_edit_sport);
        descriptionTV=view.findViewById(R.id.MyReviewDetailsFragment_tv_edit_description);
        emailTV= view.findViewById(R.id.MyReviewDetailsFragment_details_tv_edit_mail);

        editBtn=view.findViewById(R.id.MyReviewDetailsFragment_edit_btn);

        re=data.get(pos);
        this.bind(re,pos);

        editBtn.setOnClickListener(view1 ->{
            MyReviewDetailsFragmentDirections.ActionMyReviewDetailsFragmentToEditMyReviewFragment action = MyReviewDetailsFragmentDirections.actionMyReviewDetailsFragmentToEditMyReviewFragment(pos);
            Navigation.findNavController(view).navigate(action);
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
        bind(re,pos);
    }

}