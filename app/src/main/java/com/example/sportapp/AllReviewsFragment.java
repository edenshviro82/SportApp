package com.example.sportapp;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sportapp.databinding.FragmentAllReviewsBinding;
import com.example.sportapp.databinding.FragmentSignUpBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AllReviewsFragment extends Fragment {


    List<Review> data=new LinkedList<>();
    RecyclerView list;
    ReviewRecyclerAdapter adapter;
    ProgressBar pb;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_all_reviews, container, false);
        list = view.findViewById(R.id.allReviews_recycler);
        list.setHasFixedSize(true);
//        data = Model.instance().getAllReviews();
        pb=view.findViewById(R.id.allReviews_progressBar);
        reloadData();

        list.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new ReviewRecyclerAdapter(getLayoutInflater(),data);
        list.setAdapter(adapter);


        adapter.setOnItemClickListener((int pos)-> {
                    Log.d("TAG", "Row was clicked " + pos);
                    Review re = data.get(pos);
                    AllReviewsFragmentDirections.ActionAllReviewsFragmentToReviewDetailsFragment action = AllReviewsFragmentDirections.actionAllReviewsFragmentToReviewDetailsFragment(pos);
                    Navigation.findNavController(view).navigate((NavDirections) action);

                }
        );

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData();
//        adapter.notifyDataSetChanged();
    }


    void reloadData() {
        Log.d("TAG","reload data");
        pb.setVisibility(View.VISIBLE);
       // data=Model.instance().getAllReviews();
        Model.instance().getAllReviews((reviewList)->{
            data=reviewList;
            adapter.setData(reviewList);
        });
        pb.setVisibility(View.GONE);
    }

    //--------------------- view holder ---------------------------
    class AllReviewsViewHolder extends RecyclerView.ViewHolder{
        TextView cityTV;
        TextView sportTV;
        TextView descriptionTV;

        public AllReviewsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
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
            return data.size();
        }
    }

}

