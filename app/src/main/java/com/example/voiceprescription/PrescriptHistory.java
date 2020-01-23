package com.example.voiceprescription;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

public class PrescriptHistory extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    Button fetchbtn;

    ArrayList<String> myList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescript_history);

        //to resolve the errors getting while opening the fil via URI.
        //It enables the VM to ignore the file URI exposure
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        searchView = findViewById(R.id.searchview);
        listView = findViewById(R.id.listview);
        fetchbtn = findViewById(R.id.fetch);
        myList = new ArrayList<String>();

        //opening the database and reading from it

        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT FILENAME FROM PRESCRIPTION", new String[]{});
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    String temp = cursor.getString(0);
                    myList.add(temp);
                } while (cursor.moveToNext());

                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
                listView.setAdapter(adapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        adapter.getFilter().filter(s);
                        return false;
                    }
                });


            } else {
                Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        fetchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String filename = searchView.getQuery().toString();
                    if(filename.isEmpty()){
                        Toast.makeText(PrescriptHistory.this, "Please select a proper file", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(PrescriptHistory.this, "" + filename, Toast.LENGTH_LONG).show();
                        //String path = Environment.getExternalStorageDirectory()+"/Prescriptions/";
                        File file = new File(Environment.getExternalStorageDirectory() + "/Prescriptions/" + filename);
                        Uri path = Uri.fromFile(file);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Log.d("Prescript History", "Created intent");
                        intent.setDataAndType(path, "application/pdf");
                        Log.d("Prescript History", "Created intent 2");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Log.d("Prescript History", "Created intent 3");
                        startActivity(intent);
                        Log.d("Prescript History", "Returned from pdf");
                    }

                }catch (Exception e)
                {
                    Toast.makeText(PrescriptHistory.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_item = listView.getItemAtPosition(i).toString();
                //Toast.makeText(PrescriptHistory.this, ""+selected_item, Toast.LENGTH_LONG).show();
                searchView.setQuery(selected_item,false);
            }
        });




    }
}

