package com.feeljcode.wordlearn.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: Feeljcode
 * Date: 2019/7/8
 * Time: 18:48
 */
public class HttpUtils {


    public static String get(String api,Map<String,String> param){
        OkHttpClient okHttpClient = new OkHttpClient();

        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder =HttpUrl.parse(ApiDocUtils.ApiBase + api)
                .newBuilder();

        if(param !=null && !param.isEmpty()){
            for (Map.Entry<String, String> entry : param.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(),entry.getValue());
            }
        }


        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();

        Call call = okHttpClient.newCall(request);
        try{
            Response response = call.execute();
            return response.body().string();
        }catch (Exception ex){
            ex.getStackTrace();
        }
        return null;
    }


    public static String post(String api, Map<String,String> param){

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();

        if(param !=null && !param.isEmpty()){
            for (Map.Entry<String, String> entry : param.entrySet()) {
                builder.add(entry.getKey(),entry.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(ApiDocUtils.ApiBase + api)
                .post(builder.build())
                .build();

        Call call = okHttpClient.newCall(request);
        try{
            Response response = call.execute();
            return response.body().string();
        }catch (Exception ex){
            ex.getStackTrace();
        }
        return null;
    }

    /**
     * 下载更新apk
     * @param photoOnlinePath
     * @param photoLocalPath
     */
    public static void downloadApk(String photoOnlinePath, String photoLocalPath) {
        File myPath = new File(photoLocalPath);
        if(!myPath.exists()){
            boolean mkdirs = myPath.mkdirs();
            System.out.println(mkdirs);
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().get().url(photoOnlinePath).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                InputStream is;
                is = response.body().byteStream();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(myPath + "/app-release.apk");
                    int len;
                    byte[] bytes = new byte[1024];
                    while ((len = is.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);
                    }
                } catch (IOException oue) {
                    oue.getStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException isclose) {
                            isclose.getStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException fosclose) {
                            fosclose.getStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


}
