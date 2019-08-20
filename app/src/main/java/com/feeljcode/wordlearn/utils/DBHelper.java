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

        //新建单词
        String wordenglish ="CREATE TABLE \"wordenglish\" (\n" +
                "  \"id\" integer NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"word\" varchar,\n" +
                "  \"phoneticsymbol\" varchar,\n" +
                "  \"type\" varchar,\n" +
                "  \"translate\" varchar\n" +
                ");";

        //同步单词标识
        String memoryTagdb = "CREATE TABLE \"memory_generate_tag\" (\n" +
                "  \"id\" integer NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"today_ganerate\" integer\n" +
                ");";

        //记忆单词
        String memoryruledb = "CREATE TABLE \"memoryrule\" (\n" +
                "  \"id\" integer NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"word\" varchar,\n" +
                "  \"phoneticsymbol\" varchar,\n" +
                "  \"type\" varchar,\n" +
                "  \"translate\" varchar\n" +
                ");";

        sqLiteDatabase.execSQL(memoryruledb);
        sqLiteDatabase.execSQL(wordenglish);
        sqLiteDatabase.execSQL(memoryTagdb);

        /*sqLiteDatabase.execSQL("INSERT INTO memory_generate_tag (today_ganerate) " +
                "VALUES ("+DateOptionsUtils.getDate(null,0)+");");*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {



    }
}
