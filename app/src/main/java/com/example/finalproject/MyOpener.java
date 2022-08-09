package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//MyOpener
public class MyOpener extends SQLiteOpenHelper {

    //Databse
    public final static String DATABASE_NAME = "ArticleDB";

    //Version#
    public final static int VERSION_NUM = 1;

    //Article
    public final static String TABLE_NAME = "Article";

    //Column id
    public final static String COL_ID = "_id";

    //Column Name
    public final static String COL_NAME = "Name";

    //Column Title
    public final static String COL_TITLE= "Title";

    //Column URL
    public final static String COL_URL = "Url";

    //Column Section
    public static final String COL_SECTION = "Section";



    //constructor
    public MyOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        db.execSQL("CREATE TABLE " + TABLE_NAME + "( _id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME  + " text,"
                + COL_TITLE   + " text,"
                + COL_URL + " text);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion
            , int newVersion)
    {

        //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }



}