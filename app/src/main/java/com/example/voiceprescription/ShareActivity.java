package com.example.voiceprescription;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.StringTokenizer;

public class ShareActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 10;
    Button smsbtn,emailbtn,pdfbtn;
    String Sname,Sage,Ssex,Ssymptoms,Sdiagnosis,Sprescription,Sremarks;
    String SmsMessage,PdfMessage;
    String FinalphoneNumber,FinalemailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();
        String message = intent.getStringExtra("finalmessage");

        smsbtn = findViewById(R.id.sms);
        emailbtn = findViewById(R.id.email);
        pdfbtn = findViewById(R.id.dontshare);

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


        //making the message for sms
        SmsMessage = "Dr. Safi Khan"+"\n\n"+"Name:   "+Sname+"\nAge:  "+Sage+"\nSex:  "+Ssex+"\nSymptoms:  "+Ssymptoms
                +"\n\nDiagnosis:  "+Sdiagnosis+"\n\nRemarks:  "+Sremarks+"\n\n\nDr. Safi Khan";

        //making the message for pdf
        PdfMessage = "<h2>Dr. Safi Khan's</h2> <br> <br> <br> <b>Prescription No. :</b>   "+"Prescription number"+
                "<br> <b>Patient Name :</b>   "+Sname+"<br> <b>Age :</b>   "+Sage+"    <b>Sex :</b>"  + Ssex+
                "<br> <b>Symptoms : </b><br> <p> "+Ssymptoms+"</p><br> <b>Diagnosis :</b><br> <p>"+ Sdiagnosis+
                "</p><br><b>Prescription :</b><br><p> "+Sprescription +"</p><br><b>Remarks :</b><br> "+Sremarks
                + "<br><br><br><h5>Dr. Safi Khan</h5>";

        //setting the button onClickListeners

        smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sending sms
                //for taking phone number creating a dialog box
                Toast.makeText(ShareActivity.this, ""+ SmsMessage, Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(ShareActivity.this,SmsSender.class);
                //intent.putExtra("message",SmsMessage);
                //startActivity(intent);
                //sendSMS();


            }
        });

        emailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }




}
