package com.example.sportapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
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
    Button logInBtn,signUpBtn;
    Spinner sportSpinner;
    private NavDirections action;
    String[] type=Model.instance().getType();
    String sport;
    ProgressBar pb;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Sign Up");
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        firebaseAuth= FirebaseAuth.getInstance();
        logInBtn= binding.getRoot().findViewById(R.id.SUfrag_SI_btn);
        pb=binding.getRoot().findViewById(R.id.signUp_progressBar);
        pb.setVisibility(View.GONE);
        signUpBtn= binding.getRoot().findViewById(R.id.SUfrag_SU_btn);
        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,type);
        sportSpinner=binding.getRoot().findViewById(R.id.SUFrag_sport_spinner);
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

        signUpBtn.setOnClickListener((view)->{
            pb.setVisibility(View.VISIBLE);
            String name= binding.SUFragNameInputEt.getText().toString();
            String email= binding.SUFragEmailInputEt.getText().toString();
            String pass= binding.SUFragPassInputEt.getText().toString();
            String city= binding.SUFragCitysInputEt.getText().toString();
            if(sport.equals(null))
                sport="not chosen";
            if(!(pass.equals("")) && !(email.equals("")) && !(name.equals("")) && !(city.equals("")) ){
                Model.instance().addUser(new User(name,email,city,sport,""),()->{
                   firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           Intent i = new Intent(getActivity(), HomeActivity.class);
                           i.putExtra("userEmail",email);
                           pb.setVisibility(View.GONE);
                           startActivity(i);
                       }

                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           pb.setVisibility(View.GONE);
                           builder.setMessage(e+"").setTitle("Error");
                           AlertDialog dialog = builder.create();
                           dialog.show();
                       }
                   });

                });
            }else{
                builder.setMessage("please fill all the fields").setTitle("Error");
                AlertDialog dialog = builder.create();
                dialog.show();
            }


        });

        logInBtn.setOnClickListener((view)->{
            action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
            Navigation.findNavController(view).navigate(action);

        });

        return binding.getRoot();
    }

}

