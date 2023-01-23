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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.LinkedList;
import java.util.List;


public class SignInFragment extends Fragment {
    FragmentSignInBinding binding;
    Button SignInBtn;
    private NavDirections action;
    User u;
    FirebaseAuth firebaseAuth;

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
        firebaseAuth= FirebaseAuth.getInstance();
        SignInBtn = binding.getRoot().findViewById(R.id.SIfrag_SU_btn);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Dismiss the dialog
                dialog.dismiss();
            }            });

        SignInBtn.setOnClickListener((view) -> {

            String email=binding.SIFragEmailInputEt.getText().toString();
            String password=binding.SIFragPassInputEt.getText().toString();
            if((!(password.equals("")) && !(email.equals(""))))
            {
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        i.putExtra("userEmail",email);
                        startActivity(i);
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        builder.setMessage(e+"").setTitle("Error");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

            }
            else {
                builder.setMessage("please fill all the fields").setTitle("Error");
                AlertDialog dialog = builder.create();
                dialog.show();
            }


            //dialog alert************

            //u= new User();
//            Model.instance().getAllUsers((all)->{
//                u=Model.instance().getUserByEmail(all,email);
//            });
//
//            if(!u.getEmail().equals("")) {
//                if (password.equals(u.getPassword())) {
////                   Intent i = new Intent(getActivity(), HomeActivity.class);
////                   i.putExtra("userEmail",email);
////                   startActivity(i);
//
//                }
//                else {
//                    builder.setMessage("Incorrect password. Please try again.").setTitle("Error");
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            }
//            else{
//                builder.setMessage("Mail do not Exist. Please try again.").setTitle("Error");
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }

        });


        return binding.getRoot();
    }
}