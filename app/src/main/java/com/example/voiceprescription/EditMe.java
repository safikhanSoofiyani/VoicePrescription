package com.example.voiceprescription;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.StringTokenizer;

public class EditMe extends AppCompatActivity {

    EditText NameDoc,NameClinic,Speciality,Address,Mobile,Email;
    Button Savebtn;
    String SnameDoc,SnameClinic,Sspeciality,Saddress,Smobile,Semail;
    String strtemp;
    private static final String filename = "AboutMe.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_me);

        //initializing
        NameDoc = findViewById(R.id.namedoc);
        NameClinic = findViewById(R.id.clinicname);
        Speciality = findViewById(R.id.speciality);
        Address = findViewById(R.id.address);
        Mobile = findViewById(R.id.mobile);
        Email = findViewById(R.id.emailaddress);
        Savebtn = findViewById(R.id.save);

        //getting intent
        Intent intent = getIntent();
        strtemp = intent.getStringExtra("message");

        //putting the text into the respective editTexts
        StringTokenizer str = new StringTokenizer(strtemp,":");
        SnameDoc = str.nextToken();
        Sspeciality = str.nextToken();
        SnameClinic = str.nextToken();
        Saddress = str.nextToken();
        Smobile = str.nextToken();
        Semail = str.nextToken();

        NameDoc.setText(SnameDoc);
        NameClinic.setText(SnameClinic);
        Speciality.setText(Sspeciality);
        Mobile.setText(Smobile);
        Email.setText(Semail);
        Address.setText(Saddress);


        Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SnameDoc = NameDoc.getText().toString();
                Sspeciality = Speciality.getText().toString();
                SnameClinic = NameClinic.getText().toString();
                Saddress = Address.getText().toString();
                Smobile = Mobile.getText().toString();
                Semail = Email.getText().toString();
                String Sfinal = SnameDoc+":"+Sspeciality+":"+SnameClinic+":"+Saddress+":"+Smobile+":"+Semail;
                FileOutputStream out = null;
                try{
                    out = openFileOutput(filename, Context.MODE_PRIVATE);
                    out.write(Sfinal.getBytes());
                    out.close();
                }catch (FileNotFoundException f)
                {
                    Toast.makeText(EditMe.this, ""+f.getMessage(), Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    Toast.makeText(EditMe.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(EditMe.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}
