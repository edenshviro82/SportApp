package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {


    EditText namePt, idPt, phonePt, cityPt;
    Button cancel,save;
    Intent menusIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        namePt = (EditText)findViewById(R.id.SignUpAct_name_pt);
        idPt = findViewById(R.id.SignUpAct_id_pt);
        phonePt=findViewById(R.id.SignUpAct_phone_pt);
        cityPt=findViewById(R.id.SignUpAct_city_pt);
        cancel=findViewById(R.id.SignUpAct_cancel_btn);
        save=findViewById(R.id.SignUpAct_save_btn);


        cancel.setOnClickListener(view -> finish());
        save.setOnClickListener((view)->{
            finish();
        });
    }
}