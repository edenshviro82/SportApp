package com.example.sportapp;

import static android.content.Intent.getIntent;

import android.content.Intent;
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

import com.example.sportapp.databinding.FragmentEditUserBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;
import com.squareup.picasso.Picasso;


public class EditUserFragment extends Fragment {
    FragmentEditUserBinding binding;
    Button SignInBtn;
    private NavDirections action;
    String email;
    Button saveBtn,cancelBtn;
    Spinner sportSpinner;
    String sport;
    EditUserFragmentViewModel viewModel;
    User newUser;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.editUserFragment);
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
                    binding.editUserAvatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.editUserAvatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditUserBinding.inflate(inflater, container, false);
        viewModel=new EditUserFragmentViewModel();
        saveBtn=   binding.getRoot().findViewById(R.id.edit_user_save_btn);
        cancelBtn= binding.getRoot().findViewById(R.id.edit_user_cancel_btn);
        email = EditUserFragmentArgs.fromBundle(getArguments()).getUserEmail();
        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,viewModel.getType());
        sportSpinner=binding.getRoot().findViewById(R.id.edit_user_sport_spinner2);
        sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),viewModel.getType()[i],Toast.LENGTH_LONG).show();
                sport=viewModel.getType()[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sportSpinner.setAdapter(adapter);


        Model.instance().getAllUsers((allUsers)-> {
            newUser = Model.instance().getUserByEmail(allUsers, email);
            if(!newUser.getImg().equals("")) {

                Picasso.get().load(newUser.getImg()).placeholder(R.drawable.addpic).into(binding.editUserAvatarImg);
            }else{
                binding.editUserAvatarImg.setImageResource(R.drawable.addpic);
            }
        });





        saveBtn.setOnClickListener((view -> {

           String name= binding.editUserNameInputEt.getText().toString();
           String city= binding.editUserCityInputEt.getText().toString();

           newUser= new User();
           Model.instance().getAllUsers((allUsers)->{
              newUser= Model.instance().getUserByEmail(allUsers,email);
              if(!city.equals(""))
                  newUser.setCity(city);

              if(!name.equals(""))
                  newUser.setName(name);

              newUser.setSport(sport);

              if (isAvatarSelected){
                  binding.editUserAvatarImg.setDrawingCacheEnabled(true);
                  binding.editUserAvatarImg.buildDrawingCache();
                  Bitmap bitmap = ((BitmapDrawable) binding.editUserAvatarImg.getDrawable()).getBitmap();
                  Model.instance().uploadImage(newUser.getEmail(), bitmap, url->{
                      if (url != null){
                          newUser.setImg(url);
                      }
                      Model.instance().addUser(newUser,()->{
                          Navigation.findNavController(view).popBackStack();

                      });
                  });
              }else {
                  Model.instance().addUser(newUser,()->{
                      Navigation.findNavController(view).popBackStack();

                  });
              }
          });

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