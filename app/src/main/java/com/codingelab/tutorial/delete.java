package com.codingelab.tutorial;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class delete extends AppCompatActivity {
    DBHelper mydb;
    EditText delte_text;
    Button delte;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_screen);
        mydb = new DBHelper(this);
        delte = (Button) findViewById(R.id.delte);



        delte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                delte_text = (EditText)findViewById(R.id.delte_text);
                String id_1=delte_text.getText().toString();
                int id=new Integer(id_1).intValue();
                mydb.deleteContact(id);



            }
        });

    }
}
