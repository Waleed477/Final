package com.codingelab.tutorial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchPhpActivity extends AppCompatActivity {

    Button btn_search_users;
    EditText searchET;

    ProgressBar pb;

    ListView listUsers;
    private Syn syn;


    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        this.syn=new Syn();


        btn_search_users = (Button) findViewById(R.id.btn_search_users);
        searchET = findViewById(R.id.searchET);



              btn_search_users.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg=syn.doInBackground("search",searchET.getText().toString());
                        //Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
                       // GetUsersTask task = new GetUsersTask();
                      //  task.execute();

                        try {
                            // JSONObject rootJSON = new JSONObject(s);

                            //if (rootJSON.getInt("status") == 1) {

                            // JSONArray usersArray = rootJSON.getJSONArray("usersPhp");

                            users = new ArrayList<>();

                            JSONArray usersArray = new JSONArray(msg);

                            for (int i = 0; i < usersArray.length(); i++) {

                                User oneUser = new User();

                                oneUser.setId(usersArray.getJSONObject(i).getString("id"));
                                oneUser.setName(usersArray.getJSONObject(i).getString("name"));
                                oneUser.setEmail(usersArray.getJSONObject(i).getString("email"));
                                oneUser.setPhone(usersArray.getJSONObject(i).getString("phone"));

                                users.add(oneUser);
                            }

                            UsersAdapter mUsersAdapter = new UsersAdapter(SearchPhpActivity.this, users);
                            listUsers.setAdapter(mUsersAdapter);

              /*  } else {

                    Toast.makeText(GetUsersActivity.this, rootJSON.getString("msg"), Toast.LENGTH_SHORT).show();
                }*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

            String newsUrl = "http://192.168.1.139:8888/sqli/mysql_write.php";

            try {

                URL url = new URL(newsUrl);

                // Open url connection
                HttpURLConnection channel = (HttpURLConnection) url.openConnection();

                // set request method
                channel.setRequestMethod("POST");
                channel.setDoOutput(true);

                OutputStream subChannel=channel.getOutputStream();
                // create a pen to write in a specific information and in which language should this pen write.
                OutputStreamWriter pen=new OutputStreamWriter(subChannel,"UTF-8");
                // create a object to start write the information
                BufferedWriter student =new BufferedWriter(pen);
                // information to write
                String search = URLEncoder.encode("search", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
                String information=search;
                // student will start writing the information
                student.write(information);
                // student will push the information from the client side to the server side
                student.flush();
                student.close();


                // open input stream and read server response data
                              InputStream inputStream = channel.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String result = bufferedReader.readLine();

                subChannel.close();
                channel.disconnect();

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
                Toast.makeText(SearchPhpActivity.this, "Error connect to Server", Toast.LENGTH_SHORT).show();
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

                    UsersAdapter mUsersAdapter = new UsersAdapter(SearchPhpActivity.this, users);
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
