package com.example.voiceprescription;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainPage extends AppCompatActivity {

    private static final String TAG = "Main Page";
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

        //setting the doctors name
        BufferedReader input = null;
        File file = null;
        try{
            file = new File(getFilesDir(),"AboutMe.txt");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            line = input.readLine();
            StringTokenizer str = new StringTokenizer(line,":");
            String Name = str.nextToken();
            name.setText(Name);

        }catch(Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }



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

        //checking whether our directory is present or not.
        //if not present then creating our directory
        String path = Environment.getExternalStorageDirectory()+"/prescription";
        Log.d(TAG,"Got the directory address");

        File dir = new File(path);
        Log.d(TAG,"created file object 1");

        if(dir.exists() && dir.isDirectory()){
            //then directory is already present.
            //dont do anything
            Log.d(TAG,"directory exists");
        }
        else{
            //directory is not present.
            //creating a new directory.
            Log.d(TAG,"directory doesnt exist");
            try {
                String newPath = Environment.getExternalStorageDirectory() + "/prescription";
                Log.d(TAG,"Got the new directory address");
                File newdir = new File(newPath);
                Log.d(TAG,"created new file object");
                newdir.mkdirs();
                Log.d(TAG,"Created the new directory");
            }catch (Exception e)
            {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }
}
