package com.example.sportapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.sportapp.model.Model;
import com.example.sportapp.model.Review;
import com.squareup.picasso.Picasso;
import java.util.LinkedList;
import java.util.List;

public class EditMyReviewFragment extends Fragment {


    int pos;
    Button cancel,save,delete;
    EditText cityET, descriptionET;
    Review re;
    Spinner sportSpinner;
    List<Review> myData= new LinkedList<>();
    ImageView avatarImg;
    String email;
    String sport;
    EditMyReviewFragmentViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditMyReviewFragmentViewModel.class);
    }


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Your Review");
        View view = inflater.inflate(R.layout.fragment_edit_my_review, container, false);
        pos = EditMyReviewFragmentArgs.fromBundle(getArguments()).getPos();
        email = EditMyReviewFragmentArgs.fromBundle(getArguments()).getUserEmail();
        cityET = view.findViewById(R.id.editMyReview_Pt_city);
        sportSpinner = view.findViewById(R.id.editMyReview_sport_spinner);
        descriptionET=view.findViewById(R.id.editMyReview_Pt_description);
        save=view.findViewById(R.id.editMyReview_save_btn);
        cancel=view.findViewById(R.id.editMyReview_cancel_btn);
        delete=view.findViewById(R.id.editMyReview_delete_btn);
        avatarImg = view.findViewById(R.id.editMyReview_iv);

        ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.drop_down_item,viewModel.getType());
        sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),viewModel.getType()[i],Toast.LENGTH_LONG).show();
                sport = viewModel.getType()[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sportSpinner.setAdapter(adapter);

        save.setOnClickListener(view1 -> {
            bindBack(pos);
             Model.instance().addReview(viewModel.getMyData(email).get(pos),()->{
                 Navigation.findNavController(view).popBackStack();
             });
        });

        cancel.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).popBackStack();
        });


        return view;
    }
    private void bind(Review re) {
        cityET.setText(re.getCity());
        descriptionET.setText(re.getDescription());
        if (re.getImg()  != null) {
            Picasso.get().load(re.getImg()).placeholder(R.drawable.addpic).into(avatarImg);
        }else{
            avatarImg.setImageResource(R.drawable.addpic);
        }
    }

    private void bindBack( int pos) {
        myData.get(pos).setCity(cityET.getText().toString());
        myData.get(pos).setSport(sport);
        myData.get(pos).setDescription(descriptionET.getText().toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        bind(viewModel.getMyData(email).get(pos));
    }




}
