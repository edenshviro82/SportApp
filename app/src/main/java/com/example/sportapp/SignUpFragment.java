package com.example.sportapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.example.sportapp.databinding.FragmentSignUpBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.app.AppCompatActivity;


public class SignUpFragment extends Fragment  {

    FirebaseAuth firebaseAuth;
    FragmentSignUpBinding binding;
    private NavDirections action;
    SignUpFragmentViewModel viewModel;
    String sport;
    User user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(SignUpFragmentViewModel.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Setting the title of the AppCompatActivity to "Sign Up"
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Sign Up");
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        firebaseAuth= FirebaseAuth.getInstance();
        // Hiding progress bar initially
        binding.signUpProgressBar.setVisibility(View.GONE);

        // Creating an AlertDialog with a positive button that dismisses the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Creating an ArrayAdapter for the spinner using type array
        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,viewModel.getType());
        // Setting the onItemSelectedListener for the spinner to update sport string
        binding.SUFragSportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sport=viewModel.getType()[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Setting the adapter for the spinner
        binding.SUFragSportSpinner.setAdapter(adapter);

        // Setting the onClickListener for the sign up button
        binding.SUfragSUBtn.setOnClickListener((view)->{
            // Showing the progress bar
            binding.signUpProgressBar.setVisibility(View.VISIBLE);
            // Getting the values from the EditTexts
            String name= binding.SUFragNameInputEt.getText().toString();
            String email= binding.SUFragEmailInputEt.getText().toString();
            String pass= binding.SUFragPassInputEt.getText().toString();
            String city= binding.SUFragCitysInputEt.getText().toString();
            // If sport string is not set, setting it to "not chosen"
            if(sport.equals(null))
                sport="not chosen";
            // Checking if all the fields are filled
            if(!(pass.equals("")) && !(email.equals("")) && !(name.equals("")) && !(city.equals("")) ){
                // Adding the user to the Model

                   firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           user=new User(name,email,city,sport,"");
                           Model.instance().addUser(user,()->{
                               Intent i = new Intent(getActivity(), HomeActivity.class);
                               i.putExtra("userEmail",email);
                               binding.signUpProgressBar.setVisibility(View.GONE);
                               startActivity(i);
                           });
                       }

                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           binding.signUpProgressBar.setVisibility(View.GONE);
                           builder.setMessage(e.getMessage()).setTitle("Error");
                           AlertDialog dialog = builder.create();
                           dialog.show();
                       }
                   });


            }else{
                builder.setMessage("please fill all the fields").setTitle("Error");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        binding.SUfragSIBtn.setOnClickListener((view)->{
            action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
            Navigation.findNavController(view).navigate(action);
        });

        return binding.getRoot();
    }

}

