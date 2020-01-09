package com.example.voiceprescription;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {

    TextView name;
    Button new_prescription,prescription_history,patient_history,about_me;
    ImageView my_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //intializing all the variables with their respective components
        new_prescription = findViewById(R.id.newPrescrpt);
        prescription_history = findViewById(R.id.Hist);
        patient_history = findViewById(R.id.PatientInfo);
        about_me = findViewById(R.id.abtme);
        name = findViewById(R.id.DocName);

        //Setting onclick listeners for each of the buttons

        new_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this,NewPrescription.class);
                startActivity(intent);
            }
        });

        prescription_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this,PrescriptHistory.class);
                startActivity(intent);

            }
        });

        patient_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this,PatientHistory.class);
                startActivity(intent);
            }
        });

        about_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this,AboutMe.class);
                startActivity(intent);
            }
        });
    }
}
