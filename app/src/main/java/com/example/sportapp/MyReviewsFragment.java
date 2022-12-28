package com.example.sportapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class MyReviewsFragment extends Fragment {


    List<Review>  data= new LinkedList<>();
    RecyclerView list;
    MyReviewsFragment.ReviewRecyclerAdapter adapter;
    Button add;
    String email;


//        @Override
//        public void onCreate(@Nullable Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//
//            Bundle bundle = getArguments();
//            if (bundle != null) {
//                this.email = "bundle.getString";
//            }
//        }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
       View view = inflater.inflate(R.layout.fragment_my_reviews, container, false);
       email = MyReviewsFragmentArgs.fromBundle(getArguments()).getUserEmail();
       Log.d("TAG",email);
       data=Model.instance().getAllReviews() ;
        data = this.getMyReviews(email);
        list = view.findViewById(R.id.myReviews_recycler);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new MyReviewsFragment.ReviewRecyclerAdapter();
        list.setAdapter(adapter);


        adapter.setOnItemClickListener((int pos)-> {
                    Log.d("TAG", "Row was clicked " + pos);
                    Review re = data.get(pos);
                    MyReviewsFragmentDirections.ActionMyReviewsFragmentToMyReviewDetailsFragment action = MyReviewsFragmentDirections.actionMyReviewsFragmentToMyReviewDetailsFragment(pos,re.getEmailOfOwner());
                    Navigation.findNavController(view).navigate(action);
                }
        );

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        data = this.getMyReviews(email);
        adapter.notifyDataSetChanged();

        //list.setAdapter(adapter);
        Log.d("TAG", "MyReview   was clicked " + data.size());


    }


    //--------------------- view holder ---------------------------
    class MyReviewsViewHolder extends RecyclerView.ViewHolder{
        TextView cityTV;
        TextView sportTV;
        TextView descriptionTV;

        public MyReviewsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
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
    class ReviewRecyclerAdapter extends RecyclerView.Adapter<MyReviewsViewHolder>{
        OnItemClickListener listener;
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        @NonNull
        @Override
        public MyReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.all_reviews_row,parent,false);
            return new MyReviewsViewHolder(view,listener);
        }

        @Override
        public void onBindViewHolder(@NonNull MyReviewsViewHolder holder, int position) {
            Review re = data.get(position);
            holder.bind(re,position);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
   }

    public List<Review> getMyReviews(String email){

        List<Review> newL = new LinkedList<>();
        for(Review r: Model.instance().getAllReviews())
        {
            if(r.getEmailOfOwner().equals(email))
            {
                newL.add(r);
            }
        }
        return newL;
    }

}
