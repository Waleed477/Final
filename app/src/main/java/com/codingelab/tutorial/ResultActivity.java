package com.codingelab.tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toast.makeText(getApplicationContext(), "Activity no 2", Toast.LENGTH_SHORT).show();

        ListView resList= (ListView) findViewById(R.id.listView);

        ArrayList<String> getData
                =getIntent().getExtras().getStringArrayList("fetchAll");
        Log.v("georgeLog", "getData created");
        for (String a:getData)
            Log.v("george_getData:", a.toString());

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        getData);
        Log.v("georgeLog", "arrayAdapter created");
        resList.setAdapter(arrayAdapter);
        Log.v("georgeLog:", "resList called");

        // make the list clickable
        resList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text or do whatever you need.
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
