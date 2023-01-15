package com.example.sportapp;

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

import com.example.sportapp.databinding.FragmentAddReviewBinding;
import com.example.sportapp.databinding.FragmentNewReviewBinding;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;


public class AddReviewFragment extends Fragment {
    int id=0;
    FragmentAddReviewBinding binding;
    Button saveBtn,cancelBtn;
    Spinner sportSpinner;
    String sport;
    String email;
    String[] type=Model.instance().getType();

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
            id++;


            Model.instance().getAllReviews((allReviews)->{
                id=allReviews.size()+1;
            });
            Review newR = new Review(id, email, description, city, sport, "");
            Log.d("id","id: "+id);
            // Model.instance().getAllReviews().add(newR);
            Model.instance().addReview(newR,(data)->{
                Navigation.findNavController(view).popBackStack();
            });



        }));

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());


        return binding.getRoot();
    }
}