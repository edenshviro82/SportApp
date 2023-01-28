package com.example.sportapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;
import java.util.LinkedList;
import java.util.List;


public class MyReviewDetailsFragment extends Fragment {

    int pos;
    TextView cityTV, sportTV, descriptionTV, emailTV;
    Button editBtn;
    Review re;
    String email;
    List<Review> data=new LinkedList<>();
    ImageView avatarImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Review's info");
        View view = inflater.inflate(R.layout.fragment_my_review_details, container, false);
        pos = MyReviewDetailsFragmentArgs.fromBundle(getArguments()).getPos();
        email = MyReviewDetailsFragmentArgs.fromBundle(getArguments()).getUserEmail();
        cityTV = view.findViewById(R.id.MyReviewDetailsFragment_tv_edit_city);
        sportTV = view.findViewById(R.id.MyReviewDetailsFragment_tv_edit_sport);
        descriptionTV=view.findViewById(R.id.MyReviewDetailsFragment_tv_edit_description);
        emailTV= view.findViewById(R.id.MyReviewDetailsFragment_details_tv_edit_mail);
        avatarImg = view.findViewById(R.id.MyReviewDetailsFragment_review_imageview);
        editBtn=view.findViewById(R.id.MyReviewDetailsFragment_edit_btn);


        editBtn.setOnClickListener(view1 ->{
            MyReviewDetailsFragmentDirections.ActionMyReviewDetailsFragmentToEditMyReviewFragment action = MyReviewDetailsFragmentDirections.actionMyReviewDetailsFragmentToEditMyReviewFragment(pos,email);
            Navigation.findNavController(view).navigate(action);
        } );
        return view;
    }
    public void reloadData(String email){
        Model.instance().getAllReviews((reviewList)->{
            data=Model.instance().getMyReviews(reviewList,email);
            re=data.get(pos);
            this.bind(re,pos);
        });
    }


    public void bind(Review re, int pos) {
        cityTV.setText(re.getCity());
        sportTV.setText(re.getSport());
        descriptionTV.setText(re.getDescription());
        emailTV.setText(re.getEmailOfOwner());
        if (re.getImg()  != "") {
            Picasso.get().load(re.getImg()).placeholder(R.drawable.addpic).into(avatarImg);
        }else{
            avatarImg.setImageResource(R.drawable.addpic);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        reloadData(email);
    }

}