package com.example.sportapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class EditMyReviewFragment extends Fragment {


    int pos;
    Button cancel,save,delete;
    EditText cityET, sportET, descriptionET;
    Review re;
    //there is no viewModel because we get the info by specific email?
    List<Review> data= new LinkedList<>();
    ImageView avatarImg;

    String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_my_review, container, false);
        pos = EditMyReviewFragmentArgs.fromBundle(getArguments()).getPos();
        email = EditMyReviewFragmentArgs.fromBundle(getArguments()).getUserEmail();
        cityET = view.findViewById(R.id.editMyReview_Pt_city);
        sportET = view.findViewById(R.id.editMyReview_Pt_sport);
        descriptionET=view.findViewById(R.id.editMyReview_Pt_description);
        save=view.findViewById(R.id.editMyReview_save_btn);
        cancel=view.findViewById(R.id.editMyReview_cancel_btn);
        delete=view.findViewById(R.id.editMyReview_delete_btn);
        avatarImg = view.findViewById(R.id.editMyReview_iv);


        save.setOnClickListener(view1 -> {
           Model.instance().getAllReviews((reviewList)->{
            data= Model.instance().getMyReviews(reviewList,email);
            bindBack(pos);
            Log.d("TAG",data.get(pos).reviewId+"<- id "+data.get(pos).getCity());
             Model.instance().addReview(data.get(pos),()->{

                 Navigation.findNavController(view).popBackStack();
             });
            });
            Log.d("TAG", data.get(pos).getDescription() + "   desc");
        });

        delete.setOnClickListener(view1->{
//            Model.instance().getAllReviews((reviewList)->{
//                allReviews=reviewList;
//                data= Model.instance().getMyReviews(allReviews,email);
//                Model.instance().deleteReview(data.get(pos),()->{
//                    Log.d("TAG",  "   desc");
//
//                });
//                Navigation.findNavController(view1).popBackStack();
//                Navigation.findNavController(view1).popBackStack();
//
//            });


        });
        cancel.setOnClickListener(view1 ->
        {
            Navigation.findNavController(view1).popBackStack();

        });


        return view;
    }
    public void bind(Review re, int pos) {
        cityET.setText(re.getCity());
        sportET.setText(re.getSport());
        descriptionET.setText(re.getDescription());
        if (re.getImg()  != "") {
            Picasso.get().load(re.getImg()).placeholder(R.drawable.addpic).into(avatarImg);
        }else{
            avatarImg.setImageResource(R.drawable.addpic);
        }
    }


    private void bindBack( int pos) {
        data.get(pos).setCity(cityET.getText().toString());
        data.get(pos).setSport(sportET.getText().toString());
        data.get(pos).setDescription(descriptionET.getText().toString());

    }

    @Override
    public void onStart() {
        super.onStart();
//        bind(re,pos);
        reloadData(email);

    }

    public void reloadData(String email){
        Model.instance().getAllReviews((reviewList)->{
            data=Model.instance().getMyReviews(reviewList,email);
            re=data.get(pos);
            this.bind(re,pos);

        });
    }
}
