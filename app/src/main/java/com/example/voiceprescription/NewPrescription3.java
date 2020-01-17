package com.example.voiceprescription;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.StringTokenizer;

public class NewPrescription3 extends AppCompatActivity {

    private static final String TAG = "NewPrescription3";
    TextView document;
    Button backbtn,confirmbtn;
    String Sname,Sage,Ssex,Ssymptoms,Sdiagnosis,Sprescription,Sremarks;
    String TextDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prescription3);
        Intent intent = getIntent();
        String message = intent.getStringExtra("tempdata2");
        //Toast.makeText(this, ""+message, Toast.LENGTH_LONG).show();



        //intializing the buttons
        document = findViewById(R.id.finaldocument);
        backbtn = findViewById(R.id.back);
        confirmbtn = findViewById(R.id.confirm);


        //printing the document
        Log.d("NewPrescription2","Starting StringTokenizer");
        StringTokenizer str = new StringTokenizer(message,":");
        Sname = str.nextToken();
        Sage = str.nextToken();
        Ssex = str.nextToken();
        Ssymptoms = str.nextToken();
        Sdiagnosis = str.nextToken();
        Sprescription = str.nextToken();
        Sremarks = str.nextToken();
        Log.d("NewPrescription2","End StringTokenizer");


        TextDisplayed = "<h2>Dr. Safi Khan's</h2> <h2> </h2> <b>Prescription No. :</b> "+"<p>Prescription number</p>"+
                "<b>Patient Name :</b> <p>"+Sname+"</p> <b>Age :</b>   <p>"+Sage+"</p>    <b>Sex :</b><p>"  + Ssex+
                "</p> <b>Symptoms : </b> <p> "+Ssymptoms+"</p> <b>Diagnosis :</b> <p>"+ Sdiagnosis+
                "</p> <b>Prescription :</b> <p> "+Sprescription +"</p><b>Remarks :</b><p>"+Sremarks
                + "</p><h2></h2><h5>Dr. Safi Khan</h5>";
        document.setText(Html.fromHtml(TextDisplayed));


        //checking whether directory is present or not
        File dir = new File(Environment.getExternalStorageDirectory()+"/Prescriptions");
        if(dir.exists() && dir.isDirectory())
        {
            //directory already exists
            //dont do anyhting
        }
        else
        {
            //creating a new directory because the original directory is not present
            try{
                if(dir.mkdir()){
                    Log.d(TAG,"Directory created");
                }
                else{
                    Log.d(TAG,"Directory not created");
                }
            }catch (Exception e)
            {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }








        //setting event handlers

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewPrescription3.this,NewPrescription2.class);
                startActivity(intent);
            }
        });

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String final_message;
                final_message = Sname+":"+Sage+":"+Ssex+":"+Ssymptoms+":"+Sdiagnosis+":"+Sprescription+":"+Sremarks;
                Intent intent1 = new Intent(NewPrescription3.this,ShareActivity.class);
                intent1.putExtra("finalmessage",final_message);
                startActivity(intent1);
            }
        });
    }
}
