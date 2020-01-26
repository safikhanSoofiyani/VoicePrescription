package com.example.voiceprescription;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SmsSender extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final String TAG ="SmsSender" ;
    EditText phonenumber;
    Button sendbtn;
    String Message,phoneNumber;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_sender);

        Intent intent = getIntent();
        Message = intent.getStringExtra("message");

        phonenumber = findViewById(R.id.pno);
        sendbtn = findViewById(R.id.send);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Calling sendSMSMessage");
                checkSmsPermission();
                finish();
            }
        });

    }

    public void checkSmsPermission(){
        if(checkSelfPermission(Manifest.permission.SEND_SMS) ==
                PackageManager.PERMISSION_DENIED){
            //Permission is not granted
            //requesting permission

            String[] permissions = {Manifest.permission.SEND_SMS};
            requestPermissions(permissions, MY_PERMISSIONS_REQUEST_SEND_SMS);


        }
        else {
            //Permission already granted
            Log.d(TAG,"First else");
            sendSms();

        }

    }

   /* protected void sendSMSMessage() {
        phoneno = pno.getText().toString();
        message = text.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }*/

    public void sendSms()
    {
        try {
            phoneNumber = phonenumber.getText().toString();
            //message = text.getText().toString();
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(Message);
            smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0  &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /*SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneno, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();*/
                    sendSms();
                } else {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }






}
