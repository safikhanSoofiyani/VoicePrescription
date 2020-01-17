package com.example.voiceprescription;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.StringTokenizer;

public class AboutMe extends AppCompatActivity {

    private static final String TAG = "AboutMe";
    TextView NameDoctor,Speciality,NameClinic,Address,Mobile,Email;
    ImageButton EditBtn;
    String SnameDoctor,Sspeciality,SnameClinic,Saddress,Smobile,Semail;
    String strtemp;
    private static final String filename = "AboutMe.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        NameDoctor = findViewById(R.id.namedoc);
        NameClinic = findViewById(R.id.clinicname);
        Speciality = findViewById(R.id.speciality);
        Address = findViewById(R.id.address);
        Mobile = findViewById(R.id.mobile);
        Email = findViewById(R.id.emailaddress);
        EditBtn = findViewById(R.id.edit);


        File file = null;
        BufferedReader br = null;
        file = new File(getFilesDir(),filename);
        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            strtemp = br.readLine();
            StringTokenizer str = new StringTokenizer(strtemp,":");
            SnameDoctor = str.nextToken();
            Sspeciality = str.nextToken();
            SnameClinic = str.nextToken();
            Saddress = str.nextToken();
            Smobile = str.nextToken();
            Semail = str.nextToken();


        }catch(FileNotFoundException f)
        {
            Toast.makeText(this, ""+f.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        NameDoctor.setText(SnameDoctor);
        NameClinic.setText(SnameClinic);
        Speciality.setText(Sspeciality);
        Mobile.setText(Smobile);
        Email.setText(Semail);
        Address.setText(Saddress);

        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutMe.this,EditMe.class);
                intent.putExtra("message",strtemp);
                startActivity(intent);
                finish();
            }
        });



    }
}
