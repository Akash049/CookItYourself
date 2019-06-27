package com.curl.ciykit.curl.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash Chandra on 08-02-2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "SQL";

    // Specifying the DATABASE VERSION
    private static final int DATABASE_VERSION = 1;

    // Specifying the database name
    private static final String DATABASE_NAME = "USER";

    // Specifying the TABLE NAME
    private static final String TABLE_NAME = "UserData";

    //Specifying the fields
    private static final String KEY_USERID = "userid";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USEREMAIL = "useremail";
    private static final String KEY_USERIMGURL = "imageurl";
    private static final String KEY_LOGGED_STATE = "log_state";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null  , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_USERID + " TEXT , "+ KEY_USERNAME + " TEXT , " + KEY_USEREMAIL + " TEXT , "
                 + KEY_USERIMGURL + " TEXT , " + KEY_LOGGED_STATE + " INTEGER ) ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddUser(UserDataModel data)
    {
        final SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_USERID,data.getUserID());
        values.put(KEY_USERNAME,data.getUserName());
        values.put(KEY_USEREMAIL,data.getUserEmail());
        values.put(KEY_USERIMGURL,data.getImageUrl());
        values.put(KEY_LOGGED_STATE,data.getUserLoggedInState());

        db.insert(TABLE_NAME  ,null,values);
        db.close();
    }
    public boolean isEmpty()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor mcursor = db.rawQuery(count,null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0)
            return false;
        else
            return true;
    }

    public List<UserDataModel> GetUser()
    {
        List<UserDataModel> mydata = new ArrayList<UserDataModel>();
        String query = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do{
                UserDataModel data = new UserDataModel();
                data.setUserID(cursor.getString(0));
                data.setUserName(cursor.getString(1));
                data.setUserEmail(cursor.getString(2));
                data.setImageUrl(cursor.getString(3));
                data.setUserLoggedInState(cursor.getInt(4));
                mydata.add(data);
            }while (cursor.moveToNext());
        }
        return mydata;
    }
}
