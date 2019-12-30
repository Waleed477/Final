package com.codingelab.tutorial;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class serach extends AppCompatActivity {

    EditText serach_text;
    Button button_serach;
    SQLiteDatabase sqLiteDatabase;
    DBHelper  db;
    ListView listView;
    Cursor cursor;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serach_screen);

        button_serach = (Button) findViewById(R.id.button_serach);
        listView=(ListView)findViewById(R.id.listView);
        db = new DBHelper(getApplicationContext());
        sqLiteDatabase=db.getReadableDatabase();

        button_serach.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                serach_text =(EditText) findViewById(R.id.serach_text);
                String ss=serach_text.getText().toString();
                sqLiteDatabase=db.getReadableDatabase();
                cursor=db.serach(ss);
                listAdapter=new ListAdapter(getApplicationContext(),R.layout.row_layout);
                listView.setAdapter(listAdapter);
                if(cursor.moveToFirst())
                {

                    do
                    {

                        String name,mobile,email;
                        String id;
                        id=cursor.getString(0);
                        name=cursor.getString(1);
                        mobile=cursor.getString(2);
                        email=cursor.getString(3);
                        DataProvider dataProvider=new DataProvider(id,name,mobile,email);
                        listAdapter.add(dataProvider);


                    }while (cursor.moveToNext());

                }

            }
        });

    }
}
