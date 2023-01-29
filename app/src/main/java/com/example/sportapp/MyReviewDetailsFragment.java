package com.example.sportapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
    String email;
    MyReviewDetailsFragmentViewModel viewModel;
    List<Review> data=new LinkedList<>();
    ImageView avatarImg;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyReviewDetailsFragmentViewModel.class);
    }
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
        bind(viewModel.getMyData(email).get(pos));
    }


    public void bind(Review re) {
        cityTV.setText(re.getCity());
        sportTV.setText(re.getSport());
        descriptionTV.setText(re.getDescription());
        emailTV.setText(re.getEmailOfOwner());
        if (re.getImg()!= null && !re.getImg().equals("")) {
            Picasso.get().load(re.getImg()).placeholder(R.drawable.no_photo).into(avatarImg);
        }else{
            avatarImg.setImageResource(R.drawable.no_photo);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        reloadData(email);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData(email);

    }
}