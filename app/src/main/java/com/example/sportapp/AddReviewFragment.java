package com.example.sportapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sportapp.databinding.FragmentAddReviewBinding;
import com.example.sportapp.databinding.FragmentNewReviewBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;

import java.util.List;
import java.util.Random;


public class AddReviewFragment extends Fragment {
    String id;
    FragmentAddReviewBinding binding;
    Button saveBtn,cancelBtn;
    Spinner sportSpinner;
    String sport;
    String email;
    String[] type=Model.instance().getType();
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            this.email = bundle.getString("Email");
        }

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.newReviewFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.addReviewAvatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.addReviewAvatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddReviewBinding.inflate(inflater, container, false);
        email = AddReviewFragmentArgs.fromBundle(getArguments()).getUserEmail();
        saveBtn = binding.getRoot().findViewById(R.id.add_Review_save_btn);
        cancelBtn = binding.getRoot().findViewById(R.id.add_Review_cancel_btn);
        sportSpinner=binding.getRoot().findViewById(R.id.add_Review_sport_spinner);
        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,type);
        sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),type[i],Toast.LENGTH_LONG).show();
                sport=type[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sportSpinner.setAdapter(adapter);



        saveBtn.setOnClickListener((view -> {

            String city = binding.addReviewCitysInputEt.getText().toString();
            String description = binding.addReviewDescriptionInputEt.getText().toString();

            Review newR = new Review(email, description, city, sport, "");
            newR.generateID();

            if (isAvatarSelected){
                binding.addReviewAvatarImg.setDrawingCacheEnabled(true);
                binding.addReviewAvatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.addReviewAvatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(String.valueOf(newR.reviewId), bitmap, url->{
                    if (url != null){
                        newR.setImg(url);
                    }
                    Model.instance().addReview(newR,()->{
                        Navigation.findNavController(view).popBackStack();

                    });
                });
            }else {
                Model.instance().addReview(newR,()->{
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

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());


        return binding.getRoot();
    }
}

//public class AddReviewFragmentViewModel extends ViewModel {
//    private LiveData<List<Review>> data = Model.instance().getAllReviews();
//
//    LiveData<List<Review>> getData(){
//        return data;
//    }
//}
