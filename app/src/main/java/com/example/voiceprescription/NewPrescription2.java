package com.example.voiceprescription;

import android.content.Intent;
import android.media.Image;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class NewPrescription2 extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEAK_PRESCRIPTION = 100;
    private static final int REQUEST_CODE_SPEAK_REMARKS = 200;
    EditText prescription,remarks;
    ImageButton micPrescription,micRemarks;
    Button nextbtn,backbtn;
    String Sname,Sage,Ssex,Ssymptoms,Sdiagnosis,Sprescription,Sremarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prescription2);

        final Intent intent = getIntent();
        String message = intent.getStringExtra("tempdata");

        //implementing String Tokenizer
        Log.d("NewPrescription2","Starting StringTokenizer");
        try {
            StringTokenizer str = new StringTokenizer(message, ":");
            Sname = str.nextToken();
            Sage = str.nextToken();
            Ssex = str.nextToken();
            Ssymptoms = str.nextToken();
            Sdiagnosis = str.nextToken();
            Log.d("NewPrescription2", "End StringTokenizer");
        }catch (Exception e)
        {
            Toast.makeText(this, "Information not correct", Toast.LENGTH_SHORT).show();
        }
        prescription = findViewById(R.id.prescription);
        remarks = findViewById(R.id.remarks);
        micPrescription = findViewById(R.id.prescriptmic);
        micRemarks = findViewById(R.id.remarksmic);
        nextbtn = findViewById(R.id.next);
        backbtn = findViewById(R.id.back);

        //setting the onclick listeners for each button

        micPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakPrescription();
            }
        });

        micRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakRemarks();
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sprescription = prescription.getText().toString();
                Sremarks = remarks.getText().toString();
                String message2 = Sname+":"+Sage+":"+Ssex+":"+Ssymptoms+":"+Sdiagnosis+":"+Sprescription+":"+Sremarks;
                Intent intent = new Intent(NewPrescription2.this,NewPrescription3.class);
                intent.putExtra("tempdata2",message2);
                startActivity(intent);

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(NewPrescription2.this,NewPrescription.class);
                startActivity(intent2);
            }
        });


    }

    private void speakRemarks() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the prescription");
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5000);
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,5000);
        i.putExtra("android.speech.extra.DICTATION_MODE",true);
        //i.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,false);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_REMARKS);

        }
        catch(Exception e)
        {
            Toast.makeText(NewPrescription2.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void speakPrescription() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the remarks");
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5000);
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,5000);
        i.putExtra("android.speech.extra.DICTATION_MODE",true);
        //i.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,false);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_PRESCRIPTION);

        }
        catch(Exception e)
        {
            Toast.makeText(NewPrescription2.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch(requestCode){

            case REQUEST_CODE_SPEAK_PRESCRIPTION:{
                Log.d("NewPrescription2","Entering prescription onActivityResult");
                if(resultCode == RESULT_OK && null != data)
                {
                    ArrayList<String> resultPrescript = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    prescription.setText(resultPrescript.get(0));
                }
                break;
            }

            case REQUEST_CODE_SPEAK_REMARKS:{
                Log.d("NewPrescription2","Entering remarks onActivityREsult");
                if(resultCode == RESULT_OK && null != data)
                {
                    ArrayList<String> resultRemarks = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    remarks.setText(resultRemarks.get(0));
                }
                break;
            }
        }

    }
}
