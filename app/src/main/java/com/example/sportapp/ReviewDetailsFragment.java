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
import com.example.sportapp.databinding.FragmentReviewDetailsBinding;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;


public class ReviewDetailsFragment extends Fragment {

    int pos;
    ReviewDetailsFragmentViewModel viewModel;
    @NonNull FragmentReviewDetailsBinding binding;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ReviewDetailsFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Review's details");
        binding = FragmentReviewDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        pos = ReviewDetailsFragmentArgs.fromBundle(getArguments()).getPos();
        bind(viewModel.getData().getValue().get(pos));

        binding.ReviewDetailsFragmentBackBtn.setOnClickListener(view1 ->{
            Navigation.findNavController(view1).popBackStack();
        } );
        return view;
    }

    public void bind(Review re) {
        binding.ReviewDetailsFragmentTvEditCity.setText(re.getCity());
        binding.ReviewDetailsFragmentTvEditSport.setText(re.getSport());
        binding.ReviewDetailsFragmentTvEditDescription.setText(re.getDescription());
        binding.ReviewDetailsFragmentDetailsTvEditMail.setText(re.getEmailOfOwner());
        if (re.getImg()!= null && !re.getImg().equals("")) {
            Picasso.get().load(re.getImg()).placeholder(R.drawable.no_photo).into(binding.ReviewDetailsFragmentReviewImageview);
        }else{
            binding.ReviewDetailsFragmentReviewImageview.setImageResource(R.drawable.no_photo);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bind(viewModel.getData().getValue().get(pos));
    }

}