package com.example.sportapp;

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
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.example.sportapp.databinding.FragmentAddReviewBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;


public class AddReviewFragment extends Fragment {

    FragmentAddReviewBinding binding;
    String sport,email;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;
    AddReviewFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the parent activity
        FragmentActivity parentActivity = getActivity();

        // Add a menu provider to the parent activity
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                // Remove the "newReviewFragment" menu item
                menu.removeItem(R.id.newReviewFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Return false for menu item selection event
                return false;
            }
        }, this, Lifecycle.State.RESUMED);

        // Register for activity result for camera preview
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    // Update the image view with the camera preview result
                    binding.addReviewAvatarImg.setImageBitmap(result);
                    // Set flag indicating that an avatar is selected
                    isAvatarSelected = true;
                }
            }
        });

        // Register for activity result for gallery selection
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    // Update the image view with the gallery selection
                    binding.addReviewAvatarImg.setImageURI(result);
                    // Set flag indicating that an avatar is selected
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AddReviewFragmentViewModel.class);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add New Review");
        binding = FragmentAddReviewBinding.inflate(inflater, container, false);
        email = AddReviewFragmentArgs.fromBundle(getArguments()).getUserEmail();
        binding.addReviewProgressBar.setVisibility(View.GONE);


        //sport spinner
        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,viewModel.getType());
        binding.addReviewSportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sport=viewModel.getType()[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.addReviewSportSpinner.setAdapter(adapter);

        binding.addReviewSaveBtn.setOnClickListener((view -> {
            // Show the progress bar
            binding.addReviewProgressBar.setVisibility(View.VISIBLE);

            // Get the city and description from the input fields
            String city = binding.addReviewCitysInputEt.getText().toString();
            String description = binding.addReviewDescriptionInputEt.getText().toString();

            // Create a new review object with the entered data
            Review newR = new Review(email, description, city, sport, "");
            newR.generateID();

            if (isAvatarSelected){
                // Enable drawing cache for the image view
                binding.addReviewAvatarImg.setDrawingCacheEnabled(true);
                binding.addReviewAvatarImg.buildDrawingCache();
                // Get the bitmap from the image view
                Bitmap bitmap = ((BitmapDrawable) binding.addReviewAvatarImg.getDrawable()).getBitmap();
                // Upload the image
                Model.instance().uploadImage(String.valueOf(newR.reviewId), bitmap, url->{
                    if (url != null){
                        // Set the image URL in the review object
                        newR.setImg(url);
                    }
                    // Add the review to the model
                    Model.instance().addReview(newR,()->{
                        // Hide the progress bar
                        binding.addReviewProgressBar.setVisibility(View.GONE);
                        // Pop the current fragment from the navigation stack
                        Navigation.findNavController(view).popBackStack();
                    });
                });
            }else {
                // Add the review to the model without an image
                Model.instance().addReview(newR,()->{
                    // Hide the progress bar
                    binding.addReviewProgressBar.setVisibility(View.GONE);
                    // Pop the current fragment from the navigation stack
                    Navigation.findNavController(view).popBackStack();
                });
            }
        }));


        binding.addFromCameraIv.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.addFromGalleryIv.setOnClickListener(view1->{
            galleryLauncher.launch("media/*");
        });

        binding.addReviewCancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());


        return binding.getRoot();
    }

}

