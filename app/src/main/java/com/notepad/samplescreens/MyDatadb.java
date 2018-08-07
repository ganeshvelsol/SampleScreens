package com.notepad.samplescreens;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user4 on 2/3/2018.
 */

public class MyDatadb extends SQLiteOpenHelper
{
    Context con;
    MyDatadb(Context con)
    {
       super(con,"file",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String table1="create table sample(name text PRIMARY KEY not null,data text,dates text)";
        String table2="create table password(number text,password text)";
        String tab3="create table count(counter text)";
        sqLiteDatabase.execSQL(table1);
        sqLiteDatabase.execSQL(table2);
        sqLiteDatabase.execSQL(tab3);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
