package com.codingelab.tutorial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetUsersActivity extends AppCompatActivity {

    Button btnGetUsers;

    ProgressBar pb;

    ListView listUsers;

    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_users);


        btnGetUsers = (Button) findViewById(R.id.btn_get_users);
        btnGetUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetUsersTask task = new GetUsersTask();
                task.execute();

            }
        });


        pb = (ProgressBar) findViewById(R.id.pb);

        listUsers = (ListView) findViewById(R.id.list_users);
    }


    private class GetUsersTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            String newsUrl = "http://192.168.8.102:8080/sqli/getData.php";

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
                Toast.makeText(GetUsersActivity.this, "Error connect to Server", Toast.LENGTH_SHORT).show();
                return;
            }

            // parsing json
            try {
               // JSONObject rootJSON = new JSONObject(s);

                //if (rootJSON.getInt("status") == 1) {

                   // JSONArray usersArray = rootJSON.getJSONArray("usersPhp");
                    JSONArray usersArray = new JSONArray(s);


                    for (int i = 0; i < usersArray.length(); i++) {

                        User oneUser = new User();

                        oneUser.setId(usersArray.getJSONObject(i).getString("id"));
                        oneUser.setName(usersArray.getJSONObject(i).getString("name"));
                        oneUser.setEmail(usersArray.getJSONObject(i).getString("email"));
                        oneUser.setPhone(usersArray.getJSONObject(i).getString("phone"));

                        users.add(oneUser);
                    }

                    UsersAdapter mUsersAdapter = new UsersAdapter(GetUsersActivity.this, users);
                    listUsers.setAdapter(mUsersAdapter);

              /*  } else {

                    Toast.makeText(GetUsersActivity.this, rootJSON.getString("msg"), Toast.LENGTH_SHORT).show();
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
