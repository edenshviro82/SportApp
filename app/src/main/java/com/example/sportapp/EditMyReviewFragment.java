package com.example.sportapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    Spinner sportSpinner;
    //EMAIL:there is no viewModel because we get the info by specific email?
    List<Review> myData= new LinkedList<>();
    ImageView avatarImg;
    String email;
    String sport;
    EditMyReviewFragmentViewModel viewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditMyReviewFragmentViewModel.class);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_my_review, container, false);
        pos = EditMyReviewFragmentArgs.fromBundle(getArguments()).getPos();
        email = EditMyReviewFragmentArgs.fromBundle(getArguments()).getUserEmail();
        cityET = view.findViewById(R.id.editMyReview_Pt_city);
        sportSpinner = view.findViewById(R.id.editMyReview_sport_spinner);
        descriptionET=view.findViewById(R.id.editMyReview_Pt_description);
        save=view.findViewById(R.id.editMyReview_save_btn);
        cancel=view.findViewById(R.id.editMyReview_cancel_btn);
        delete=view.findViewById(R.id.editMyReview_delete_btn);
        avatarImg = view.findViewById(R.id.editMyReview_iv);

        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,viewModel.getType());
        sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),viewModel.getType()[i],Toast.LENGTH_LONG).show();
                sport = viewModel.getType()[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sportSpinner.setAdapter(adapter);

        save.setOnClickListener(view1 -> {
           Model.instance().getAllReviews((reviewList)->{
               myData= Model.instance().getMyReviews(reviewList,email);
            bindBack(pos);
            Log.d("TAG",myData.get(pos).reviewId+"<- id "+myData.get(pos).getCity());
             Model.instance().addReview(myData.get(pos),()->{
                 Log.d("TAG", myData.get(pos).getDescription() + "   desc");

             });
            });
            Navigation.findNavController(view).popBackStack();

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
        descriptionET.setText(re.getDescription());
        if (re.getImg()  != "") {
            Picasso.get().load(re.getImg()).placeholder(R.drawable.addpic).into(avatarImg);
        }else{
            avatarImg.setImageResource(R.drawable.addpic);
        }
    }


    private void bindBack( int pos) {
        myData.get(pos).setCity(cityET.getText().toString());
        myData.get(pos).setSport(sport);
        myData.get(pos).setDescription(descriptionET.getText().toString());

    }

    @Override
    public void onStart() {
        super.onStart();
//        bind(re,pos);
        reloadData(email);

    }

    public void reloadData(String email){
        Model.instance().getAllReviews((reviewList)->{
            myData=Model.instance().getMyReviews(reviewList,email);
            re=myData.get(pos);
            this.bind(re,pos);

        });
    }


}
