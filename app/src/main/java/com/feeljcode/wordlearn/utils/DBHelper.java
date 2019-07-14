package com.feeljcode.wordlearn.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context,int version){
        super(context,"wordLearn.db",null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String initdb = "CREATE TABLE \"memoryrule\" (\n" +
                "  \"id\" integer NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"word\" varchar,\n" +
                "  \"phoneticsymbol\" varchar,\n" +
                "  \"type\" varchar,\n" +
                "  \"translate\" varchar\n" +
                ");";

        sqLiteDatabase.execSQL(initdb);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {



    }
}
