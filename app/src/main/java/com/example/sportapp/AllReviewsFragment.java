package com.example.sportapp;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sportapp.databinding.FragmentAllReviewsBinding;
import com.example.sportapp.databinding.FragmentSignUpBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AllReviewsFragment extends Fragment {

    RecyclerView list;
    ReviewRecyclerAdapter adapter;
    SwipeRefreshLayout sr;
    AllReviewsFragmentViewModel viewModel;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_all_reviews, container, false);

        sr= view.findViewById(R.id.swipeRefresh);
        list = view.findViewById(R.id.allReviews_recycler);
        list.setHasFixedSize(true);
        reloadData();

        list.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new ReviewRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        list.setAdapter(adapter);


        adapter.setOnItemClickListener((int pos)-> {
                    Log.d("TAG", "Row was clicked " + pos);
                    Review re = viewModel.getData().getValue().get(pos);
                    AllReviewsFragmentDirections.ActionAllReviewsFragmentToReviewDetailsFragment action = AllReviewsFragmentDirections.actionAllReviewsFragmentToReviewDetailsFragment(pos);
                    Navigation.findNavController(view).navigate((NavDirections) action);

                }
        );
        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });

        Model.instance().EventReviewsListLoadingState.observe(getViewLifecycleOwner(),status->{
           sr.setRefreshing(status == Model.LoadingState.LOADING);
        });

        sr.setOnRefreshListener(()->{
            reloadData();
        });
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AllReviewsFragmentViewModel.class);
    }

    void reloadData() {
        Model.instance().refreshAllReviews();
    }

    //--------------------- view holder ---------------------------
    class AllReviewsViewHolder extends RecyclerView.ViewHolder{
        TextView cityTV;
        TextView sportTV;
        TextView descriptionTV;
        ImageView avatarImg;

        public AllReviewsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
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
            if (!re.getImg().equals("")) {
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
    class ReviewRecyclerAdapter extends RecyclerView.Adapter<AllReviewsViewHolder>{
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
        public AllReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.all_reviews_row,parent,false);
            return new AllReviewsViewHolder(view,listener);
        }

        @Override
        public void onBindViewHolder(@NonNull AllReviewsViewHolder holder, int position) {
            Review re = data.get(position);
            holder.bind(re,position);
        }

        @Override
        public int getItemCount() {
            if (data == null) return 0;
            return data.size();
        }
    }

}

