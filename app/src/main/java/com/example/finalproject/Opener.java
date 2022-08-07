package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*IMPORTED SOURCE ................REFERENCE*/
public class MyOpener extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "ArticleDatabase";
    public final static int VERSION_NUM = 1;
    public final static String COL_ID = "_id";
    public final static String COL_NAME = "Name";
    public final static String COL_TITLE= "Title";
    public final static String COL_URL = "Url";
    public final static String COL_SECTION = "Section";
    private static final String TABLE_NAME = "Articles";

    public MyOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public MyOpener(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        db.execSQL("CREATE TABLE " + TABLE_NAME + "( _id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME  + " text,"
                + COL_TITLE   + " text,"
                + COL_URL + " text, "
                + COL_SECTION + " text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion
            , int newVersion)
    {   //Drop the old table
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table
        onCreate(db);
    }
}
