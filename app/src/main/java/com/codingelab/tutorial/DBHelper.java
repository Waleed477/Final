package com.codingelab.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key, name text,phone text,email text)"//, street text,place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact  (String name, String phone, String email)//, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        //contentValues.put("street", street);
        //contentValues.put("place", place);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public Cursor lastRow()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor last =  db.rawQuery( " SELECT * FROM contacts WHERE id=(SELECT max(id) FROM contacts)", null );
        return last;
    }


    public Cursor countRow()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor last =  db.rawQuery( " SELECT COUNT(name) FROM contacts ", null );
        return last;
    }



    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    SQLiteDatabase db = this.getWritableDatabase();
    public void deleteContact (int id)
    {
        db.delete("contacts","id="+id,null);
    }

    public ArrayList<String> getAllContacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllphone()
    {
        ArrayList<String> array_list_phone = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_phone.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE)));
            res.moveToNext();
        }
        return array_list_phone;
    }

    public ArrayList<String> getAllemail()
    {
        ArrayList<String> array_list_email = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_email.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_EMAIL)));
            res.moveToNext();
        }
        return array_list_email;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result=db.rawQuery("Select * from contacts",null);
        return result;
    }

    public Cursor serach(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns={CONTACTS_COLUMN_ID,CONTACTS_COLUMN_NAME,CONTACTS_COLUMN_PHONE,CONTACTS_COLUMN_EMAIL};
        Cursor cursor=db.query(CONTACTS_TABLE_NAME,columns,CONTACTS_COLUMN_NAME +" = '"+name+"'",null,null,null,null);
      return cursor;
    }

}