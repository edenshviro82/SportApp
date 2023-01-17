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

import java.util.LinkedList;
import java.util.List;


public class MyReviewDetailsFragment extends Fragment {

    int pos;
    TextView cityTV, sportTV, descriptionTV, emailTV;
    Button backBtn,editBtn;
    Review re;
    String email;
    List<Review> data=new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_review_details, container, false);
        pos = MyReviewDetailsFragmentArgs.fromBundle(getArguments()).getPos();
        email = MyReviewDetailsFragmentArgs.fromBundle(getArguments()).getUserEmail();
        cityTV = view.findViewById(R.id.MyReviewDetailsFragment_tv_edit_city);
        sportTV = view.findViewById(R.id.MyReviewDetailsFragment_tv_edit_sport);
        descriptionTV=view.findViewById(R.id.MyReviewDetailsFragment_tv_edit_description);
        emailTV= view.findViewById(R.id.MyReviewDetailsFragment_details_tv_edit_mail);
//        reloadData(email);

        editBtn=view.findViewById(R.id.MyReviewDetailsFragment_edit_btn);
//        Log.d("Tag","size: " + data.size());
//        re=data.get(pos);
//        this.bind(re,pos);

        editBtn.setOnClickListener(view1 ->{
            MyReviewDetailsFragmentDirections.ActionMyReviewDetailsFragmentToEditMyReviewFragment action = MyReviewDetailsFragmentDirections.actionMyReviewDetailsFragmentToEditMyReviewFragment(pos,email);
            Navigation.findNavController(view).navigate(action);
        } );
        return view;
    }
    public void reloadData(String email){
        Log.d("Tag","before: ");
        Model.instance().getAllReviews((reviewList)->{
            Log.d("Tag","reviewList: " + reviewList.size());
            data=Model.instance().getMyReviews(reviewList,email);
            re=data.get(pos);
            this.bind(re,pos);
            Log.d("Tag","size: " + data.size());

        });
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
        reloadData(email);
    }

}