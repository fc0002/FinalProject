package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The type My opener.
 */
public class MyOpener extends SQLiteOpenHelper {

    /**
     * The constant DATABASE_NAME.
     */
    public final static String DATABASE_NAME = "ArticleDB";
    /**
     * The constant VERSION_NUM.
     */
    public final static int VERSION_NUM = 1;
    /**
     * The constant TABLE_NAME.
     */
    public final static String TABLE_NAME = "Article";
    /**
     * The constant COL_ID.
     */
    public final static String COL_ID = "_id";
    /**
     * The constant COL_NAME.
     */
    public final static String COL_NAME = "Name";
    /**
     * The constant COL_TITLE.
     */
    public final static String COL_TITLE= "Title";
    /**
     * The constant COL_URL.
     */
    public final static String COL_URL = "Url";
    public static final String COL_SECTION = "Section";


    /**
     * Instantiates a new My opener.
     *
     * @param ctx the ctx
     */
//constructor
    public MyOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    //will create table
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
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }



}


