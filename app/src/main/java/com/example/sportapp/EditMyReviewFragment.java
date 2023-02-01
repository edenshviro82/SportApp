package com.example.sportapp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.sportapp.databinding.FragmentEditMyReviewBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;


public class EditMyReviewFragment extends Fragment {


    int pos;
    String email,sport;
    Review newR;
    EditMyReviewFragmentViewModel viewModel;
    @NonNull FragmentEditMyReviewBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;
    View view;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditMyReviewFragmentViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register the contract to handle camera request result
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                // check if the result is not null
                if (result != null) {
                    // set the result as the image for the ImageView
                    binding.editMyReviewIv.setImageBitmap(result);
                    // set the flag to indicate that an avatar has been selected
                    isAvatarSelected = true;
                }
            }
        });
        // Register the contract to handle gallery request result
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.editMyReviewIv.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Your Review");
        binding = FragmentEditMyReviewBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        pos = EditMyReviewFragmentArgs.fromBundle(getArguments()).getPos();
        email = EditMyReviewFragmentArgs.fromBundle(getArguments()).getUserEmail();
        newR=new Review();
        binding.progressBar.setVisibility(View.GONE);

        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,viewModel.getType());
        binding.editMyReviewSportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sport = viewModel.getType()[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.editMyReviewSportSpinner.setAdapter(adapter);

        binding.editMyReviewSaveBtn.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);

            newR=viewModel.getMyData(email).get(pos);
            newR.setSport(sport);

            if(!binding.editMyReviewEditCity.getText().toString().equals(""))
                newR.setCity(binding.editMyReviewEditCity.getText().toString());
            if(!binding.editMyReviewDescriptionInputEt.getText().toString().equals(""))
                newR.setDescription( binding.editMyReviewDescriptionInputEt.getText().toString());

            if (isAvatarSelected){
                binding.editMyReviewIv.setDrawingCacheEnabled(true);
                binding.editMyReviewIv.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.editMyReviewIv.getDrawable()).getBitmap();
                Model.instance().uploadImage(String.valueOf(newR.getId()), bitmap, url->{
                    if (url != null){
                        newR.setImg(url);
                    }
                    Model.instance().addReview(newR,()->{
                        binding.progressBar.setVisibility(View.GONE);
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            }else {
                Model.instance().addReview(newR,()->{
                    binding.progressBar.setVisibility(View.GONE);
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });

        binding.editMyReviewCancelBtn.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).popBackStack();
        });


        binding.addFromCameraIv.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.addFromGalleryIv.setOnClickListener(view1->{
            galleryLauncher.launch("media/*");
        });

        return view;
    }
    private void bind(Review re) {
        if (re.getImg()  != null && !re.getImg().equals("")) {
            Picasso.get().load(re.getImg()).placeholder(R.drawable.addpic).into(binding.editMyReviewIv);
        }else{
            binding.editMyReviewIv.setImageResource(R.drawable.addpic);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bind(viewModel.getMyData(email).get(pos));
    }




}
