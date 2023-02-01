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
import com.example.sportapp.databinding.FragmentMyReviewDetailsBinding;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;

public class MyReviewDetailsFragment extends Fragment {

    int pos;
    String email;
    MyReviewDetailsFragmentViewModel viewModel;
    @NonNull FragmentMyReviewDetailsBinding binding;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyReviewDetailsFragmentViewModel.class);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Review's info");
        binding = FragmentMyReviewDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pos = MyReviewDetailsFragmentArgs.fromBundle(getArguments()).getPos();
        email = MyReviewDetailsFragmentArgs.fromBundle(getArguments()).getUserEmail();

        binding.MyReviewDetailsFragmentEditBtn.setOnClickListener(view1 ->{
            MyReviewDetailsFragmentDirections.ActionMyReviewDetailsFragmentToEditMyReviewFragment action = MyReviewDetailsFragmentDirections.actionMyReviewDetailsFragmentToEditMyReviewFragment(pos,email);
            Navigation.findNavController(view).navigate(action);
        } );
        return view;
    }
    public void reloadData(String email){
        bind(viewModel.getMyData(email).get(pos));
    }


    public void bind(Review re) {
        binding.MyReviewDetailsFragmentTvEditCity.setText(re.getCity());
        binding.MyReviewDetailsFragmentTvEditSport.setText(re.getSport());
        binding.MyReviewDetailsFragmentTvEditDescription.setText(re.getDescription());
        binding.MyReviewDetailsFragmentDetailsTvEditMail.setText(re.getEmailOfOwner());
        if (re.getImg()!= null && !re.getImg().equals("")) {
            Picasso.get().load(re.getImg()).placeholder(R.drawable.no_photo).into(binding.MyReviewDetailsFragmentReviewImageview);
        }else{
            binding.MyReviewDetailsFragmentReviewImageview.setImageResource(R.drawable.no_photo);
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