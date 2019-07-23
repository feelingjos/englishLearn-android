package com.feeljcode.wordlearn;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.feeljcode.wordlearn.adapter.WordAdapter;
import com.feeljcode.wordlearn.entity.WordItem;
import com.feeljcode.wordlearn.utils.ApiDocUtils;
import com.feeljcode.wordlearn.utils.DataOperation;
import com.feeljcode.wordlearn.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by bruce on 2016/11/1.
 * BaseFragment
 */

public class BaseFragment extends Fragment {

    private Context context;
    private String info;
    private Handler mHandler;
    private List<WordItem> data;
    private WordAdapter adapter;

    public BaseFragment(Context context,String info){
        this.context = context;
        this.info = info;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(info.equals("新闻")){

            data = new ArrayList<>();

            View view = inflater.inflate(R.layout.word_home,null);

            //列表展示
            ListView listView = (ListView) view.findViewById(R.id.word_list);

            //同步按钮
            Button synGenrnate = (Button) view.findViewById(R.id.synGernate);

            adapter = new WordAdapter(context,data);

            listView.setAdapter(adapter);

            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    adapter.setRefresh(data);
                }
            };

            new Thread(() ->{
                data = DataOperation.getToDayWord(context);
                if(null != data && !data.isEmpty()){
                    Message message = new Message();
                    message.obj = data ;
                    mHandler.sendMessage(message);
                }
            }).start();

            synGenrnate.setOnClickListener(button -> {
                new Thread(() ->{
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

                        List<WordItem> toDayWord = DataOperation.getToDayWord(context);

                        if(null != toDayWord && !toDayWord.isEmpty()){
                            Message message = new Message();
                            message.obj = toDayWord;
                            mHandler.sendMessage(message);
                        }

                    }catch (Exception ex){
                        ex.getStackTrace();
                    }
                }).start();
            });

            return view;
        }else{
            View view = inflater.inflate(R.layout.fragment_base, null);
            TextView tvInfo = (TextView) view.findViewById(R.id.textView);
            tvInfo.setText(this.info);
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
