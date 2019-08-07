package com.feeljcode.wordlearn.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * User: Feeljcode
 * Date: 2019/8/7
 * Time: 22:38
 * Description:  版本
 */
public class Version {

    /**
     * 获取版本信息
     * @param context
     * @return
     */
    public static String getVersionCode(Context context){

        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            // versionName = pi.versionName;
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode + "";

    }

}
