package com.example.voiceprescription;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    Button btn;
    private static final String filename = "AboutMe.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //In this activity im thinking of incorporating login activity.
        //For the time being there is just a button
        //But if the rest of the things pan out well then
        //ill go ahead with the login activity.

        //checking whether file present or not
        if(fileExist(filename))
        {
            //file is present
        }
        else
        {
            //file not present so create it
            FileOutputStream out =null;
            String Sfinal = "Dr.Lorem Ipsum"+":"+"Lorem Ipsum Dolor"+":"+"Lorem Ipsum Dolor"+":"+"Lorem Ipsum Dolor"+":"+"Lorem Ipsum Dolor"+":"+"Lorem Ipsum Dolor";
            try {
                out = openFileOutput(filename, Context.MODE_PRIVATE);
                out.write(Sfinal.getBytes());
                out.close();
            }catch (FileNotFoundException f)
            {
                Toast.makeText(this, ""+f.getMessage(), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }





        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , MainPage.class);
                startActivity(intent);
            }
        });


    }

    public boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}
