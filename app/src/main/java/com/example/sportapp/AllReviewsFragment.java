package com.example.sportapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sportapp.databinding.FragmentAllReviewsBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;
import java.util.List;


public class AllReviewsFragment extends Fragment {


    ReviewRecyclerAdapter adapter;
    AllReviewsFragmentViewModel viewModel;
    @NonNull FragmentAllReviewsBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("All Reviews");
        // Inflates the layout for this fragment and sets the title of the ActionBar
        binding = FragmentAllReviewsBinding.inflate(inflater, container, false);

        // Sets up the RecyclerView with a LinearLayoutManager and sets an adapter
        View view = binding.getRoot();
        binding.allReviewsRecycler.setHasFixedSize(true);
        reloadData();

        binding.allReviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext())); //define the recycler view to be a list
        adapter = new ReviewRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.allReviewsRecycler.setAdapter(adapter);

        // Sets a click listener for the items in the RecyclerView to navigate to ReviewDetailsFragment
        adapter.setOnItemClickListener((int pos)-> {
                    AllReviewsFragmentDirections.ActionAllReviewsFragmentToReviewDetailsFragment action = AllReviewsFragmentDirections.actionAllReviewsFragmentToReviewDetailsFragment(pos);
                    Navigation.findNavController(view).navigate((NavDirections) action);
                }
        );

        // Observes changes in the review list data and updates the adapter
        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });

        // Observes the loading status of the review list and updates the swipeRefresh widget accordingly
        Model.instance().EventReviewsListLoadingState.observe(getViewLifecycleOwner(),status->{
           binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        // Sets a refresh listener to reload the review list data
        binding.swipeRefresh.setOnRefreshListener(()->{
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
        ImageView avatarImg;

        public AllReviewsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
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

        public void bind(Review re) {
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

        // Set the OnItemClickListener
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        // Create a view holder
        @NonNull
        @Override
        public AllReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate the all_reviews_row layout
            View view = getLayoutInflater().inflate(R.layout.all_reviews_row,parent,false);
            // Create and return a new AllReviewsViewHolder
            return new AllReviewsViewHolder(view,listener);
        }

        // Bind the data to the view holder
        @Override
        public void onBindViewHolder(@NonNull AllReviewsViewHolder holder, int position) {
            // Get the Review object at the specified position
            Review re = data.get(position);
            // Bind the Review object to the view holder
            holder.bind(re);
        }

        // Return the number of items in the data
        @Override
        public int getItemCount() {
            if (data == null) return 0;
            return data.size();
        }
    }

}

