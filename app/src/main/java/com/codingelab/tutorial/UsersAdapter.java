package com.codingelab.tutorial;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {

  private static ArrayList<User> users;
  public Context mContext;

  public UsersAdapter(Context context, ArrayList<User> comingUsers) {
    users = comingUsers;
    mContext = context;
  }

  public View getView(final int position, View convertView, ViewGroup parent) {


    LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    convertView = mInflater.inflate(R.layout.list_row_user, null);

    TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
    TextView tvEmail = (TextView) convertView.findViewById(R.id.tv_email);
    TextView tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
    Button deletebtn = (Button) convertView.findViewById(R.id.deletebtn);
    Button updatebtn = (Button) convertView.findViewById(R.id.updatebtn);

    tvName.setText(users.get(position).getName());
    tvEmail.setText(users.get(position).getEmail());
    tvPhone.setText(users.get(position).getPhone());

    deletebtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        Syn syn=new Syn();

        String msg=syn.doInBackground("delete",String.valueOf(users.get(position).getId()));

        Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();


      }
    });

    updatebtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        UpdatephpdbActivity.start(mContext,users.get(position));

      }
    });

    return convertView;
  }

  //region other methods
  public int getCount() {
    return users.size();
  }

  public Object getItem(int position) {
    return users.get(position);
  }

  public long getItemId(int position) {
    return position;
  }
  //endregion

}
