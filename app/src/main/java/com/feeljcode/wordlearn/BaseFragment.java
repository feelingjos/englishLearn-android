package com.feeljcode.wordlearn;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.feeljcode.wordlearn.adapter.WordAdapter;
import com.feeljcode.wordlearn.adapter.WordMenuAdapter;
import com.feeljcode.wordlearn.entity.WordItem;
import com.feeljcode.wordlearn.entity.WordMenu;
import com.feeljcode.wordlearn.utils.DataOperation;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import library.PopupList;
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
    private List<WordItem> data;
    private WordAdapter listWordAdapter;
    private List<String> popupMenuItemList = new ArrayList<>();

    private Handler  mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            String tag = data.getString("tag");
            if("updateSuccess".equals(tag)){//更新远程数据
                BaseFragment.this.data = (List<WordItem>) msg.obj;
                listWordAdapter.setRefresh(BaseFragment.this.data);
            }else if("loadSuccess".equals(tag)){
                BaseFragment.this.data = (List<WordItem>) msg.obj;
                listWordAdapter.setRefresh(BaseFragment.this.data);
            }else if("downloadApkSuccess".equals(tag)){//更新apk版本号

                File file = (File) msg.obj;
                Intent intent = new Intent();
                //执行动作
                intent.setAction(Intent.ACTION_VIEW);
                Uri datas = null;
                // 判断版本大于等于7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
                    datas = FileProvider.getUriForFile(context, "com.feeljcode.wordlearn.fileprovider", file);
                    // 给目标应用一个临时授权
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    //datas = Uri.fromFile(file);
                    datas = Uri.fromFile(file);
                }
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(datas, "application/vnd.android.package-archive");

                List<ResolveInfo> resolveLists = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                // 然后全部授权
                for (ResolveInfo resolveInfo : resolveLists){
                    String packageName = resolveInfo.activityInfo.packageName;
                    context.grantUriPermission(packageName, datas, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivity(intent);
            }
        }
    };

    public BaseFragment(Context context,String info){
        this.context = context;
        this.info = info;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(info.equals("单词")){

            data = new ArrayList<>();

            View view = inflater.inflate(R.layout.word_home,null);

            //列表展示
            ListView listView = (ListView) view.findViewById(R.id.word_list);

            Spinner spinner = (Spinner) view.findViewById(R.id.word_spinner);

            List<WordMenu> list = new ArrayList<>();

            WordMenu wordMenu = new WordMenu();
            wordMenu.setId(1);
            wordMenu.setName("同步");
            WordMenu wordMenu1 = new WordMenu();
            wordMenu1.setName("添加");
            wordMenu1.setId(2);

            WordMenu wordMenu2 = new WordMenu();
            wordMenu2.setName("更新");
            wordMenu2.setId(3);

            list.add(wordMenu);
            list.add(wordMenu1);
            list.add(wordMenu2);

            WordMenuAdapter wordMenuAdapter = new WordMenuAdapter(mHandler,list,context);

            //*ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,list);*//*
            spinner.setAdapter(wordMenuAdapter);

            /*spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    LinearLayout linearLayout = (LinearLayout) view;
                    TextView  textView = (TextView) linearLayout.getChildAt(0);

                    CharSequence tag = textView.getText();

                    if(1 == TypeUtils.castToInt(tag)){

                        Intent intent = new Intent(context,WordAddActivity.class);
                        startActivity(intent);
                        Toast.makeText(context,"同步",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(context,"添加",Toast.LENGTH_LONG).show();
                    }
                }
            });*/

           /* //同步按钮
            Button synGenrnate = (Button) view.findViewById(R.id.synGernate);*/

            listWordAdapter = new WordAdapter(context,data);

            listView.setAdapter(listWordAdapter);

            new Thread(() ->{
                data = DataOperation.getToDayWord(context);
                if(null != data && !data.isEmpty()){
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("tag","loadSuccess");//加载本地
                    message.setData(bundle);
                    message.obj = data ;
                    mHandler.sendMessage(message);
                }
            }).start();

            popupMenuItemList.add("困难");
            popupMenuItemList.add("容易");

            PopupList popupList = new PopupList(context);
            popupList.bind(listView, popupMenuItemList, new PopupList.PopupListListener() {
                @Override
                public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                    return true;
                }

                @Override
                public void onPopupListClick(View contextView, int contextPosition, int position) {
                    Toast.makeText(context,contextPosition + "",Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this, contextPosition + "," + position, Toast.LENGTH_SHORT).show();
                }
            });

            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    int checkedItemPosition = listView.getCheckedItemPosition();
                    Toast.makeText(context, "you chose item " + checkedItemPosition, Toast.LENGTH_SHORT).show();

                }
            });*/

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(context,"点击了",Toast.LENGTH_LONG).show();
                }
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
