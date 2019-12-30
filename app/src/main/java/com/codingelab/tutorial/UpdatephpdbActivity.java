package com.codingelab.tutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatephpdbActivity extends AppCompatActivity {
    EditText name,phone,email;
    Button update;
   // EditText id;
 //   Button delete;
    private Syn syn;

   static User emItem;

    public static void start(Context context, User em) {

        Intent starter = new Intent(context, UpdatephpdbActivity.class);

        emItem = em;

        context.startActivity(starter);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phpdb);
        this.syn=new Syn();

        update=(Button)findViewById(R.id.bttnUpdate);
     //   delete=(Button)findViewById(R.id.bttnDelete);
        name=(EditText)findViewById(R.id.editTxtName);
        phone=(EditText)findViewById(R.id.editTxtPhone);
        email=(EditText)findViewById(R.id.editTxtEmail);
       // id=(EditText)findViewById(R.id.editTxtDelete);


        name.setText(emItem.getName());
        phone.setText(emItem.getPhone());
        email.setText(emItem.getEmail());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=syn.doInBackground("update", String.valueOf(emItem.getId()),name.getText().toString(),phone.getText().toString(),email.getText().toString());
                Toast.makeText(getBaseContext(),msg, Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        ///
       // delete.setOnClickListener(new View.OnClickListener() {
           // @Override
         //   public void onClick(View view) {
                //String msg=syn.doInBackground("delete",id.getText().toString());
                //Toast.makeText(getBaseContext(),msg, Toast.LENGTH_SHORT).show();

            }


    }

