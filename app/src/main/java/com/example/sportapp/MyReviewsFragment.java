package com.example.sportapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sportapp.databinding.FragmentMyReviewsBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;
import java.util.List;


public class MyReviewsFragment extends Fragment {


    MyReviewsFragment.ReviewRecyclerAdapter adapter;
    String email;
    @NonNull FragmentMyReviewsBinding binding;
    MyReviewsFragmentViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyReviewsFragmentViewModel.class);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Your Reviews");
        binding= FragmentMyReviewsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        email = MyReviewsFragmentArgs.fromBundle(getArguments()).getUserEmail();

        binding.myReviewsRecycler.setHasFixedSize(true);
        binding.myReviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new MyReviewsFragment.ReviewRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.myReviewsRecycler.setAdapter(adapter);

        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(viewModel.getMyData(list,email));
        });

        Model.instance().EventReviewsListLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.myReviewsSwipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.myReviewsSwipeRefresh.setOnRefreshListener(()->{
            reloadData();
        });

        adapter.setOnItemClickListener((int pos)-> {
                    Review re = viewModel.getMyData(email).get(pos);
                    MyReviewsFragmentDirections.ActionMyReviewsFragmentToMyReviewDetailsFragment action = MyReviewsFragmentDirections.actionMyReviewsFragmentToMyReviewDetailsFragment(pos,re.getEmailOfOwner());
                    Navigation.findNavController(view).navigate(action);
                }
        );
        return view;

    }
    void reloadData() {
        Model.instance().refreshAllReviews();
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData();
        adapter.notifyDataSetChanged();
    }


    //--------------------- view holder ---------------------------
    class MyReviewsViewHolder extends RecyclerView.ViewHolder{
        TextView cityTV;
        TextView sportTV;
        ImageView avatarImg;

        public MyReviewsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            cityTV = itemView.findViewById(R.id.allReviewsRow_city);
            sportTV = itemView.findViewById(R.id.allReviewsRow_sport_tv);
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
            if (re.getImg()!= null && !re.getImg().equals("")) {
                Picasso.get().load(re.getImg()).placeholder(R.drawable.no_photo).into(avatarImg);
            }else{
                avatarImg.setImageResource(R.drawable.no_photo);
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
            if (data == null) return 0;
            return data.size();
        }
   }



}
