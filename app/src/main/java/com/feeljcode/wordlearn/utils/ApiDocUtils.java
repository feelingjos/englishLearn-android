package com.feeljcode.wordlearn.utils;

/**
 * User: Feeljcode
 * Date: 2019/7/8
 * Time: 0:02
 */
public class ApiDocUtils {

    public static final String ApiBase = "http://192.168.1.111:8888";
    //public static final String ApiBase = "http://192.168.1.102:9959";
    //public static final String ApiBase = "http://192.168.1.202:9959";

    //是否同步
    public static final  String synGenerate = "/isgenerateTodayMemoryWord";

    //获取远程记忆数据
    public  static  final String getTodayMemoryWord = "/getMemoryWord";

    //执行生成日期语句
    public static final String generateMemoryWord = "/generateMemoryWord";

    //推送本地新单词到服务器
    public static final String pushLocalNewWord = "/saveNewWordForApp";

    public static final String downloadApk = "/res/static/app-release.apk";

    //获取新版本信息
    public static final String getApkVersion = "/getApkVersion";


}
