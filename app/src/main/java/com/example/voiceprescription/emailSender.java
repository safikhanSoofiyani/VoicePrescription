package com.example.voiceprescription;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;


import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class emailSender extends AppCompatActivity {
    String EmailAddress,Message;
    EditText emailT;
    Button sendbtn;
    String filename;
    String Sname;
    String date,Subject,Body;
    private static final int STORAGE_CODE = 1000;
    public static final String TAG = "EmailSender";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sender);

        Intent intent = getIntent();
        filename = intent.getStringExtra("message");
        Sname = intent.getStringExtra("name");

        emailT = findViewById(R.id.email);
        sendbtn = findViewById(R.id.send);

        date = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(System.currentTimeMillis());
        Subject = "Prescription dated :"+date;
        Body = "Respected Sir/Madam\nPlease find the prescription attached.\nYour visit was on date: "+date+"."
            +"\n\nThank You";



        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //first creating a pdf document.
                Log.d(TAG,"Button clicked");

                //creating the pdf file here
                //filename = Sname + date+".pdf";


                //Log.d(TAG,"Made the filename");
                //checkpdfpermission();
                //Log.d(TAG,"Came back from checkpdfpermission");
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),filename);
                Log.d(TAG,"Made the file object");
                Uri path = Uri.fromFile(filelocation);
                Log.d(TAG,"Made the Uri object");
                String[] email = new String[1];
                EmailAddress = emailT.getText().toString();
                email[0] = EmailAddress;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Log.d(TAG,"Made the intent");
                intent.setData(Uri.parse("mailto:"));
                Log.d(TAG,"Made the intent 2");
                intent.putExtra(Intent.EXTRA_EMAIL,email);
                Log.d(TAG,"Made the intent 3");
                intent.putExtra(Intent.EXTRA_STREAM,path);
                Log.d(TAG,"Made the intent 4");
                intent.putExtra(Intent.EXTRA_SUBJECT,Subject);
                Log.d(TAG,"Made the intent 5");
                intent.putExtra(Intent.EXTRA_TEXT,Body);
                Log.d(TAG,"Made the intent 6");
                startActivity(intent);
                Toast.makeText(emailSender.this, "Redirecting", Toast.LENGTH_SHORT).show();
                finish();



            }
        });
    }

    /*public void checkpdfpermission()
    {
        Log.d(TAG,"Entered checkpdfpermisssion");
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            //System OS >= Marshmellow
            //Check whether permission is enabled or not

            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                //Permission is not granted
                //requesting permission

                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, STORAGE_CODE);


            }
            else {
                //Permission already granted
                Log.d(TAG,"First else");
                savePdf();

            }
        }
        else{
            //system OS < Marshmellow
            //not required to check runtime permission
            Log.d(TAG,"Second else");
            savePdf();
        }


    }
    private void savePdf() {
        //Creating object of document class
        Log.d(TAG,"Entered savePdf");
        Document mDoc = new Document();

        //pdf file path
        String mFilePath = Environment.getExternalStorageDirectory() + "/" + filename;
        Log.d(TAG,"Got the filepath");

        try{
            //Using pdfWriter class
            PdfWriter pdfWriter;
            pdfWriter = PdfWriter.getInstance(mDoc,new FileOutputStream(mFilePath));
            Log.d(TAG,"Got the pdwriter object");
            //opening the document
            mDoc.open();
            Log.d(TAG,"opened the document");
            //writing text from edit Text
            mDoc.addAuthor("Safi Khan");//actually doctors name
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            Log.d(TAG,"Got the object of XMLWORkER");
            worker.parseXHtml(pdfWriter,mDoc, new StringReader(Message));
            Log.d(TAG,"successfully wrote in the pdf");

            mDoc.close();
            Log.d(TAG,"Successfully closed the file");
            //Telling that file is saved
            Toast.makeText(this, filename+"\n is saved to \n"+mFilePath, Toast.LENGTH_LONG).show();



        }catch(Exception e)
        {
            Toast.makeText(emailSender.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode){

            case STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission was granted from popup
                    //calling savepdf
                    savePdf();

                }

                else{
                    //permission was denied from popup
                    //show error message
                    Toast.makeText(emailSender.this,"Permission was denied",Toast.LENGTH_LONG).show();


                }
            }
        }
    }*/


}
