package com.feeljcode.wordlearn.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * User: Feeljcode
 * Date: 2019/7/8
 * Time: 18:48
 */
public class HttpUtils {


    public static Response get(String api,Map<String,String> param){
        OkHttpClient okHttpClient = new OkHttpClient();

        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder =HttpUrl.parse(ApiDocUtils.ApiBase + api)
                .newBuilder();

        if(param !=null && !param.isEmpty()){
            param.forEach((key,value) -> {
                urlBuilder.addQueryParameter(key,value);
            });
        }


        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();

        Call call = okHttpClient.newCall(request);
        try{
            Response response = call.execute();
            return response;
        }catch (Exception ex){
            ex.getStackTrace();
        }
        return null;
    }


    public static Response post(String api, Map<String,String> param){

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();

        if(param !=null && !param.isEmpty()){
            param.forEach((key,value) ->{
                builder.add(key,value);
            });
        }


        Request request = new Request.Builder()
                .url(ApiDocUtils.ApiBase + api)
                .post(builder.build())
                .build();

        Call call = okHttpClient.newCall(request);
        try{
            Response response = call.execute();
            return response;
        }catch (Exception ex){
            ex.getStackTrace();
        }
        return null;
    }

}
