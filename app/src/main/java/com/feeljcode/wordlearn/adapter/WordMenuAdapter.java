package com.feeljcode.wordlearn.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.feeljcode.wordlearn.R;
import com.feeljcode.wordlearn.WordAddActivity;
import com.feeljcode.wordlearn.entity.WordItem;
import com.feeljcode.wordlearn.entity.WordMenu;
import com.feeljcode.wordlearn.utils.ApiDocUtils;
import com.feeljcode.wordlearn.utils.DataOperation;
import com.feeljcode.wordlearn.utils.HttpUtils;
import com.feeljcode.wordlearn.utils.Version;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Feeljcode
 * Date: 2019/7/27
 * Time: 22:00
 * Description:  菜单列表
 */
public class WordMenuAdapter extends BaseAdapter {

    private List<WordMenu> data;
    private Context context;
    private Handler mHandler;

    public WordMenuAdapter(Handler mHandler,List<WordMenu> data,Context context){
        this.mHandler = mHandler;
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.word_spinner_layout,viewGroup,false);
            holder = new ViewHolder();
            holder.operation = (TextView) view.findViewById(R.id.operation);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        holder.operation.setTag(data.get(i).getId());
        holder.operation.setText(data.get(i).getName());

        holder.operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView textView = (TextView) view;

                Object tag = textView.getTag();

                if(1 == TypeUtils.castToInt(tag)){//同步

                    new Thread(() ->{
                        try{

                            String isSynTag = DataOperation.getIsSynTag(context);
                            if(isSynTag !=null){
                                return;
                            }
                            String saa = HttpUtils.post(ApiDocUtils.synGenerate,null);
                            //未生成
                            if(null == saa || "".equals(saa)){
                                //执行接口生成
                                HttpUtils.post(ApiDocUtils.generateMemoryWord, null);
                            }
                            //获取数据
                            String data = HttpUtils.post(ApiDocUtils.getTodayMemoryWord, null);

                            DataOperation.isMemoryWord(context,data);

                            List<WordItem> localNewWord = DataOperation.getLocalNewWord(context);

                            if(!localNewWord.isEmpty()){
                                Map<String,String> param = new HashMap<>();
                                param.put("data", JSON.toJSONString(localNewWord));
                                HttpUtils.post(ApiDocUtils.pushLocalNewWord,param);
                            }

                            List<WordItem> toDayWord = DataOperation.getToDayWord(context);

                            if(null != toDayWord && !toDayWord.isEmpty()){
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString("tag","updateSuccess");
                                message.setData(bundle);
                                message.obj = toDayWord;
                                mHandler.sendMessage(message);
                            }

                        }catch (Exception ex){
                            ex.getStackTrace();
                        }
                    }).start();
                }else if(2 == TypeUtils.castToInt(tag)){//添加

                    Intent intent = new Intent(context, WordAddActivity.class);
                    context.startActivity(intent);
                    
                }else if(3 == TypeUtils.castToInt(tag)){

                    new Thread(() ->{

                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

                        String post = HttpUtils.post( ApiDocUtils.getApkVersion, null);

                        Long versionCode = Version.getVersionCode(context);

                        if (TypeUtils.castToLong(post).longValue() > versionCode.longValue()){
                            File file = new File(path + "/app-release.apk");
                            if (!file.exists()){
                                //HttpUtils.downloadApk(ApiDocUtils.ApiBase + ApiDocUtils.downloadApk, "/data/data/com.feeljcode.wordlearn/download");
                                HttpUtils.downloadApk(ApiDocUtils.ApiBase + ApiDocUtils.downloadApk, path);
                            }else{
                                file = new File(path + "/app-release.apk");
                            }
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("tag","downloadApkSuccess");
                            message.setData(bundle);
                            message.obj = file;
                            mHandler.sendMessage(message);
                        }else {
                            Message message=new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("tag","noUpdate");
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        }


                        Toast.makeText(context,Version.getVersionCode(context).toString(),Toast.LENGTH_LONG).show();
                        Toast.makeText(context,post.toString(),Toast.LENGTH_LONG).show();
                    }).start();

                   /* String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

                    new Thread(() ->{

                    }).start();*/

                   //Toast.makeText(context,context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),Toast.LENGTH_LONG).show();

                   //Toast.makeText(context,Version.getVersionCode(context),Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context,"按钮异常",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    static class ViewHolder{
        TextView operation;//操作
    }
}
