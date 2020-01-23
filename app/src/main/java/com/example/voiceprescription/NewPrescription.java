package com.example.voiceprescription;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewPrescription extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEAK_NAME = 1;
    private static final int REQUEST_CODE_SPEAK_SYMPTOMS = 2;
    private static final int REQUEST_CODE_SPEAK_DIAGNOSIS = 3;
    private static final String Tag = "NewPrescription.this";
    TextView prescriptionNo;
    EditText name,age,symptoms,diagnosis;
    ImageButton nameMic,symptomsMic,diagnosisMic;
    Button nextbtn;
    Spinner sexList;

    String Ssex, Sname, Ssymptom,Sdiagnosis,Sage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prescription);

        //initializing all the variables with their respective components
        prescriptionNo = findViewById(R.id.prescrpt_no);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        symptoms = findViewById(R.id.symptoms);
        diagnosis = findViewById(R.id.diagnosis);
        nameMic = findViewById(R.id.mic_name);
        symptomsMic = findViewById(R.id.mic_symptoms);
        diagnosisMic = findViewById(R.id.mic_diagnosis);
        nextbtn = findViewById(R.id.next);
        sexList = findViewById(R.id.sex);

        //attaching onclick listeners to each
        nameMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakName();

            }
        });

        symptomsMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakSymptoms();

            }
        });

        diagnosisMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakDiagnosis();

            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //For the time being
                //Ssex="/:" ; Sname="/:"; Ssymptom="/:";Sdiagnosis="/:";Sage="/:";
                Log.d(Tag,"Entered In nextbtnOnclick 1");
                String toast;
                Sname = name.getText().toString();
                //Log.d(Tag,"Entered In nextbtnOnclick 2");
                Ssymptom = symptoms.getText().toString();
                Sdiagnosis = diagnosis.getText().toString();
                Sage = age.getText().toString();
                //Log.d(Tag,"Entered In nextbtnOnclick 3");
                toast = Sname+":"+Sage+":"+Ssex+":"+Ssymptom+":"+Sdiagnosis;
                //Log.d(Tag,"Entered In nextbtnOnclick 4");
                //Toast.makeText(NewPrescription.this, ""+toast, Toast.LENGTH_LONG).show();
                //if(Sname.equals("/:") || Sage.equals("/:") || Ssymptom.equals("/:") || Sdiagnosis.equals("/:") || Ssex.equals("/:")) {
                  //  Toast.makeText(NewPrescription.this, "Please enter all the fields", Toast.LENGTH_LONG).show();
                //}
                //else{

                    Intent intent = new Intent(NewPrescription.this, NewPrescription2.class);
                    //Log.d(Tag,"Entered In nextbtnOnclick 5");
                    intent.putExtra("tempdata", toast);
                    startActivity(intent);
                }
                //Log.d(Tag,"Entered In nextbtnOnclick 6");



            //}
        });

        //playing with the spinner object
        String[] items = new String[]{"Male","Female","Others","Prefer not to say"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,items);
        sexList.setAdapter(adapter);

        //initializing item selected listener
        sexList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("NewPrescription","Entered on item selected");
                switch (i)
                {
                    case 0:
                    {
                        Ssex = adapterView.getSelectedItem().toString();
                        //Ssex = "male";
                        break;
                    }

                    case 1:
                    {
                        Ssex = "Female";
                        break;
                    }

                    case 2:
                    {
                        Ssex = "Others";
                        break;
                    }

                    case 3:
                    {
                        Ssex = "Data not available";
                        break;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast.makeText(NewPrescription.this, "Please enter appropriate gender", Toast.LENGTH_LONG).show();

            }
        });
        //adapter.notifyDataSetChanged();


    }


    //method which calls the recognizer intent for name
    public void speakName()
    {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the name");
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5000);
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,5000);
        i.putExtra("android.speech.extra.DICTATION_MODE",true);
        //i.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,false);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_NAME);

        }
        catch(Exception e)
        {
            Toast.makeText(NewPrescription.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


    //method which calls the recognizer intent for symptoms
    public void speakSymptoms()
    {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the symptoms");
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5000);
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,5000);
        i.putExtra("android.speech.extra.DICTATION_MODE",true);
        //i.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,false);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_SYMPTOMS);

        }
        catch(Exception e)
        {
            Toast.makeText(NewPrescription.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }


    //method which calls the recognizer intent for diagnosis
    public void speakDiagnosis()
    {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Please say the diagnosis");
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5000);
        //i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,5000);
        i.putExtra("android.speech.extra.DICTATION_MODE",true);
        //i.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,false);

        try{
            startActivityForResult(i,REQUEST_CODE_SPEAK_DIAGNOSIS);

        }
        catch(Exception e)
        {
            Toast.makeText(NewPrescription.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }


    //handling the results of all the intents
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch(requestCode){

            case REQUEST_CODE_SPEAK_NAME:
            {
                Log.d("NewPrescription","REQUEST_CODE_SPEAK_NAME");
                //if request initiated by speakName
                if(resultCode == RESULT_OK && null != data)
                {
                    ArrayList<String> resultName = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    name.setText(resultName.get(0));
                }
                break;
            }

            case REQUEST_CODE_SPEAK_SYMPTOMS:
            {
                Log.d("NewPrescription","REQUEST_CODE_SPEAK_SYMPTOMS");
                //if request initiated by speakSymptoms
                if(resultCode == RESULT_OK && null != data)
                {
                    ArrayList<String> resultSymp = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    symptoms.setText(resultSymp.get(0));
                }
                break;
            }

            case REQUEST_CODE_SPEAK_DIAGNOSIS:
            {
                Log.d("NewPrescription","REQUEST_CODE_SPEAK_DIAGNOSIS");
                //if request initiated by speakDiagnosis
                if(resultCode == RESULT_OK && null != data)
                {
                    ArrayList<String> resultDiag = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    diagnosis.setText(resultDiag.get(0));
                }
                break;
            }



        }
    }
}
