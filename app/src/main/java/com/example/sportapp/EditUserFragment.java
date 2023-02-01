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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.sportapp.databinding.FragmentEditUserBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;
import com.squareup.picasso.Picasso;


public class EditUserFragment extends Fragment {
    FragmentEditUserBinding binding;
    String email, sport;
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
                menu.removeItem(R.id.userDetailsFragment);
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Your Info");

        binding = FragmentEditUserBinding.inflate(inflater, container, false);
        viewModel=new EditUserFragmentViewModel();
        email = EditUserFragmentArgs.fromBundle(getArguments()).getUserEmail();
        binding.editUserProgressBar.setVisibility(View.GONE);

        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,viewModel.getType());
        binding.editUserSportSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sport=viewModel.getType()[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.editUserSportSpinner2.setAdapter(adapter);


        Model.instance().getAllUsers((allUsers)-> {
            User newUser = Model.instance().getUserByEmail(allUsers, email);
            if (newUser.getImg()!= null && !newUser.getImg().equals("")) {

                Picasso.get().load(newUser.getImg()).placeholder(R.drawable.addpic).into(binding.editUserAvatarImg);
            }else{
                binding.editUserAvatarImg.setImageResource(R.drawable.addpic);
            }
        });


        binding.editUserSaveBtn.setOnClickListener((view -> {
            binding.editUserProgressBar.setVisibility(View.VISIBLE);

           String name= binding.editUserNameInputEt.getText().toString();
           String city= binding.editUserCityInputEt.getText().toString();


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
                          binding.editUserProgressBar.setVisibility(View.GONE);
                          Navigation.findNavController(view).popBackStack();

                      });
                  });
              }else {
                  Model.instance().addUser(newUser,()->{
                      binding.editUserProgressBar.setVisibility(View.GONE);
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

        binding.editUserCancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());



        return binding.getRoot();

    }


}