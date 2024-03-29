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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.sportapp.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;


public class SignInFragment extends Fragment {
    FragmentSignInBinding binding;
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Sign In");
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        firebaseAuth= FirebaseAuth.getInstance();
        binding.signInProgressBar.setVisibility(View.GONE);

        //pop up dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Dismiss the dialog
                dialog.dismiss();
            }            });


        binding.SIfragSUBtn.setOnClickListener((view) -> {

            binding.signInProgressBar.setVisibility(View.VISIBLE);
            String email=binding.SIFragEmailInputEt.getText().toString();
            String password=binding.SIFragPassInputEt.getText().toString();
            if((!(password.equals("")) && !(email.equals(""))))
            {
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        i.putExtra("userEmail",email);
                        binding.signInProgressBar.setVisibility(View.GONE);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.signInProgressBar.setVisibility(View.GONE);
                        //shoe the dialog
                        builder.setMessage(e.getMessage()).setTitle("Error");
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
        });
        return binding.getRoot();
    }
}