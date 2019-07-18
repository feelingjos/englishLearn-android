package com.feeljcode.wordlearn.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.feeljcode.wordlearn.entity.WordItem;

import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * User: Feeljcode
 * Date: 2019/7/17
 * Time: 21:17
 */
public class DataOperation {

    public static void isMemoryWord(Context context,String data){

        DBHelper dbHelper = new DBHelper(context, DBVersion.DB_VERSION);

        SQLiteDatabase sQLiteDatabase = null;

        List<WordItem> wordItems = JSON.parseArray(data, WordItem.class);

        try{

            sQLiteDatabase = dbHelper.getReadableDatabase();
            //开启事务
            sQLiteDatabase.beginTransaction();

            for (WordItem wordItem : wordItems) {
                ContentValues contentValues = new ContentValues();

                contentValues.put("phoneticsymbol",wordItem.getPhoneticSymbol());
                contentValues.put("translate",wordItem.getTranslate());
                contentValues.put("type",wordItem.getType());
                contentValues.put("word",wordItem.getWord());

                sQLiteDatabase.insert("memoryrule","null",contentValues);

            }

            //提交事务
            sQLiteDatabase.setTransactionSuccessful();

        }catch (Exception ex){
            ex.getStackTrace();
        }finally {
            if(sQLiteDatabase != null){
                sQLiteDatabase.endTransaction();
                sQLiteDatabase.close();
            }
        }

    }

}
