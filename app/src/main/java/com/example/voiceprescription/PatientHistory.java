package com.example.voiceprescription;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class PatientHistory extends AppCompatActivity {
    TextView textView;
    ArrayList<String> NameList, DateList, TimeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);

        textView = findViewById(R.id.tv);
        NameList = new ArrayList<>();
        DateList = new ArrayList<>();
        TimeList = new ArrayList<>();

        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT PATIENTNAME,PDATE,PTIME FROM PATIENT", new String[]{});
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    String name = cursor.getString(0);
                    String date = cursor.getString(1);
                    String time = cursor.getString(2);
                    NameList.add(name);
                    DateList.add(date);
                    TimeList.add(time);
                } while (cursor.moveToNext());

            }
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if(NameList.isEmpty())
        {
            //List is empty
            //Display nothing to show
            textView.setText("Still waiting for the first patient");
        }
        else
        {
            String finalString = "";
            for(int i=0;i<NameList.size();i++){
                String sno = String.valueOf((i+1));
                finalString = finalString + sno +".  "+ NameList.get(i) + " visited you on "+DateList.get(i)+" at time "+TimeList.get(i)+"\n\n";
            }
            textView.setText(finalString);
        }
    }
}
