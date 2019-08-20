package com.feeljcode.wordlearn.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.feeljcode.wordlearn.entity.WordItem;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Feeljcode
 * Date: 2019/7/17
 * Time: 21:17
 */
public class DataOperation {

    /**
     * 保存远程请求过来用背的单词
     * @param context
     * @param data
     */
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

    /**
     * 查询今天要背的单词
     * @return
     */
    public static List<WordItem> getToDayWord(Context context){

        DBHelper dbHelper = new DBHelper(context, DBVersion.DB_VERSION);
        SQLiteDatabase sQLiteDatabase = null;

        try{
            sQLiteDatabase = dbHelper.getReadableDatabase();
            Cursor cursor = sQLiteDatabase.query("memoryrule", null, null, null, null, null, null);
            //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
            List<WordItem> data = new ArrayList<>();
            //取出cursor中所有的数据
            while(cursor.moveToNext()) {

                WordItem wordItem = new WordItem();

                String word = cursor.getString(cursor.getColumnIndex("word"));
                String phoneticsymbol = cursor.getString(cursor.getColumnIndex("phoneticsymbol"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String translate = cursor.getString(cursor.getColumnIndex("translate"));

                wordItem.setWord(word);
                wordItem.setPhoneticSymbol(phoneticsymbol);
                wordItem.setType(type);
                wordItem.setTranslate(translate);

                data.add(wordItem);

            }
            // 3. 关闭
            cursor.close();
            sQLiteDatabase.close();
            return data;
        }catch (Exception ex){
            ex.getStackTrace();
        }

        return null;
    }

    /**
     * 添加要保存的单词到本地
     * @param context
     * @param wordItem
     */
    public static void addWordLocal(Context context,WordItem wordItem){

        DBHelper dbHelper = new DBHelper(context, DBVersion.DB_VERSION);
        SQLiteDatabase sQLiteDatabase = null;
        try{
            sQLiteDatabase = dbHelper.getReadableDatabase();
            //开启事务
            sQLiteDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put("phoneticsymbol",wordItem.getPhoneticSymbol());
            contentValues.put("translate",wordItem.getTranslate());
            contentValues.put("type",wordItem.getType());
            contentValues.put("word",wordItem.getWord());

            sQLiteDatabase.insert("wordenglish","null",contentValues);

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

    /**
     * 获取本地新加的单词上传到服务器
     * @param context
     * @return
     */
    public static List<WordItem> getLocalNewWord(Context context){

        DBHelper dbHelper = new DBHelper(context, DBVersion.DB_VERSION);
        SQLiteDatabase sQLiteDatabase = null;

        try{
            sQLiteDatabase = dbHelper.getReadableDatabase();
            Cursor cursor = sQLiteDatabase.query("wordenglish", null, null, null, null, null, null);
            //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
            List<WordItem> data = new ArrayList<>();
            //取出cursor中所有的数据
            while(cursor.moveToNext()) {

                WordItem wordItem = new WordItem();

                String word = cursor.getString(cursor.getColumnIndex("word"));
                String phoneticsymbol = cursor.getString(cursor.getColumnIndex("phoneticsymbol"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String translate = cursor.getString(cursor.getColumnIndex("translate"));

                wordItem.setWord(word);
                wordItem.setPhoneticSymbol(phoneticsymbol);
                wordItem.setType(type);
                wordItem.setTranslate(translate);

                data.add(wordItem);

            }
            // 3. 关闭
            cursor.close();
            sQLiteDatabase.close();
            return data;
        }catch (Exception ex){
            ex.getStackTrace();
        }
        return null;
    }

    /**
     * 获取本地同步标识
     * @param context
     * @return
     */
    public static String getIsSynTag(Context context){
        DBHelper dbHelper = new DBHelper(context, DBVersion.DB_VERSION);
        SQLiteDatabase sQLiteDatabase = null;
        try{
            sQLiteDatabase = dbHelper.getReadableDatabase();
            String where =" today_ganerate = " + DateOptionsUtils.getDate(null,0);
            Cursor cursor = sQLiteDatabase.query("memory_generate_tag", new String[]{"today_ganerate"},where , null, null, null, null);
            //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
            //取出cursor中所有的数据
            String tag= null;
            while(cursor.moveToNext()) {
                tag = cursor.getString(cursor.getColumnIndex("today_ganerate"));
            }
            // 3. 关闭
            cursor.close();
            sQLiteDatabase.close();
            return tag;
        }catch (Exception ex){
            ex.getStackTrace();
        }

        return null;
    }

    /**
     * 保存同步标识
     * @param context
     */
    public static void saveTodayTag(Context context){
        DBHelper dbHelper = new DBHelper(context, DBVersion.DB_VERSION);
        SQLiteDatabase sQLiteDatabase = null;
        try{
            sQLiteDatabase = dbHelper.getReadableDatabase();
            //开启事务
            sQLiteDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put("today_ganerate", DateOptionsUtils.getDate(null,0));

            sQLiteDatabase.insert("memory_generate_tag","null",contentValues);

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
