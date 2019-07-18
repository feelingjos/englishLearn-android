package com.feeljcode.wordlearn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.feeljcode.wordlearn.entity.WordItem;
import com.feeljcode.wordlearn.utils.ApiDocUtils;
import com.feeljcode.wordlearn.utils.DBHelper;
import com.feeljcode.wordlearn.utils.DBVersion;
import com.feeljcode.wordlearn.utils.DataOperation;
import com.feeljcode.wordlearn.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;

import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;


/**
 * Created by bruce on 2016/11/1.
 * BaseFragment
 */

public class BaseFragment extends Fragment {

    private Context context;

    public BaseFragment(){

    }

    public BaseFragment(Context context){
        this.context = context;
    }

    public static BaseFragment newInstance(String info) {
        Bundle args = new Bundle();
        BaseFragment fragment = new BaseFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    public static BaseFragment newInstance(Context context,String info) {
        Bundle args = new Bundle();
        BaseFragment fragment = new BaseFragment(context);
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String title = getArguments().getString("info");

        if(title.equals("新闻")){
            View view = inflater.inflate(R.layout.word_home,null);

            ListView listView = (ListView) view.findViewById(R.id.word_list);

            view.findViewById(R.id.synGernate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //请求
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                String saa = HttpUtils.post(ApiDocUtils.synGenerate,null);
                                //未生成
                                if(null == saa || "".equals(saa)){
                                    //执行接口生成
                                    HttpUtils.post(ApiDocUtils.generateMemoryWord, null);
                                }
                                //获取数据
                                String data = HttpUtils.post(ApiDocUtils.getTodayMemoryWord, null);

                                DataOperation.isMemoryWord(context,data);
                            }catch (Exception ex){
                                ex.getStackTrace();
                            }
                        }
                    }).start();

                }
            });

            List<String> list=new ArrayList<>();

            for (int i =0; i<10000 ; i ++ ){
                list.add(i+"+aaa");
            }

            ArrayAdapter<String> adapter =new ArrayAdapter<String>(context,R.layout.item_word,list);

            listView.setAdapter(adapter);

            return view;
        }else{
            View view = inflater.inflate(R.layout.fragment_base, null);
            TextView tvInfo = (TextView) view.findViewById(R.id.textView);
            tvInfo.setText(getArguments().getString("info"));
            tvInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OkHttpClient okHttpClient = new OkHttpClient();
                    //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。

                    Request request = new Request.Builder().url("http://192.168.1.111:8888/androidConnection?name=okhttp").method("GET",null).build();
                    //3.创建一个call对象,参数就是Request请求对象

                    final Call call = okHttpClient.newCall(request);
                    //4.同步调用会阻塞主线程,这边在子线程进行
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //同步调用,返回Response,会抛出IO异常

                                Response response = call.execute();

                                String data = response.body().string();

                                Log.e("data",data);

                                Log.e("name",response.body().toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                    /*Map<String,Object> param=new HashMap<>();

                    param.put("name","测试网络请求");

                    String data= HttpRequest.get("http://192.168.1.111:8888/androidConnection").charset(CharsetUtil.CHARSET_UTF_8).
                            form(param).execute().body();

                    Log.e("e",data);*/



                    Snackbar.make(v, "加油", Snackbar.LENGTH_SHORT).show();

                }
            });
            return view;
        }
    }
}
