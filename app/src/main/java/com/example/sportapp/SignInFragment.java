package com.example.sportapp;

import android.app.AlertDialog;
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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sportapp.databinding.FragmentSignInBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;
import android.app.AlertDialog;
import android.content.DialogInterface;




public class SignInFragment extends Fragment {
    FragmentSignInBinding binding;
    Button SignInBtn;
    private NavDirections action;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.signInFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignInBinding.inflate(inflater, container, false);

        SignInBtn = binding.getRoot().findViewById(R.id.SIfrag_SU_btn);



        SignInBtn.setOnClickListener((view) -> {

            String email=binding.SIFragEmailInputEt.getText().toString();
            String password=binding.SIFragPassInputEt.getText().toString();

            //dialog alert************
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Dismiss the dialog
                    dialog.dismiss();
                }
            });
            //dialog alert************

            if(Model.instance().getAllUsers().containsKey(email)) {
                if (password.equals(Model.instance().getAllUsers().get(email).getPassword())) {
//                   Intent i = new Intent(getActivity(), HomeActivity.class);
//                   i.putExtra("userEmail",email);
//                   startActivity(i);

                }
                else {

                    builder.setMessage("Incorrect password. Please try again.").setTitle("Error");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
            else{
                builder.setMessage("Mail do not Exist. Please try again.").setTitle("Error");
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });


        return binding.getRoot();
    }
}