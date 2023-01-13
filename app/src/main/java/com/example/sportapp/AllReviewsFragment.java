package com.example.sportapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.example.sportapp.model.User;

import java.util.HashMap;
import java.util.List;


public class AllReviewsFragment extends Fragment {


    List<Review> data;
    RecyclerView list;
    ReviewRecyclerAdapter adapter;
    Button add;
    Switch citySwitch;
    String email;
    String city;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_all_reviews, container, false);
        list = view.findViewById(R.id.allReviews_recycler);
        list.setHasFixedSize(true);
        email = AllReviewsFragmentArgs.fromBundle(getArguments()).getUserEmail();
        city = Model.instance().getAllUsers().get(email).getCity();


        citySwitch = view.findViewById(R.id.switch_city);
        citySwitch.setOnCheckedChangeListener((listener,b)->{
            if(citySwitch.isChecked())
            {
                data = Model.instance().getReviewsOrderByCity(city);

            }
            else {
                data = Model.instance().getAllReviews();

            }
        });


        list.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new ReviewRecyclerAdapter();
        list.setAdapter(adapter);


        adapter.setOnItemClickListener((int pos)-> {


                    Log.d("TAG", "Row was clicked " + pos);
                    Review re = data.get(pos);
                    AllReviewsFragmentDirections.ActionAllReviewsFragmentToReviewDetailsFragment action = AllReviewsFragmentDirections.actionAllReviewsFragmentToReviewDetailsFragment(pos);
                    Navigation.findNavController(view).navigate(action);

                }
        );

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
        //list.setAdapter(adapter);


    }

    //--------------------- view holder ---------------------------
    class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView cityTV;
        TextView sportTV;
        TextView descriptionTV;

        public StudentViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            cityTV = itemView.findViewById(R.id.allReviewsRow_city);
            sportTV = itemView.findViewById(R.id.allReviewsRow_sport_tv);
            descriptionTV = itemView.findViewById(R.id.allReviewsRow_description_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(Review re, int pos) {
            cityTV.setText(re.getCity());
            sportTV.setText(re.getSport());
            descriptionTV.setText(re.getDescription());
        }
    }


    //---------------------OnItemClickListener ---------------------------
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }



    //---------------------Recycler adapter ---------------------------
    class ReviewRecyclerAdapter extends RecyclerView.Adapter<StudentViewHolder>{
        OnItemClickListener listener;
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        @NonNull
        @Override
        public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.all_reviews_row,parent,false);
            return new StudentViewHolder(view,listener);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
            Review re = data.get(position);
            holder.bind(re,position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}