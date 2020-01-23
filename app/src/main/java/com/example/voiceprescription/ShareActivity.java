package com.example.voiceprescription;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.channels.InterruptedByTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

public class ShareActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 10;
    private static final String TAG = "Share Activity";
    private static final int STORAGE_CODE = 1000;
    Button smsbtn,emailbtn,dontbtn;
    String Sname,Sage,Ssex,Ssymptoms,Sdiagnosis,Sprescription,Sremarks;
    String SmsMessage,PdfMessage;
    String FinalphoneNumber,FinalemailAddress;
    String filename;
    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        final Intent intent = getIntent();
        String message = intent.getStringExtra("finalmessage");

        smsbtn = findViewById(R.id.sms);
        emailbtn = findViewById(R.id.email);
        dontbtn = findViewById(R.id.dontshare);

        //getting the doctors name
        try {
            File file = new File(getFilesDir(), "AboutMe.txt");
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            line = input.readLine();
            StringTokenizer str1 = new StringTokenizer(line, ":");
            Name = str1.nextToken();
        }catch(Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }

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
        /*SmsMessage = "Dr. Safi Khan"+"\n\n"+"Name:   "+Sname+"\nAge:  "+Sage+"\nSex:  "+Ssex+"\nSymptoms:  "+Ssymptoms
                +"\n\nDiagnosis:  "+Sdiagnosis+"\n\nRemarks:  "+Sremarks+"\n\n\nDr. Safi Khan";*/
        SmsMessage = Name+"\n\n"+"Name:   "+Sname+"\nAge:  "+Sage+"\nSex:  "+Ssex+"\nSymptoms:  "+Ssymptoms
                +"\n\nDiagnosis:  "+Sdiagnosis+"\n\nRemarks:  "+Sremarks+"\n\n\n"+Name;

        //making the message for pdf
        /*PdfMessage = "<h1>Dr. Safi Khan's</h1> <h2> </h2> <b>Prescription No. :</b> "+"<p>Prescription number</p>"+
                "<b>Patient Name :</b> <p>"+Sname+"</p> <b>Age :</b>   <p>"+Sage+"</p>    <b>Sex :</b><p>"  + Ssex+
                "</p> <b>Symptoms : </b> <p> "+Ssymptoms+"</p> <b>Diagnosis :</b> <p>"+ Sdiagnosis+
                "</p> <b>Prescription :</b> <p> "+Sprescription +"</p><b>Remarks :</b><p>"+Sremarks
                + "</p><h2></h2><h5>Dr. Safi Khan</h5>";*/
        PdfMessage = "<h1>"+Name+"</h1> <h2> </h2> <b>Prescription No. :</b> "+"<p>Prescription number</p>"+
                "<b>Patient Name :</b> <p>"+Sname+"</p> <b>Age :</b>   <p>"+Sage+"</p>    <b>Sex :</b><p>"  + Ssex+
                "</p> <b>Symptoms : </b> <p> "+Ssymptoms+"</p> <b>Diagnosis :</b> <p>"+ Sdiagnosis+
                "</p> <b>Prescription :</b> <p> "+Sprescription +"</p><b>Remarks :</b><p>"+Sremarks
                + "</p><h2></h2><h5>"+Name+"</h5>";




        String date = new SimpleDateFormat("dd-MM-yyyy--HH:mm",
                Locale.getDefault()).format(System.currentTimeMillis());
        filename = Sname + date+".pdf";
        checkpdfpermission();

        String daten = new SimpleDateFormat("dd-MM-yyyy/HH:mm",
                Locale.getDefault()).format(System.currentTimeMillis());
        StringTokenizer stringTokenizer = new StringTokenizer(daten,"/");
        String justDate = stringTokenizer.nextToken();
        String justTime = stringTokenizer.nextToken();

        //inserting the pdf path in the database
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        helper.insertPrescript(Sname,filename,database);

        helper.insertPatient(Sname,justDate,justTime,database);


        //setting the button onClickListeners

        smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sending sms
                //for taking phone number creating a dialog box
                //Toast.makeText(ShareActivity.this, ""+ SmsMessage, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShareActivity.this,SmsSender.class);
                intent.putExtra("message",SmsMessage);
                startActivity(intent);
                //sendSMS();


            }
        });

        emailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(ShareActivity.this,emailSender.class);
                intent1.putExtra("message",filename);
                intent1.putExtra("name",Sname);
                startActivity(intent1);

            }

        });

        dontbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    public void checkpdfpermission()
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
        String mFilePath = Environment.getExternalStorageDirectory() + "/Prescriptions/" + filename;
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



            mDoc.addAuthor(Name);//actually doctors name
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            Log.d(TAG,"Got the object of XMLWORkER");
            worker.parseXHtml(pdfWriter,mDoc, new StringReader(PdfMessage));
            Log.d(TAG,"successfully wrote in the pdf");

            mDoc.close();
            Log.d(TAG,"Successfully closed the file");
            //Telling that file is saved
            Toast.makeText(this, filename+"\n is saved to \n"+mFilePath, Toast.LENGTH_LONG).show();



        }catch(Exception e)
        {
            Toast.makeText(ShareActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ShareActivity.this,"Permission was denied",Toast.LENGTH_LONG).show();


                }
            }
        }
    }






}
