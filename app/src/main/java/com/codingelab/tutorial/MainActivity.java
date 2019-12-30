package com.codingelab.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity  {
  private Button onInsert,onSearch;
  private Button onSyn;
  EditText edit_name;
  EditText edit_phone;
  EditText edit_email;
  private Button next;
  private Button onGet;

  ProgressBar pb;

  ArrayList<User> usersPhp;
  ArrayList<User> usersSqlite ;


  SQLiteDatabase sqLiteDatabase;
  DBHelper  db;
  Cursor cursor;

  private Syn syn;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (android.os.Build.VERSION.SDK_INT > 9) {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);
    }
    // initilizing propreties
    this.onInsert=(Button)findViewById(R.id.onInsert);
    onSearch=(Button)findViewById(R.id.onSearch);
    edit_name=(EditText)findViewById(R.id.edit_name);
    edit_phone=(EditText)findViewById(R.id.edit_phone);
    edit_email=(EditText)findViewById(R.id.edit_email);
      this.next=(Button)findViewById(R.id.next);
    this.onGet=(Button)findViewById(R.id.onGet);

    pb = (ProgressBar) findViewById(R.id.pb);

    this.next.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
              startActivity(intent);
          }
      });

    this.onSyn=(Button)findViewById(R.id.onSyn);


    this.syn=new Syn();
    // preparing listener (onAction)
    this.onInsert.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String msg=syn.doInBackground("insert",edit_name.getText().toString(),edit_phone.getText().toString(),edit_email.getText().toString());
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
      }
    });

    this.onSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, SearchPhpActivity.class);
        startActivity(i);
      }
    });


    this.onSyn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       // syn.doInBackground("syn");

          GetUsersTask task = new GetUsersTask();
          task.execute();

      }
    });

      this.onGet.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              Intent i = new Intent(MainActivity.this, GetUsersActivity.class);
              startActivity(i);

          }
      });


   // getSupportLoaderManager().initLoader(0, null, this);

  }

  private class GetUsersTask extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();

      pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {

      String newsUrl = "http://192.168.1.139:8888/sqli/getData.php";

      try {

        URL url = new URL(newsUrl);

        // Open url connection
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

        // set request method
        httpConnection.setRequestMethod("GET");

        // open input stream and read server response data
        InputStream inputStream = httpConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String result = bufferedReader.readLine();


        httpConnection.disconnect();

        Log.i("result",result);


        return result;

      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      return null;
    }


    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);

      pb.setVisibility(View.GONE);

      if (s == null) {
        Toast.makeText(MainActivity.this, "Error connect to Server", Toast.LENGTH_SHORT).show();
        return;
      }

      // parsing json
      try {
        // JSONObject rootJSON = new JSONObject(s);

        //if (rootJSON.getInt("status") == 1) {

        // JSONArray usersArray = rootJSON.getJSONArray("usersPhp");

        usersPhp = new ArrayList<>();
        usersSqlite = new ArrayList<>();

        JSONArray usersArray = new JSONArray(s);

        for (int i = 0; i < usersArray.length(); i++) {

          User oneUser = new User();

          oneUser.setId(usersArray.getJSONObject(i).getString("id"));
          oneUser.setName(usersArray.getJSONObject(i).getString("name"));
          oneUser.setEmail(usersArray.getJSONObject(i).getString("email"));
          oneUser.setPhone(usersArray.getJSONObject(i).getString("phone"));
          usersPhp.add(oneUser);
        }

        db = new DBHelper(getApplicationContext());
        sqLiteDatabase=db.getReadableDatabase();
        cursor=db.getAllData();
        if(cursor.moveToFirst())
        {
          do
          {
            String id,name,mobile,email;
            id=cursor.getString(0);
            name=cursor.getString(1);
            mobile=cursor.getString(2);
            email=cursor.getString(3);
            User user=new User(id,name,mobile,email);
            usersSqlite.add(user);
          }while (cursor.moveToNext());

        }


        for(int i = 0; i< usersPhp.size(); i++) {
          if (!containName(usersSqlite, usersPhp.get(i))) {
            db.insertContact(usersPhp.get(i).getName(), usersPhp.get(i).getPhone(), usersPhp.get(i).getEmail());
          }
        }


        phpSync();


        //   Toast.makeText(MainActivity.this, ""+usersPhp.size(), Toast.LENGTH_SHORT).show();
      //  Toast.makeText(MainActivity.this, ""+usersSqlite.size(), Toast.LENGTH_SHORT).show();



      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }


  public  void phpSync(){
    for(int i = 0; i< usersSqlite.size(); i++) {
      if (!containName(usersPhp, usersSqlite.get(i))) {

        syn.doInBackground("insert",usersSqlite.get(i).getName(),usersSqlite.get(i).getPhone(),usersSqlite.get(i).getEmail());

      }
    }

  }


  boolean containName(List<User> list, User userphp) {

    for(int i=0;i<list.size();i++) {
      if (list.get(i).getName().equals(userphp.getName()) && list.get(i).getEmail().equals(userphp.getEmail())&& list.get(i).getPhone().equals(userphp.getPhone())){
        return true;
      }
    }

    return false;
  }



}
