package com.codingelab.tutorial;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {



        DBHelper mydb;

        Button bttnshow1;
        Button bttnshowall;
        Button bttnadd;
        Button last_row;
        Button delete;
        Button show_all;
        Button search;

        EditText editTextName;
        EditText editTextPhone;
        EditText editTextEmail;

        TextView last_name;
        TextView last_phone;
        TextView last_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

            ArrayAdapter<String> arrayAdapter;

            mydb = new DBHelper(this);

            editTextName = (EditText)findViewById(R.id.editName);
            editTextPhone = (EditText)findViewById(R.id.editPhone);
            editTextEmail = (EditText)findViewById(R.id.editEmail);

            bttnadd = (Button) findViewById(R.id.bttnAdd);
            bttnshow1 = (Button) findViewById(R.id.bttnShow1);
            bttnshowall = (Button) findViewById(R.id.bttnShowAll);
            delete = (Button) findViewById(R.id.delete);
            last_row = (Button) findViewById(R.id.last_row);
            show_all = (Button) findViewById(R.id.show_all);
            last_name = (TextView)findViewById(R.id.last_name);
            last_phone = (TextView)findViewById(R.id.last_phone);
            last_email = (TextView)findViewById(R.id.last_email);
            search = (Button) findViewById(R.id.search);

            bttnadd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //remove the following toast...
                    Toast.makeText(getApplicationContext(),
                            "bttnOnClick Pressed", Toast.LENGTH_SHORT).show();

                    String getName = editTextName.getText().toString();
                    String getPhone = editTextPhone.getText().toString();
                    String getEmail = editTextEmail.getText().toString();

                    if (mydb.insertContact(getName, getPhone, getEmail)) {
                        Log.v("georgeLog", "Successfully inserted record to db");
                        Toast.makeText(getApplicationContext(),
                                "Inserted:" + getName + ", " + getPhone + "," + getEmail, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "DID NOT insert to db :-(", Toast.LENGTH_SHORT).show();
                }
            });

            bttnshow1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.v("georgeLog", "clicked on fetch");
                    Cursor getData=mydb.getData(1); //specific record (id=1)

                    if (getData.moveToNext()) {// data?
                        Log.v("georgeLog", "data found in DB...");
                        String dName = getData.getString(getData.getColumnIndex("name"));
                        String dPhone = getData.getString(getData.getColumnIndex("phone"));
                        String dEmail = getData.getString(getData.getColumnIndex("email"));
                        Toast.makeText(getApplicationContext(),
                                "rec: " + dName + ", " + dPhone + ", " + dEmail, Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(),
                                "did not get any data...:-(", Toast.LENGTH_LONG).show();
                    getData.close();
                }
            });

            bttnshowall.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.v("georgeLog", "clicked on Result Button");
                    ArrayList<String> fetchAll = new ArrayList<String>();
                    fetchAll=mydb.getAllContacts();
                    for (String a:fetchAll)
                        Log.v("georgeLog:", a.toString());
                    Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                    Log.v("georgeLog:", "intent executed");
                    intent.putStringArrayListExtra("fetchAll",fetchAll);
                    Log.v("georgeLog:","fetchALL executed");
                    startActivity(intent);
                    Log.v("georgeLog:", "startActivity executed");
                }
            });



            last_row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.v("georgeLog", "clicked on fetch");
                    Cursor getData=mydb.lastRow(); //specific record (id=1)

                    if (getData.moveToNext()) {// data?
                        Log.v("georgeLog", "data found in DB...");
                        String dName = getData.getString(getData.getColumnIndex("name"));
                        String dPhone = getData.getString(getData.getColumnIndex("phone"));
                        String dEmail = getData.getString(getData.getColumnIndex("email"));
                        Toast.makeText(getApplicationContext(),
                                "rec: " + dName + ", " + dPhone + ", " + dEmail, Toast.LENGTH_LONG).show();
                        last_name.setText(dName);
                        last_phone.setText(dPhone);
                        last_email.setText(dEmail);

                    }
                    else
                        Toast.makeText(getApplicationContext(),
                                "did not get any data...:-(", Toast.LENGTH_LONG).show();
                    getData.close();
                }
            });

            show_all.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    Intent intent = new Intent(getApplicationContext(),ShowAll.class);
                    startActivity(intent);
                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    Intent intent = new Intent(getApplicationContext(),delete.class);
                    startActivity(intent);
                }
            });

            search.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),serach.class);
                    startActivity(intent);
                }
            });


        }
    }



