package com.example.sportapp;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

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
import com.example.sportapp.databinding.FragmentNewReviewBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;


public class EditUserFragment extends Fragment {
    FragmentEditUserBinding binding;
    Button SignInBtn;
    private NavDirections action;
    String email;
    Button saveBtn,cancelBtn;
    Spinner sportSpinner;
    String sport;
    String[] type={"running","swimming"};



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
                menu.removeItem(R.id.editUserFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditUserBinding.inflate(inflater, container, false);
        saveBtn=   binding.getRoot().findViewById(R.id.edit_user_save_btn);
        cancelBtn= binding.getRoot().findViewById(R.id.edit_user_cancel_btn);
        email = EditUserFragmentArgs.fromBundle(getArguments()).getUserEmail();
        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,type);
        sportSpinner=binding.getRoot().findViewById(R.id.edit_user_sport_spinner);
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

           String name= binding.editUserNameInputEt.getText().toString();
           String city= binding.editUserCityInputEt.getText().toString();

           Model.instance().printUser(email);


           User newUser= Model.instance().getAllUsers().get(email);
         if(!city.equals(""))
            newUser.setCity(city);

         if(!name.equals(""))
            newUser.setName(name);


         newUser.setSport(sport);

         Model.instance().getAllUsers().put(email,newUser);
         Model.instance().printUser(email);
         Navigation.findNavController(view).popBackStack();


       }));

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());



        return binding.getRoot();

    }


}