package com.example.sportapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sportapp.databinding.FragmentMyReviewsBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


public class MyReviewsFragment extends Fragment {


    List<Review>  data= new LinkedList<>();
    RecyclerView list;
    MyReviewsFragment.ReviewRecyclerAdapter adapter;
    String email;
    @NonNull FragmentMyReviewsBinding binding;
    SwipeRefreshLayout sw;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
       View view = inflater.inflate(R.layout.fragment_my_reviews, container, false);
        binding= FragmentMyReviewsBinding.inflate(inflater, container, false);

        email = MyReviewsFragmentArgs.fromBundle(getArguments()).getUserEmail();
       Log.d("TAG",email);
        sw=view.findViewById(R.id.my_reviews_swipeRefresh);
        list = view.findViewById(R.id.myReviews_recycler);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new MyReviewsFragment.ReviewRecyclerAdapter(getLayoutInflater(),data);

        list.setAdapter(adapter);

        sw.setOnRefreshListener(()->{
            reloadData(email);
        });

        adapter.setOnItemClickListener((int pos)-> {
                    Log.d("TAG", "Row was clicked " + pos);
                    Review re = data.get(pos);
                    MyReviewsFragmentDirections.ActionMyReviewsFragmentToMyReviewDetailsFragment action = MyReviewsFragmentDirections.actionMyReviewsFragmentToMyReviewDetailsFragment(pos,re.getEmailOfOwner());
                    Navigation.findNavController(view).navigate(action);
                }
        );

        return view;

    }
    void reloadData(String email) {
        sw.setRefreshing(true);
        Model.instance().getAllReviews((reviewList)->{
            data=Model.instance().getMyReviews(reviewList,email);
            adapter.setData(data);
            Log.d("TAG","progress");
            sw.setRefreshing(false);
        });
     //   sw.setRefreshing(false);

    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData(email);
        adapter.notifyDataSetChanged();
    }


    //--------------------- view holder ---------------------------
    class MyReviewsViewHolder extends RecyclerView.ViewHolder{
        TextView cityTV;
        TextView sportTV;
        TextView descriptionTV;
        ImageView avatarImg;

        public MyReviewsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            cityTV = itemView.findViewById(R.id.allReviewsRow_city);
            sportTV = itemView.findViewById(R.id.allReviewsRow_sport_tv);
            descriptionTV = itemView.findViewById(R.id.allReviewsRow_description_tv);
            avatarImg = itemView.findViewById(R.id.allReviewsRow_avatar_img);

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
            if (re.getImg()  != "") {
                Picasso.get().load(re.getImg()).placeholder(R.drawable.addpic).into(avatarImg);
            }else{
                avatarImg.setImageResource(R.drawable.addpic);
            }
        }
    }


    //---------------------OnItemClickListener ---------------------------
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }



    //---------------------Recycler adapter ---------------------------
    class ReviewRecyclerAdapter extends RecyclerView.Adapter<MyReviewsViewHolder>{
        OnItemClickListener listener;

        LayoutInflater inflater;
        List<Review> data;

        public void setData(List<Review> data){
            this.data = data;
            notifyDataSetChanged();
        }

        public ReviewRecyclerAdapter(LayoutInflater inflater, List<Review> data){
            this.inflater = inflater;
            this.data = data;
        }
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



}
