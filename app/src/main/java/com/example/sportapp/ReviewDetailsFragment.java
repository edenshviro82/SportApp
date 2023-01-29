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



public class ReviewDetailsFragment extends Fragment {

    int pos;
    TextView cityTV, sportTV, descriptionTV, emailTV;
    Button backBtn;
    Review re;
    ImageView avatarImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Review's details");
        View view = inflater.inflate(R.layout.fragment_review_details, container, false);
        pos = ReviewDetailsFragmentArgs.fromBundle(getArguments()).getPos();
        cityTV = view.findViewById(R.id.ReviewDetailsFragment_tv_edit_city);
        sportTV = view.findViewById(R.id.ReviewDetailsFragment_tv_edit_sport);
        descriptionTV=view.findViewById(R.id.ReviewDetailsFragment_tv_edit_description);
        emailTV= view.findViewById(R.id.ReviewDetailsFragment_details_tv_edit_mail);
        avatarImg = view.findViewById(R.id.ReviewDetailsFragment_review_imageview);
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
        if (re.getImg()  != "") {
            Picasso.get().load(re.getImg()).placeholder(R.drawable.no_photo).into(avatarImg);
        }else{
            avatarImg.setImageResource(R.drawable.no_photo);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        reloadData();
    }

    void reloadData(){
        Model.instance().getAllReviews((allReviews)->{
            re=allReviews.get(pos);
            this.bind(re,pos);
        });
    }

}